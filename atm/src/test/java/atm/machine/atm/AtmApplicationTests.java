package atm.machine.atm;


import atm.machine.atm.dispenserlogic.*;
import atm.machine.atm.models.Account;
import atm.machine.atm.models.Session;
import atm.machine.atm.tablesets.AccountSet;
import atm.machine.atm.tablesets.SessionSet;
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

	@Test public void sessionSetShouldContainAnotherSessionWithSameToken(){
		SessionSet sessions = new SessionSet();
		Session session1 = new Session("abc");
		sessions.add(session1);
		Session session2 = new Session("abc");
		assertThat(sessions.contains(session2)).isTrue();
	}

	@Test public void accountSetShouldContainAnotherAccountWithSameAccountNumberEvenWithDifferentPINs(){
		AccountSet accounts = new AccountSet();
		Account account1 = new Account(123456789L,"1234");
		accounts.add(account1);
		Account account2 = new Account(123456789L, "12345");
		assertThat(accounts.contains(account2)).isTrue();
	}

	@Test public void accountSetShouldContainAnotherAccountWithSameAccountNumberWithSamePINs(){
		AccountSet accounts = new AccountSet();
		Account account1 = new Account(123456789L,"1234");
		accounts.add(account1);
		Account account2 = new Account(123456789L, "1234");
		assertThat(accounts.contains(account2)).isTrue();
	}

	@Test public void accountSetShouldNotContainAnotherAccountWithDifferentAccountNumbersEvenIfPINsMatches(){
		AccountSet accounts = new AccountSet();
		Account account1 = new Account(123456789L,"1234");
		accounts.add(account1);
		Account account2 = new Account(12345678L, "1234");
		assertThat(accounts.contains(account2)).isFalse();
	}

	@Test public void accountSetShouldNotContainAnotherAccountWithDifferentAccountNumbersEvenIfPINsAreDifferent(){
		AccountSet accounts = new AccountSet();
		Account account1 = new Account(123456789L,"1234");
		accounts.add(account1);
		Account account2 = new Account(12345678L, "12345");
		assertThat(accounts.contains(account2)).isFalse();
	}

	@Test public void accountSetShouldReturnTheAccountIfAccountIdIsFoundInSet(){
		AccountSet accounts = new AccountSet();
		Account account1 = new Account(123456789L,"1234");
		accounts.add(account1);
		Account account2 = new Account(123456789L, "12345");
		assertThat(accounts.retrieveOneOrNull(account2).equals(account1)).isTrue();
	}

	@Test public void accountSetShouldReturnNullIfAccountIdIsNotFoundInSet(){
		AccountSet accounts = new AccountSet();
		Account account1 = new Account(123456789L,"1234");
		accounts.add(account1);
		Account account2 = new Account(12345678L, "12345");
		assertThat(accounts.retrieveOneOrNull(account2)).isNull();
	}

	@Test public void sessionSetShouldReturnTheSessionIfTokenIsFoundInSet(){
		SessionSet sessions = new SessionSet();
		Session session1 = new Session("abc");
		sessions.add(session1);
		Session session2 = new Session("abc");
		assertThat(sessions.retrieveOneOrNull(session2).equals(session1)).isTrue();
	}

	@Test public void sessionSetShouldReturnNullIfTokenIsNotFoundInSet(){
		SessionSet sessions = new SessionSet();
		Session session1 = new Session("abc");
		sessions.add(session1);
		Session session2 = new Session("ab");
		assertThat(sessions.retrieveOneOrNull(session2)).isNull();
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


}
