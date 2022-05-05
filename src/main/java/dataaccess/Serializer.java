package dataaccess;

import java.io.*;

/**
 * Used to serialize the information from the DeliveryService class
 */
public class Serializer<T> {
    private T t;
    private String fileName;
    public Serializer(T t, String fileName) {
        this.t = t;
        this.fileName = fileName;
    }

    public void serialize() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(t);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public T deserialize() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        T toReturn = (T) objectInputStream.readObject();
        objectInputStream.close();
        return toReturn;
    }
}
