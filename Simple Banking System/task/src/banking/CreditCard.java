package banking;

import java.util.Random;

public class CreditCard {
    private static final String bin = "400000";
    private final String cardNumber;
    private final String pin;

    private final Random ran = new Random();

    public CreditCard() {
        cardNumber = generateCardNumber();
        pin = generatePin();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    private String generateCardNumber() {
        int makeCardNumber = ran.nextInt(999999999);
        String cardNum = bin + String.format("%09d", makeCardNumber);
        return cardNum + calculateCheckSum(cardNum);
    }


    private String generatePin() {
        int makePin = ran.nextInt(9999);
        return String.format("%04d", makePin);
    }

    public static String calculateCheckSum(String ccNumber) {
        if (ccNumber == null)
            return null;
        String digit;

        int[] digits = new int[ccNumber.length()];
        for (int i = 0; i < ccNumber.length(); i++) {
            digits[i] = Character.getNumericValue(ccNumber.charAt(i));
        }

        for (int i = digits.length - 1; i >= 0; i -= 2) {
            digits[i] += digits[i];

            if (digits[i] >= 10) {
                digits[i] = digits[i] - 9;
            }
        }
        int sum = 0;
        for (int j : digits) {
            sum += j;
        }

        sum = sum * 9;

        digit = sum + "";
        return digit.substring(digit.length() - 1);
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
//https://www.admfactory.com/luhn-algorithm-implementation-in-java/