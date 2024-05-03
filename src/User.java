import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User (Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    public void register () {
        scanner.nextLine();
        System.out.println("Enter Your Full Name: ");
        String full_name = scanner.nextLine();
        System.out.println("Input Your Email: ");
        String email = scanner.nextLine();
        System.out.println("Input Your Phone Number");
        String phone_number = scanner.nextLine();
        System.out.println("Input Password: ");
        String password = scanner.nextLine();
        if (user_exist(email)) {
            System.out.println("Oops, User Already Exist for this Email Address !!");
            return;
        }
        String register_query = "INSERT INTO User (Full_Name, Email, Phone_number, Password) VALUES (?, ?, ?, ? )";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone_number);
            preparedStatement.setString(4, password);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Registration Successful, CONGRATULATIONS!!");
            }else {
                System.out.println("Oops, Registration Failed! ");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String login () {
        scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Phone Number: ");
        String phone_number = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        String login_query = "SELECT * FROM User WHERE email = ? AND Password = ? AND  Phone_number = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, phone_number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return email;
            }else {
                return null;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean user_exist (String email) {
        String query = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else {
                return false;
        }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}