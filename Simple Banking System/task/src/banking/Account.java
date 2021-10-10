package banking;

import java.util.Scanner;

public class Account {
    int option;
    boolean dbCreated = false;
    CreditCard newCredit = new CreditCard();
    Scanner scan = new Scanner(System.in);
    DatabaseManager dtbMng = new DatabaseManager();

    public void chooseOption() {
        if (!dbCreated) {
            dtbMng.createNewDatabase();
            dbCreated = true;
        }
        dtbMng.loadAccounts();
        while (true) {
            System.out.println("1. Create an account \n2. Log into account \n0. Exit");
            option = scan.nextInt();
            switch (option) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    loginToAccount();
                    break;
                case 0:
                    System.out.println("\nBye!");
                    System.exit(0);
                    break;
            }
        }
    }

    private void createAccount() {
        String cardNumber = newCredit.getCardNumber();
        String pin = newCredit.getPin();

        System.out.println("Your card has been created\n" +
                "Your card number:\n" + cardNumber + "\n" +
                "Your card PIN:\n" + pin + "\n");
        dtbMng.addAccount(newCredit);
        
    }

    private void loginToAccount() {
        System.out.println("Enter your card number:");
        String cardNumber = scan.next();
        System.out.println("Enter your PIN:");
        String pin = scan.next();
        if (dtbMng.isCardInDatabase(cardNumber, pin)) {
            System.out.println("You have successfully logged in!");
            loginMenu();
        } else {
            System.out.println("Wrong card number or PIN!");
        }

    }

    private void loginMenu() {
        while (true) {
            System.out.println("1. Balance\n" + "2. Log out\n" + "0. Exit");
            switch (scan.nextInt()) {
                case 1:
                    System.out.println("\nBalance: 0");
                    break;
                case 2:
                    System.out.println("\nYou have successfully logged out!");
                    return;
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
            }
        }
    }
}