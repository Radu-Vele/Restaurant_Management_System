package utils;

public class AlreadyExistingUsername extends Exception{
    public AlreadyExistingUsername(String message) {
        super(message);
    }
}
