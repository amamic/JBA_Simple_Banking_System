package banking;

import java.util.Random;

public class CreditCard {
    private String cardNumber;
    private String pin;
    private int balance = 0;

    private final Random ran = new Random();

    public CreditCard() {
        cardNumber = generateCardNumber();
        pin = generatePin();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() { return pin; }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    private String generateCardNumber() {
        int makeCardNumber = ran.nextInt(999999999);
        String cardNum = "400000" + String.format("%09d", makeCardNumber);
        for (int i = 0; i <= 9; i++) {
            String checkedCard = cardNum + i;
            if (checkLuhn(checkedCard)) {
                return checkedCard;
            }
        } return null;

    }
    private String generatePin() {
        int makePin = ran.nextInt(9999);
        return String.format("%04d", makePin);
    }


    public static boolean checkLuhn(String ccNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--)
        {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", pin='" + pin + '\'' +
                ", balance=" + balance +
                '}';
    }
}
