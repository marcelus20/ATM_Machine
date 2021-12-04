package atm.machine.atm;

import atm.machine.atm.logic.ATMMachine;
import atm.machine.atm.logic.Cash;
import atm.machine.atm.models.ATMStatus;
import atm.machine.atm.models.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AtmApplicationController {

	public static void main(String[] args) {
		SpringApplication.run(AtmApplicationController.class, args);
	}

	@RequestMapping("/ping")
	public String ping(){
		return "Pong";
	}

	@RequestMapping("/account")
	public Account accountDemo(){
		return new Account(123456789L,1234, 800, 200);
	}

	@RequestMapping("/withdraw")
	public String accountDemo(@RequestParam(value = "value") Integer value){
		ATMMachine.withdraw(new Cash(value));
		return "withdrew";
	}

	/**
	 * Endpoint to retrieve the status of the ATM Machine
	 * It will display how many of each notes there are left and the total amount.
	 * @return
	 */
	@RequestMapping("/status")
	public ATMStatus status(){
		return ATMMachine.serialise();
	}
}
