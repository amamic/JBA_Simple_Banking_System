package banking;

import java.util.Random;

public class CreditCard {
    private static final String bin = "400000";
    private final String cardNumber;
    private final String pin;
    private final Random ran = new Random();


    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public CreditCard() {
        cardNumber = bin + generateCardNumber() + 2;
        pin = generatePin();
    }

    private String generateCardNumber() {
        int accountID = ran.nextInt(999999999);
        return String.format("%09d", accountID);
    }

    private String generatePin() {
        int pinID = ran.nextInt(9999);
        return String.format("%04d", pinID);
    }


    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", pin='" + pin + '\'' +
                ", ran=" + ran +
                '}';
    }
}