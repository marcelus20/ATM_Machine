package atm.machine.atm;

import atm.machine.atm.dispenserlogic.ATMMachine;
import atm.machine.atm.dispenserlogic.Cash;
import atm.machine.atm.exeptions.*;
import atm.machine.atm.models.*;
import atm.machine.atm.tablesets.AccountMap;
import atm.machine.atm.tablesets.SessionMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;


@SpringBootApplication
@RestController
public class AtmApplicationController {


	private final AccountMap accounts;
	private SessionMap sessions;
	private ATMMachine atmMachine;

	AtmApplicationController(){
		this.accounts = new AccountMap();
		this.sessions = new SessionMap();
		this.atmMachine = ATMMachine.getInstance();

		// Adding the first account to the set: (I'm hardcoding as per assignment)
		accounts.write(new Account(123456789L, "1234", 800, 200));
		// Adding the second account:
		accounts.write(new Account(987654321L, "4321", 1230, 150));
	}

	public static void main(String[] args) {
		SpringApplication.run(AtmApplicationController.class, args);
	}



	/**
	 * "should not dispense funds if the pin is incorrect,"
	 * The session object will be generated when the customer has entered the correct pin for the correct account.
	 * A valid session tokens is the indication that PIN was entered correctly and thus, can withdraw or see balance.
	 *
	 */
	@PostMapping("/session")
	public Session session(@RequestBody Map<String, Object> body) throws NonExistentAccountError, InvalidPinError {

		Long accountNumber = Long.valueOf(String.valueOf(body.get("accountNumber")));
		String pin = (String) body.get("pin");

		// Look account with matching accountId and password
		Account account = this.accounts.retrieveOneOrNull(new Account(accountNumber, pin));
		if(account != null){
			if(account.getPin().equals(pin)){
				// Create a new session object
				Session session = new Session(accountNumber);
				// Add to the session set
				this.sessions.write(session);

				// Return the serialised version of session
				return session;
			}else{
				throw new InvalidPinError();
			}
		}else{
			// Account Doesn't Exist
			throw new NonExistentAccountError();
		}

	}

	private Session sessionVerifierHelper(Long accountNumber, String token) throws InvalidTokenError, ExpiredTokenError {
		// First check if a session can be found in this.sessions with that token
		Session session = this.sessions.retrieveOneOrNull(new Session(token));

		if(session != null && accountNumber.equals(session.getAccountNumber())){
			if(!session.hasExpired()){
				return session;
			}else{
				throw new ExpiredTokenError();
			}
		}else{
			throw new InvalidTokenError();
		}
	}

	@GetMapping("/balance")
	public AccountBalance balance(
			@RequestParam("accountNumber") Long accountNumber,
			@RequestParam("token") String token
	) throws InvalidTokenError, ExpiredTokenError {
		Session validSession = sessionVerifierHelper(accountNumber, token);

		// If the sentence above doesn't throw, it means that we are good to proceed
		// Token is valid, and thus, balance can be shown.
		Account account = this.accounts.retrieveOneOrNull(new Account(accountNumber));

		// filteredAccounts is expected to have size of 1, but if its size is 0 for any reason, let's throw a 500
		if(account != null){
			// Generate the AccountBalance object from the account
			return new AccountBalance(account.getAccountNumber(), account.getBalance(), account.calculateMaximumWithdrawAmount());
		}else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/withdraw")
	public PostWithdrawAccount accountDemo(@RequestBody() Map<String, Object> body)
			throws
			NoEnoughCashError,
			ValueWithInvalidMultipleError,
			ATMOutOfBankNotesError,
			InvalidTokenError,
			ExpiredTokenError
	{

		Long accountNumber = Long.valueOf(String.valueOf(body.get("accountNumber")));
		String token = (String) body.get("token");
		Integer value = (Integer) body.get("value");

		Session session = sessionVerifierHelper(accountNumber, token);
		Account account = this.accounts.retrieveOneOrNull(new Account(accountNumber));
		if(account !=null){
			Double maximumWithdrawAmount = account.calculateMaximumWithdrawAmount();
			if(value < maximumWithdrawAmount){
				if(value % 50 == 0 || value % 20 == 0 || value % 10 == 0 || value % 5 == 0){
					// value is multiple of 50 or 20 or 10 or 5, go ahead.
					if(value < this.atmMachine.serialise().getTotal()){
						// If value less is more than the total that the ATM contains in cash
						//Perform withdraw
						this.atmMachine.withdraw(new Cash(value));
						//Update Account
						Account updatedAccount = account.withBalance(account.getBalance() - value);
						// Save updatedAccount in the accounts table
						accounts.write(updatedAccount);
						PostWithdrawAccount postWithdrawAccount = new PostWithdrawAccount(
								updatedAccount.getAccountNumber(),
								updatedAccount.getBalance(),
								this.atmMachine.getWithdraw()
						);
						// Reset the ATM withdraw to default
						this.atmMachine.setWithdraw(new Withdraw());
						return postWithdrawAccount;
					}else{
						// ATM doesn't have enough notes to fulfill withdraw request
						throw new ATMOutOfBankNotesError();
					}
				}else{
					// Not a valid multiple
					throw new ValueWithInvalidMultipleError();
				}
			}else{
				// No enough cash!
				throw new NoEnoughCashError();
			}
		}else{
			// Something went wrong! :/
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



	/**
	 * Endpoint to retrieve the status of the ATM Machine
	 * It will display how many of each notes there are left and the total amount.
	 * @return
	 */
	@RequestMapping("/status")
	public ATMStatus status(){
		return this.atmMachine.serialise();
	}
}
