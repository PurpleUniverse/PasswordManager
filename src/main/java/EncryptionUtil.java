import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.util.Base64;

public class EncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static final String MASTER_KEY_FILE = "src/main/resources/master_key.txt";

    private static Key getMasterKey() {
        try {
            File keyFile = new File(MASTER_KEY_FILE);
            if (!keyFile.exists()) {
                KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
                keyGen.init(256); // AES-256
                SecretKey secretKey = keyGen.generateKey();
                try (FileOutputStream fos = new FileOutputStream(keyFile)) {
                    fos.write(secretKey.getEncoded());
                }
            }
            byte[] keyBytes = new byte[32]; // 256 bits
            try (FileInputStream fis = new FileInputStream(keyFile)) {
                fis.read(keyBytes);
            }
            return new SecretKeySpec(keyBytes, ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving master key", e);
        }
    }

    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getMasterKey());
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    public static String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getMasterKey());
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }
}
