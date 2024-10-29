import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;

public class EncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static String usbPath; // Variable to hold the USB path
    private static String AES_KEY_FILE; // Variable for the AES key file path

    public static void setUsbPath(String path) {
        usbPath = path;
        AES_KEY_FILE = usbPath + "/aes_key.txt"; // Set the AES key file path based on user input
        generateKeyIfNotExists(); // Generate key if it doesn't exist
    }

    private static void generateKeyIfNotExists() {
        try {
            File keyFile = new File(AES_KEY_FILE);
            // Generate a new AES key if it doesn't exist
            if (!keyFile.exists()) {
                KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
                keyGen.init(256); // AES-256
                SecretKey secretKey = keyGen.generateKey();
                Files.write(keyFile.toPath(), secretKey.getEncoded());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generating key", e);
        }
    }

    public static String encrypt(String data) {
        try {
            Key key = new SecretKeySpec(Files.readAllBytes(Paths.get(AES_KEY_FILE)), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    public static String decrypt(String encryptedData) {
        try {
            Key key = new SecretKeySpec(Files.readAllBytes(Paths.get(AES_KEY_FILE)), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }
}
