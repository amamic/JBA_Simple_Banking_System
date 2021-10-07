package banking;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Account {
    int option;
    Map<String, CreditCard> accountInformation = new HashMap<>();
    Scanner scan = new Scanner(System.in);

    public void chooseOption() {
        do {
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
                    break;
            }
        } while (option != 0);
    }

    private void createAccount() {
        CreditCard newCredit = new CreditCard();
        accountInformation.put(newCredit.getCardNumber(), newCredit);
        System.out.println("Your card has been created\n" +
                "Your card number:\n" + newCredit.getCardNumber() + "\n" +
                "Your card PIN:\n" + newCredit.getPin() + "\n");
    }

    private void loginToAccount() {
        System.out.println("Enter your card number:");
        String cardNumber = scan.next();
        System.out.println("Enter your PIN:");
        String pin = scan.next();
        CreditCard card = accountInformation.get(cardNumber);
        if (card != null) {
            if (card.getPin().equals(pin)) {
                System.out.println("You have successfully logged in!");
                loginMenu();
            } else
                System.out.println("Wrong card number or PIN!");
        }
    }

    private void loginMenu() {
        System.out.println("1. Balance\n" + "2. Log out\n" + "0. Exit");
        switch (scan.nextInt()) {
            case 1:
                System.out.println("\nBalance: 0");
            case 2:
                System.out.println("\nYou have successfully logged out!");
            case 0:
                System.out.println("Bye!");
        }
    }

}