import java.sql.*;

public class PasswordVault {
    private Connection connection;

    public PasswordVault() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:passwords.db");
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS credentials (username TEXT PRIMARY KEY, password TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCredential(String username, String password) {
        try {
            String encryptedPassword = EncryptionUtil.encrypt(password);
            String sql = "INSERT INTO credentials (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, encryptedPassword);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getCredential(String username) {
        try {
            String sql = "SELECT password FROM credentials WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return EncryptionUtil.decrypt(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}






