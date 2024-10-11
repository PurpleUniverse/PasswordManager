import java.util.Scanner;

public class PasswordManager {
    public static void main(String[] args) {
        PasswordVault vault = new PasswordVault();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n" + " Welcome to the Password Manager ");
        System.out.println(" -------------------------------");

        while (true) {
            System.out.println("1. Add Credential");
            System.out.println("2. Retrieve Credential");
            System.out.println("3. Generate Password");
            System.out.println("4. Exit");
            System.out.print("\n" + "Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    vault.addCredential(username, password);
                    break;
                case 2:
                    System.out.print("Enter username to retrieve: ");
                    String retrieveUsername = scanner.nextLine();
                    String retrievedPassword = vault.getCredential(retrieveUsername);
                    System.out.println("Password: " + retrievedPassword + "\n");
                    break;
                case 3:
                    String generatedPassword = PasswordGenerator.generatePassword();
                    System.out.println("Generated Password: " + generatedPassword + "\n");
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

