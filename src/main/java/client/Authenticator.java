package client;

import common.exceptions.InvalidInputException;
import common.networkStructures.AuthenticationRequest;
import common.networkStructures.AuthenticationResponse;
import common.networkStructures.Request;

import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Authenticator {
    private Scanner scanner = new Scanner(System.in);
    private InetAddress address;
    private int port;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Authenticator(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    private String readUsername() throws InvalidInputException {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        if (username.equals("")) {
            throw new InvalidInputException("Username can't be empty, please enter a valid username: ");
        }
        return username;
    }

    private String readPassword() {
        Console console = System.console();
        System.out.println("Enter password: ");
        String password;

        if (console != null) {
            password = String.valueOf(console.readPassword());
        } else {
            password = scanner.nextLine();
        }

        password = encodePassword(password);

        return password;

    }



    public String encodePassword(String password) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-224");

            // Apply SHA-224 hashing to the password
            byte[] hashedBytes = messageDigest.digest(password.getBytes());

            // Convert the hashed bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void authenticate() {
        while (true) {
            try {
                System.out.println("Choose what you want to do:\n1 log in\n2 sign up");
                int option = 0;
                try {
                    option = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Option is either 1 or 2");
                }

                Request authenticationRequest;

                if (option == 1) {
                    username = readUsername();
                    password = readPassword();
                    authenticationRequest = new AuthenticationRequest(false, username, password);
                } else if (option == 2) {
                    username = readUsername();
                    password = readPassword();
                    //todo
                    // read 2 times
                    // if they are equal...
                    authenticationRequest = new AuthenticationRequest(true, username, password);
                } else {
                    throw new InvalidInputException("Option is either 1 or 2");
                }

                try (Socket socket = new Socket(address, port);
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    out.writeObject(authenticationRequest);
                    out.flush();

                    AuthenticationResponse response = (AuthenticationResponse) in.readObject();
                    if (response.isAuthenticated()) {
                        System.out.println("You have successfully logged in");
                        break;
                    } else {
                        System.out.println(response.getException().getMessage());
                    }
                } catch (IOException e) {
                    System.out.println("Server is down, please try again later");
                }


            } catch (InvalidInputException e) {
                System.out.println("Error sending an authentication request to the server: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Error receiving an authentication response from the server: " + e.getMessage());
            }
        }
            // receive result
    }
}
