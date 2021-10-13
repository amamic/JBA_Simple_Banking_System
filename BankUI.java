package banking;

import java.util.Scanner;

public class BankUI {

    Scanner scan = new Scanner(System.in);
    DatabaseManager dtbMng = new DatabaseManager();


    public void chooseOption() {
        dtbMng.createNewDatabase();
        dtbMng.loadCreditCardAccounts();
        while (true) {
            System.out.println("1. Create an account \n2. Log into account \n0. Exit");
            switch (scan.nextInt()) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    loginToAccount();
                    break;
                case 0:
                    System.out.println("\nBye!");
                    dtbMng.closeConnection();
                    System.exit(0);
                    break;
            }
        }
    }

    private void createAccount() {
        CreditCard newCreditC = new CreditCard();
        String cardNumber = newCreditC.getCardNumber();
        String pin = newCreditC.getPin();

        System.out.println("Your card has been created\n" +
                "Your card number:\n" + cardNumber + "\n" +
                "Your card PIN:\n" + pin + "\n");
        dtbMng.addCreditCardAccount(newCreditC);

    }

    private void loginToAccount() {
        System.out.println("Enter your card number:");
        String userCardNumber = scan.next();
        System.out.println("Enter your PIN:");
        String userPin = scan.next();
        if (dtbMng.isCardInDatabase(userCardNumber, userPin)) {
            System.out.println("You have successfully logged in!");
            loginMenu(userCardNumber);
        } else {
            System.out.println("Wrong card number or PIN!");
        }

    }

    private void loginMenu(String cardNumber) {
        while (true) {
            System.out.println("1. Balance\n" + "2. Add income\n" + "3. Do transfer\n" +
                    "4. Close account\n" + "5. Log out\n" + "0. Exit");
            switch (scan.nextInt()) {
                case 1:
                    balance(cardNumber);
                    break;
                case 2:
                    addIncome(cardNumber);
                    break;
                case 3:
                    doTransfer(cardNumber);
                    break;
                case 4:
                    closeAccount(cardNumber);
                    return;
                case 5:
                    System.out.println("\nYou have successfully logged out!");
                    return;
                case 0:
                    System.out.println("Bye!");
                    dtbMng.closeConnection();
                    System.exit(0);
                    break;
            }
        }
    }

    private void balance(String cardNumber) {
        int balance = dtbMng.returnBalance(cardNumber);
        System.out.println("Balance: " + balance);
    }

    private void addIncome(String cardNumber) {
        System.out.println("Enter income: ");
        int inputtedIncome = scan.nextInt();
        dtbMng.updateBalance(cardNumber, inputtedIncome);
        System.out.println("Income was added!");
    }

    private void doTransfer(String cardNumber) {
        System.out.println("Transfer\nEnter card number: ");
        String ccNumToTransfer = scan.next();
        if (!CreditCard.checkLuhn(ccNumToTransfer)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return;
        }
        if (!dtbMng.isCardInDatabase(ccNumToTransfer)) {
            System.out.println("Such a card does not exist.");
            return;
        }
        if (cardNumber.equals(ccNumToTransfer)) {
            System.out.println("You can't transfer money to the same account!");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        int transferSum = scan.nextInt();
        if (transferSum <= dtbMng.returnBalance(cardNumber)) {
            dtbMng.updateBalance(ccNumToTransfer, transferSum);
            dtbMng.updateBalance(cardNumber, -transferSum);
            System.out.println("Success!");
        } else
            System.out.println("Not enough money!");
    }

    private void closeAccount(String cardNumber) {
        dtbMng.deleteCreditCardAccount(cardNumber);
        System.out.println("The account has been closed!");
    }
}
