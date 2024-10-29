import java.io.File;
import java.util.Scanner;

public class PasswordManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String usbPath = null;

        // Prompt user for USB path with validation
        while (true) {
            System.out.print("\nEnter the path to your USB drive for storing the AES key: ");
            usbPath = scanner.nextLine();
            File usbDir = new File(usbPath);

            if (usbDir.exists() && usbDir.isDirectory()) {
                break; // Valid path, break the loop
            } else {
                System.out.println("\nInvalid path. Please enter a valid USB drive path.");
            }
        }

        // Set the USB path in EncryptionUtil
        EncryptionUtil.setUsbPath(usbPath);

        // Start password manager
        PasswordVault vault = new PasswordVault();
        System.out.println("\n" + " Welcome to the Password Manager ");
        System.out.println(" -------------------------------");

        while (true) {
            System.out.println("1. Add Credential");
            System.out.println("2. Retrieve Credential");
            System.out.println("3. Generate Password");
            System.out.println("4. Exit");
            System.out.print("\nChoose an option: ");
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
                    System.out.println("WARNING: Please ensure that the USB is removed and stored safely! Then press ENTER to continue.");
                    scanner.nextLine(); // Ensure user takes action by pausing the console
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

