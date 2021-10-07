package banking;

import java.util.Scanner;

public class Account {
    int option;
    static String[] accountInformation;
    int i = -1;
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
        i++;
        int array = i + 2;
        CreditCard newCC = new CreditCard();
        accountInformation = new String[array];
        accountInformation[i] = newCC.getCardNumber();
        accountInformation[++i] = newCC.getPin();
        System.out.println("Your card has been created\n" +
                "Your card number:\n" + newCC.getCardNumber() + "\n" +
                "Your card PIN:\n" + newCC.getPin() + "\n");
    }

    private void loginToAccount() {
        System.out.println("Enter your card number:");
        String cardNumber = scan.next();
        System.out.println("Enter your PIN:");
        String pin = scan.next();

        if (accountInformation[0].equals(cardNumber) && accountInformation[1].equals(pin)) {
            System.out.println("You have successfully logged in!");
            balance();
        } else
            System.out.println("Wrong card number or PIN!");
        System.out.println();
    }

    private void balance() {
        System.out.println("1. Balance\n" + "2. Log out\n" +
                "0. Exit");
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