package utils;

public class AlreadyImportedInitialProducts extends Exception{
    public AlreadyImportedInitialProducts() {
        super("Warning: the initial set of products was already imported!");
    }
}
