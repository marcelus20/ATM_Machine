package atm.machine.atm;


import atm.machine.atm.dispenserlogic.*;
import atm.machine.atm.models.Account;
import atm.machine.atm.models.Session;
import atm.machine.atm.tablesets.AccountMap;
import atm.machine.atm.tablesets.SessionMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class AtmApplicationTests {

	@Autowired
	private AtmApplicationController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test public void accountMapShouldKeepTheMostUpToDateValueOfAccount(){
		AccountMap accounts = new AccountMap();
		Account account1 = new Account(123456789L,"1234");
		accounts.write(account1);
		Account account2 = new Account(123456789L, "12345"); // Second entry
		accounts.put(123456789L, account2);
		assertThat(accounts.get(123456789L).getPin()).isEqualTo("12345");
	}

	@Test public void accountMapSizeShouldNotChangeIfANewEntryIsAddedWithTheSameAccountId(){
		AccountMap accounts = new AccountMap();
		Account account1 = new Account(123456789L,"1234");
		accounts.write(account1);
		Account account2 = new Account(123456789L, "12345"); // Second entry
		accounts.put(123456789L, account2);
		assertThat(accounts.size()).isEqualTo(1);
	}

	@Test public void accountMapRetrieveOrNullShouldReturnNullIfAccountNumberDoesntExist(){
		AccountMap accounts = new AccountMap();
		Account account1 = new Account(123456789L,"1234");
		assertThat(accounts.retrieveOneOrNull(new Account(12345L))).isNull();
	}

	@Test public void sessionMapShouldKeepTheMostUpToDateValueOfSession(){
		SessionMap sessions = new SessionMap();
		Session session1 = new Session(123456789L, "abc");
		sessions.write(session1);
		Session session2 = new Session(123456789L, "abc", 10L); // Second entry
		sessions.put("abc", session2);
		assertThat(sessions.get("abc").getExpires()).isEqualTo(10L);
	}

	@Test public void sessionMapSizeShouldNotChangeIfANewEntryIsAddedWithTheSameSessionToken(){
		SessionMap sessions = new SessionMap();
		Session session1 = new Session(123456789L, "abc");
		sessions.write(session1);
		Session session2 = new Session(123456789L, "abc", 10L); // Second entry
		sessions.put("abc", session2);
		assertThat(sessions.size()).isEqualTo(1);
	}

	@Test public void sessionMapRetrieveOrNullShouldReturnNullIfSessionTokenDoesntExist(){
		SessionMap sessions = new SessionMap();
		Session session1 = new Session(123456789L, "abc");
		assertThat(sessions.retrieveOneOrNull(new Session("abcd"))).isNull();
	}


	@Test public void sessionExtendedTokenExpiresShouldBeGreaterThanCurrentTimeRegardlessOfOriginalExpires(){
		Session original = new Session("abc");
		Session extendedSession = original.extendToken();
		assertThat(extendedSession.getExpires()).isGreaterThan(System.currentTimeMillis());
	}

	@Test public void sessionExtendedTokenShouldReturnNullIfSessionIsExpired(){
		Session original = new Session(123456789L,"abc",0L);
		assertThat(original.extendToken()).isNull();
	}

	@Test public void chainShouldNotDispenseMoreThanTheAvailableBankNotesForEachDispenser(){
		Fifties fifties = Fifties.getInstance(); // 10 bank notes
		Twenties twenties = Twenties.getInstance(); // 30 bank notes
		Tenners tenners = Tenners.getInstance(); //30 bank notes
		Fivers fivers = Fivers.getInstance(); //20 bank notes

		fifties.setNextCashDispenser(twenties);
		twenties.setNextCashDispenser(tenners);
		tenners.setNextCashDispenser(fivers);

		fifties.dispense(new Cash(550));
		assertThat(fifties.getNumberOfNotes()).isEqualTo(0); // From 2 notes, it went to 0
		assertThat(twenties.getNumberOfNotes()).isEqualTo(28); // From 30 notes it went to 28
		assertThat(tenners.getNumberOfNotes()).isEqualTo(29);

		// At this point, fifties should be 0, twenties 28 and tenners 29.
		//Simulate one withdraw of 570
		fifties.dispense(new Cash(570));
		assertThat(fifties.getNumberOfNotes()).isEqualTo(0); // Fifties should remain 0
		assertThat(twenties.getNumberOfNotes()).isEqualTo(0); // twenties should drop from 28 to 0
		assertThat(tenners.getNumberOfNotes()).isEqualTo(28); // tenners should drop from 30 to 28

		// At this point, fifties should be 0, twenties 0 and tenners 28.
		//Simulate one withdraw of 305
		fifties.dispense(new Cash(305));
		assertThat(fifties.getNumberOfNotes()).isEqualTo(0); // Fifties should remain 0
		assertThat(twenties.getNumberOfNotes()).isEqualTo(0); // twenties remains 0
		assertThat(tenners.getNumberOfNotes()).isEqualTo(0); // tenners should drop from 28 to 0
		assertThat(fivers.getNumberOfNotes()).isEqualTo(15); // fivers should drop from 20 to 15

		// At this point, fifties should be 0, twenties should be 0, tenners 0 and fivers 15
		fifties.dispense(new Cash(100));
		assertThat(fifties.getNumberOfNotes()).isEqualTo(0); // Fifties should remain 0
		assertThat(twenties.getNumberOfNotes()).isEqualTo(0); // twenties remains 0
		assertThat(tenners.getNumberOfNotes()).isEqualTo(0); // tenners remains 0
		assertThat(fivers.getNumberOfNotes()).isEqualTo(0); // fivers drop to 0 even if the value is larger
	}

	@Test public void accountSetShouldUpdateToNewObjectPassedAsParameter(){
		AccountMap accounts = new AccountMap();
		Account account1 = new Account(111111L, "1234", 100, 100);
		accounts.write(account1);
		Account updatedAccount1 = account1.withBalance(200);
		accounts.write(updatedAccount1);

		assertThat(accounts.retrieveOneOrNull(new Account(111111L)).getBalance()).isEqualTo(200);

	}

}
