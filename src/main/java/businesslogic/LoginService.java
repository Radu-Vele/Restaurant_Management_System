package businesslogic;

import dataaccess.Serializer;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

/**
 * Used to maintain the information regarding the existing account holders and to add new ones
 */
public class LoginService {
    Collection<AccountHolder> accountHolders;

    public LoginService() {
        this.accountHolders = new HashSet<>();
    }

    public void loadData() throws IOException, ClassNotFoundException {
        Serializer<Collection<AccountHolder>> serializer = new Serializer<>(accountHolders, "accounts.txt");
        serializer.deserialize();
    }

    public void addAccountHolder(AccountHolder newAccountHolder) {
        accountHolders.add(newAccountHolder);
    }

    public void saveData() throws IOException {
        Serializer<Collection<AccountHolder>> serializer = new Serializer<>(accountHolders, "accounts.txt");
        serializer.serialize();
    }

    /**
     * Search for an account holder having the same username. Password is checked if username is found.
     * @param accountHolder
     * @return
     */
    public boolean searchAccountHolder(AccountHolder accountHolder) {
        //TODO: implement
        return false;
    }
}
