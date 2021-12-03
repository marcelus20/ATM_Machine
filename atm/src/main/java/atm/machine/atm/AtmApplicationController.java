package atm.machine.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
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


}
