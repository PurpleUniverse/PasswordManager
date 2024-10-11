# Password Manager

My mini project was developed in Java. It's a small console application that would prompt the user to add or retrieve their credentials, as well as generate passwords. 

## Project structure

- PasswordManager.java: Main application class.
- PasswordVault.java: Class to handle password storage and retrieval to database.
- PasswordGenerator.java: Class to generate strong passwords.
- EncryptionUtil.java: Utility class for AES encryption and decryption of the passwords.
- HashUtil.java: ~~Utility class for that would use bcrypt maven dependency to hash and automatically salt.~~ Currently, this utility class is not used.

Note: The project includes Maven dependencies: SQLite and bcrypt, which are stored in the pom.xml file.

## Instructions to run the application

To run the application, make sure that the Maven dependencies are included, installed and working properly. Then build the PasswordManager.java and run the code. 

The code should be able to create the password.db file and the master_key.txt file in resources where the AES key would be generated and stored

## Screenshots of the product

![image](https://github.com/user-attachments/assets/e85fbe36-186a-434f-8514-49371f4f4548)

## Security of the product

### What do you protect against (who are the threat actors)

Ideally the password manager would be able to protect against external data breachers that or anyone tryng to gain access of the protected accounts. Due to the password manager not being connected to a server, as long as the device that the password manager is stored does not get compromised, the stored passwords would be secured from external attacks.

### What is your security model (encryption, key handling etc.)

- Encryption: The passwords are encrypted using AES (Advanced Encryption Standard) with a 256-bit key, to better ensure that attackers won't be able to gain access without the encryption key.
- Key Handling: The key is generated using the Java's *keyGenerator* and is not hardcoded in the application.
- Database usage: The SQLite stores the encrypted data on the person's device
- Hashing (WIP): Bcrypt was going to be used for hashing and salting the passwords as a more secure one-way encryption of passwords. I chose Bcrypt due to being a bit more lightweight for a small application, compared to Argon2 and scrypt. However the other options were also considered.

### Any pitfalls or limitations the solution

Regarding pitfalls, the primary ones would be the compromise of the master key as it would give access to all of the password and the access of the user's device. There's no multi-facor euthernecation which could've provided extra protection. Additionally, hashing was never able to be utilized, although it could provide better security. A different database method should also be considered upon a bigger project as SQLite is a lightweight database and would not be able to handle all uses cases, such as a multi-user enviorment. 

Regarding limitations, the application is lacking GUI that a user would have an easier time navigating and it could also provide a user login/master password to the vault. Additionally, an improvement of functionality was considered regarding changing existing passwords and deleting. However, as the main scope was safely storing entries it was considered as low priority.
