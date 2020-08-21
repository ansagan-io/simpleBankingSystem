package banking;

import java.util.Random;

class BankCard {
    private final String bankCardNumber;
    private final String bankCardPIN;

    public String getBankCardNumber() {
        return this.bankCardNumber;
    }

    public String getBankCardPIN() {
        return this.bankCardPIN;
    }

    public BankCard() {
        String bankCardNumber1;
        bankCardNumber1 = generateAccountNumber();
        this.bankCardPIN = generatePIN();

        while (DataBase.notExists(bankCardNumber1)) {
            bankCardNumber1 = getBankCardNumber();
        }
        this.bankCardNumber = bankCardNumber1;
        System.out.println();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println( this.bankCardNumber);
        System.out.println("Your card PIN:");
        System.out.println(this.bankCardPIN);
    }

    private String generatePIN() {
        Random random = new Random();
        StringBuilder PIN = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int temp = random.nextInt(10);
            PIN.append(temp);
        }
        return PIN.toString();
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder bankCardNumber = new StringBuilder();
        String BIN = "400000";
        StringBuilder lastNumber =  new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int j = random.nextInt(10);
            lastNumber.append(j);
        }
        bankCardNumber.append(BIN).append(lastNumber).append(getCheckDigit(BIN + lastNumber.toString()));

        return bankCardNumber.toString();
    }

    private int getCheckDigit(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = Integer.parseInt(number.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }
        int mod = sum % 10;
        return ((mod == 0) ? 0 : 10 - mod);
    }
    public static boolean isBankCardValid(String ccNumber) {
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
}
