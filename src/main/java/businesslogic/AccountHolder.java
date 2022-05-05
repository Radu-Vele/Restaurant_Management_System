package businesslogic;

import java.io.Serializable;
import java.util.Objects;

public class AccountHolder implements Serializable {
    private String username;
    private String password;
    private String role;

    public AccountHolder(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Useful for storing account holders in a hash Set (unique elements)
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountHolder that = (AccountHolder) o;
        return username.equals(that.username) && password.equals(that.password) && role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role);
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
