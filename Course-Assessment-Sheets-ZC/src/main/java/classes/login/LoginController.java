package classes.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import classes.user.User;

public class LoginController {
    private User currentUser;

    public String getUserPath() {
        return currentUser.getUserPath();
    }

    public boolean checkUsernameExistance(String username) throws FileNotFoundException, IOException { // return true if
                                                                                                       // the user name
                                                                                                       // exists and
                                                                                                       // false if not
        BufferedReader reader;
        reader = new BufferedReader(
                new FileReader(".." + File.separator + "Course-Assessment-Sheets-ZC" + File.separator + "src"
                        + File.separator + "main" + File.separator + "Data" + File.separator + "UsernamesOnly.txt"));

        String line = reader.readLine();
        while (line != null) {
            System.out.println(line);
            if (line.equals(username)) {
                reader.close();
                System.out.println("user exists");
                return true;
            }
            // read next line
            line = reader.readLine();
        }
        reader.close();
        System.out.println("user not exists");

        return false;
    }

    public boolean checkUsernamePasswordExistance(String username, String password)
            throws FileNotFoundException, IOException { // return true if
        // the user name and password are correct
        // fasle if not
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(
                ".." + File.separator + "Course-Assessment-Sheets-ZC" + File.separator + "src" + File.separator + "main"
                        + File.separator + "Data" + File.separator + "UsernamesAndPasswords.txt"));

        String line = reader.readLine();
        String potentialUserName = line; // This line
        String potentialPassword = reader.readLine(); // The next line

        while (line != null) {
            System.out.println("User Name : ");
            System.out.println(potentialUserName);
            System.out.println("Password :");

            System.out.println(potentialPassword);
            if ((potentialUserName.equals(username)) && (potentialPassword.equals(password))) {
                reader.close();
                System.out.println("user and password are correct");
                return true;
            }
            // read next 2 lines
            potentialUserName = reader.readLine();
            potentialPassword = reader.readLine();
            line = potentialPassword;

        }
        reader.close();
        System.out.println("user name or password either doesn't exist or wrong");

        return false;
    }

    public Boolean addingNewUser(String username, String password) { // used
                                                                     // https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java

        // writing to the usernameonly file
        try (FileWriter fw = new FileWriter(".." + File.separator + "Course-Assessment-Sheets-ZC" + File.separator
                + "src" + File.separator + "main" + File.separator + "Data" + File.separator + "UsernamesOnly.txt",
                true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) {
            out.println(username);

        } catch (IOException e) {
            // exception handling left as an exercise for the reader
        }

        // writing to the username and password file
        try (FileWriter fw = new FileWriter(
                ".." + File.separator + "Course-Assessment-Sheets-ZC" + File.separator + "src" + File.separator + "main"
                        + File.separator + "Data" + File.separator + "UsernamesAndPasswords.txt",
                true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) {
            out.println(username);
            out.println(password);
        } catch (IOException e) {
            // exception handling left as an exercise for the reader
        }

        // creating a new folder in Data folder
        // used
        // https://www.tutorialspoint.com/how-to-create-a-new-directory-by-using-file-object-in-java
        String path = ".." + File.separator + "Course-Assessment-Sheets-ZC" + File.separator + "src" + File.separator
                + "main" + File.separator + "Data" + File.separator + username;
        File file = new File(path);
        // Creating the directory
        boolean bool = file.mkdirs();
        if (bool) {
            System.out.println("Directory created successfully" + file.getAbsolutePath());
            currentUser = new User(path);
            // return Optional.of(file.getAbsolutePath());
        } else {
            System.out.println("Sorry couldnt create specified directory");
            // return Optional.empty();
        }
        return bool;

    }

}