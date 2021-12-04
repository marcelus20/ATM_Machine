package atm.machine.atm;

import atm.machine.atm.dispenserlogic.ATMMachine;
import atm.machine.atm.models.ATMStatus;
import atm.machine.atm.models.Account;
import atm.machine.atm.models.AccountBalance;
import atm.machine.atm.models.Session;
import atm.machine.atm.tablesets.AccountSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@SpringBootApplication
@RestController
public class AtmApplicationController {


	private final AccountSet accounts;
	private HashSet<Session> sessions;
	private ATMMachine atmMachine;

	AtmApplicationController(){
		this.accounts = new AccountSet();
		this.sessions = new HashSet<Session>();
		this.atmMachine = ATMMachine.getInstance();

		// Adding the first account to the set: (I'm hardcoding as per assignment)
		accounts.add(new Account(123456789L, "1234", 800, 200));
		// Adding the second account:
		accounts.add(new Account(987654321L, "4321", 1230, 150));
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
	public Session session(@RequestBody Map<String, Object> body){

		Long accountNumber = Long.valueOf(String.valueOf(body.get("accountNumber")));
		String pin = (String) body.get("pin");

		// Look account with matching accountId and password
		boolean contains = this.accounts.contains(new Account(accountNumber, pin));
		if(contains){
			// Query the account with matching accountNumber
			Account account = accounts.retrieveOneOrNull(new Account(accountNumber));
			if(account != null && account.getPin().equals(pin)){
				// Create a new session object
				Session session = new Session(accountNumber);
				// Add to the session set
				this.sessions.add(session);

				// Return the serialised version of session
				return session;
			}else{
				// PIN is incorrect
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PIN is incorrect");
			}

		}else{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account ID or PIN is incorrect");
		}

	}

	private Session sessionVerifierHelper(Long accountNumber, String token){
		// First check if a session can be found in this.sessions with that token
		final boolean contains = this.sessions.contains(new Session(token));

		if(contains){
			// Retrieve the session object and verify if the accountNumber matches the session.accountNumber
			List<Session> sessionList= new ArrayList<Session>(this.sessions);
			List<Session> resultingList = sessionList.stream().filter(session->session.getToken().equals(token)).collect(Collectors.toList());

			// It's expected the resulting list to be of size 1 only.
			if(resultingList.size() == 1){
				// Retrieve the session object from the resultingList
				Session session = resultingList.get(0);

				if(!session.hasExpired()){
					return session;
				}else{
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session Expired.");
				}
			}else{
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Session");
			}
		}else{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Session");
		}
	}

	@GetMapping("/balance")
	public AccountBalance balance(@RequestParam("accountNumber") Long accountNumber, @RequestParam("token") String token){

		Session validSession = sessionVerifierHelper(accountNumber, token);

		// If the sentence above doesn't throw, it means that we are good to proceed
		// Token is valid, and thus, balance can be shown.
		// Retrieve the account from the this.accounts
		List<Account> filteredAccounts = this.accounts.stream().filter(acc->acc.getAccountNumber().equals(accountNumber)).collect(Collectors.toList());

		// filteredAccounts is expected to have size of 1, but if its size is 0 for any reason, let's throw a 500
		if(filteredAccounts.size() == 1){
			Account account = filteredAccounts.get(0);
			// Generate the AccountBalance object from the account
			return new AccountBalance(account.getAccountNumber(), account.getBalance());
		}else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	@RequestMapping("/withdraw")
	public String accountDemo(@RequestParam(value = "value") Integer value){
		ATMMachine.withdraw(new Cash(value));
		return "withdrew";
	}
*/
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
