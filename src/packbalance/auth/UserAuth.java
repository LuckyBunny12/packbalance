package packbalance.auth;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserAuth {
    private static final String FILE_NAME = "users.dat";
    private static Map<String, String> userDatabase = new HashMap<>();
    
    static {
        loadUsers();
    }
    
    public static boolean registerUser(String userId, String password) {
        if (userDatabase.containsKey(userId)) {
            return false;
        }
        userDatabase.put(userId, hashPassword(password));
        saveUsers();
        return true;
    }
    
    public static boolean verifyUser(String userId, String password) {
        return userDatabase.containsKey(userId) && userDatabase.get(userId).equals(hashPassword(password));
    }
    
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    @SuppressWarnings("unchecked")
	private static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            userDatabase = (Map<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            userDatabase = new HashMap<>();
        }
    }
    
    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(userDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

