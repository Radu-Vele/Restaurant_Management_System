package businesslogic;

import dataaccess.Serializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    /**
     * Loads data from the file accounts.txt to the data structure that stores all account holders
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadData() throws IOException, ClassNotFoundException {
        //if the file doesn't exist, create it
        File file = new File("accounts.txt");
        if(file.createNewFile()) {
            return; //new file created;
        }
        else { //file already exists
            Serializer<Collection<AccountHolder>> serializer = new Serializer<>(accountHolders, "accounts.txt");
            accountHolders = serializer.deserialize();
        }
    }

    public void addAccountHolder(AccountHolder newAccountHolder) {
        accountHolders.add(newAccountHolder);
    }

    /**
     * Saves data from the accountHolders data structure to the accounts.txt file
     * @throws IOException
     */
    public void saveData() throws IOException {
        Serializer<Collection<AccountHolder>> serializer = new Serializer<>(accountHolders, "accounts.txt");
        serializer.serialize();
    }

    public Collection<AccountHolder> getAccountHolders() {
        return accountHolders;
    }

    public boolean searchUsername(String username) {
        for(AccountHolder accountHolder:accountHolders) {
            if(accountHolder.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public int getNewID() {
        if (accountHolders.size() == 0) {
            return 1;
        }
        else {
            int max = 0;
            for(AccountHolder accountHolder: accountHolders) {
                if(accountHolder.getID() > max) {
                    max = accountHolder.getID();
                }
            }
            return max + 1;
        }
    }

    public AccountHolder getAccountHolderFromSet(AccountHolder accountHolder) {
        for(AccountHolder accountHolder1 : accountHolders) {
            if(accountHolder.getUsername().equals(accountHolder1.getUsername())) {
                return accountHolder1;
            }
        }
        return null;
    }
}
