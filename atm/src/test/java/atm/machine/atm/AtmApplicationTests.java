package atm.machine.atm;


import atm.machine.atm.models.Account;
import atm.machine.atm.models.AccountBalance;
import atm.machine.atm.models.Session;
import atm.machine.atm.tablesets.AccountSet;
import atm.machine.atm.tablesets.SessionSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

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


}
