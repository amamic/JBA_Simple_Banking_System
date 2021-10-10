package banking;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class DatabaseManager {
    Map<String, CreditCard> accountInformation = new HashMap<>();

    public Connection connect() {
        String url = "jdbc:sqlite:card.s3db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + " id integer PRIMARY KEY,\n"
                + " number text NOT NULL UNIQUE,\n"
                + " pin text NOT NULL,\n"
                + " balance integer\n"
                + ");";
        try (Connection conn = this.connect();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadAccounts() {
        String sql = "SELECT * FROM card";
        try (Connection conn = this.connect();
             Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                CreditCard creditCard = new CreditCard();
                creditCard.setCardNumber(result.getString("number"));
                creditCard.setPin(result.getString("pin"));
                creditCard.setBalance(result.getInt("balance"));
                accountInformation.put(creditCard.getCardNumber(), creditCard);
                System.out.println("Account loaded: " + creditCard);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addAccount(CreditCard c) {
        String sql = "INSERT INTO card(number,pin, balance) VALUES(?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, c.getCardNumber());
            statement.setString(2, c.getPin());
            statement.setInt(3, c.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        accountInformation.put(c.getCardNumber(), c);
        System.out.println(c);
    }

    public boolean isCardInDatabase(String number, String pin) {
        String sql = "SELECT number, pin FROM card";
        try {
            Connection conn = this.connect();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (number.equals(resultSet.getString("number")) &&
                        pin.equals(resultSet.getString("pin"))) {
                    return true;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

}