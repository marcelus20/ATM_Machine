package atm.machine.atm.models;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Session {
    private Long accountId;
    private String token;
    private Long expires;

    public Session(Long accountId) {
        this.accountId = accountId;
        this.token = RandomStringUtils.randomAlphabetic(12);

        // Set the expiration to (now + 1 minute)
        // 1 minute is usually the time the person takes to operate the ATM.
        // Of course if the person is still interacting with it it will keep renewing.
        // Client will be sending PUT requests to update the expires to more 30 seconds periodically
        // 1 minute is 1000 * 60
        this.expires = System.currentTimeMillis() + 1000 * 60;
    }

    public Session(Long accountId, String token, Long expires) {
        this.accountId = accountId;
        this.token = token;
        this.expires = expires;
    }

    public Session(Long accountId, Long expires) {
        this.accountId = accountId;
        this.token = RandomStringUtils.randomAlphabetic(12);
        this.expires = expires;
    }

    public Session(String token) {
        this.token = token;
        this.accountId = 0L;
        this.expires = System.currentTimeMillis() + 1000 * 60;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        return new EqualsBuilder().append(token, session.token).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(token).toHashCode();
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() >= this.expires;
    }

    public Session extendToken() {
        if(!this.hasExpired()){
            return new Session(this.accountId, this.token, System.currentTimeMillis() + 30 * 1000);
        }else {
            return null;
        }
    }
}
