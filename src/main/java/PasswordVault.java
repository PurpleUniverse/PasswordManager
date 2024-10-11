import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordVault {
    private static final String DB_URL = "jdbc:sqlite:passwords.db";

    public PasswordVault() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS credentials (username TEXT PRIMARY KEY, password TEXT)";
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCredential(String username, String password) {
        String hashedPassword = EncryptionUtil.hashPassword(password);
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO credentials (username, password) VALUES (?, ?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
            System.out.println("Credential added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getCredential(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT password FROM credentials WHERE username = ?")) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            } else {
                return "No credential found for this username.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}





