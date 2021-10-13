package banking;

import java.sql.*;

public class DatabaseManager {
    private Connection conn = null;

    public void createNewDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + " id integer PRIMARY KEY,\n"
                + " number text NOT NULL UNIQUE,\n"
                + " pin text NOT NULL,\n"
                + " balance integer\n"
                + ");";
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:card.s3db");
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void addCreditCardAccount(CreditCard c) {
        String sql = "INSERT INTO card(number,pin, balance) VALUES(?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, c.getCardNumber());
            statement.setString(2, c.getPin());
            statement.setInt(3, c.getBalance());
            statement.executeUpdate();

            System.out.println(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int returnBalance(String number) {
        String sql = "SELECT number, balance FROM card";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (number.equals(resultSet.getString("number"))) {
                    return resultSet.getInt("balance");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void updateBalance(String number, int InputtedIncome) {
        String sql = "UPDATE card SET " +
                "balance = ?" +
                "WHERE number = ?";
        int balance = returnBalance(number);
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, balance + InputtedIncome);
            statement.setString(2, number);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public boolean isCardInDatabase(String number, String pin) {
        String sql = "SELECT number, pin FROM card";
        try {
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

    public boolean isCardInDatabase(String number) {

        String sql = "SELECT number, pin FROM card";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (number.equals(resultSet.getString("number"))) {
                    return true;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void deleteCreditCardAccount(String number) {
        String sql = "DELETE FROM card where number = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, number);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadCreditCardAccounts() {
        String sql = "SELECT * FROM card";
        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                CreditCard creditCard = new CreditCard();
                creditCard.setCardNumber(result.getString("number"));
                creditCard.setPin(result.getString("pin"));
                creditCard.setBalance(result.getInt("balance"));
                System.out.println("Account loaded: " + creditCard);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


