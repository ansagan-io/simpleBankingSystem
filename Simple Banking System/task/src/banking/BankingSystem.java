package banking;

import java.util.Scanner;

public class BankingSystem {
    private final Scanner scanner = new Scanner(System.in);

    public void startBanking () {
        System.out.println("1. Create an account\n2. Log into account\n0. Exit");
        String input = scanner.next();

        switch (input) {
            case "1":
                createAnBankCard();
                System.out.println();
                startBanking();
            case "2":
                System.out.println("Enter your card number:");
                String cardNumber = scanner.next();
                System.out.println("Enter your PIN:");
                String PIN = scanner.next();
                logIntoAccount(cardNumber, PIN);
                System.out.println();
                startBanking();
            case "0": exitIt();
            default:
                System.out.println("Unknown command, please try again...");
                System.out.println();
                startBanking();
        }
    }

    private void createAnBankCard() {
        BankCard bankCard = new BankCard();
        DataBase.insert(bankCard.getBankCardNumber(), bankCard.getBankCardPIN());
    }

    private void logIntoAccount(String cardNumber, String PIN) {
        if (DataBase.canLogIn(cardNumber, PIN)) {
            System.out.println();
            System.out.println("You have successfully logged in!");
            System.out.println();
            accountManager(cardNumber);
        }
        if (!DataBase.canLogIn(cardNumber, PIN)) {
            System.out.println();
            System.out.println("Wrong card number or PIN!");
            System.out.println();
            startBanking();
        }
    }

    private void accountManager(String cardNumber) {
        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");
        String input = scanner.next();
        switch (input) {
            case "1":
                System.out.println();
                System.out.println("Balance: " + DataBase.getBalance(cardNumber));
                System.out.println();
                accountManager(cardNumber);
            case "2":
                System.out.println();
                System.out.println("Enter income:");
                int income = scanner.nextInt();
                DataBase.addIncome(cardNumber, income);
                System.out.println("Income was added!");
                System.out.println();
                accountManager(cardNumber);
            case "3":
                System.out.println();
                System.out.println("Transfer\n" +
                        "Enter card number:");
                String cardToTransfer = scanner.next();
                doTransfer(cardToTransfer, cardNumber);
                System.out.println("Success!");
                System.out.println();
                accountManager(cardNumber);
            case "4":
                System.out.println();
                DataBase.closeAccount(cardNumber);
                System.out.println("The account has been closed!");
                System.out.println();
                this.startBanking();
            case "5":
                System.out.println();
                System.out.println("You have successfully logged out!");
                System.out.println();
                startBanking();
            case "0": exitIt();
            default:
                System.out.println();
                System.out.println("Unknown command, please try again...");
                System.out.println();
                accountManager(cardNumber);
        }
    }

    private void doTransfer(String cardToTransfer, String cardNumberFrom) {
        if (cardToTransfer.equals(cardNumberFrom)) {
            System.out.println("You can't transfer money to the same account!");
            System.out.println();
            accountManager(cardNumberFrom);
        } else if (!BankCard.isBankCardValid(cardToTransfer)) {
            System.out.println("Probably you made mistake in the card number.\nPlease try again!");
            System.out.println();
            accountManager(cardNumberFrom);
        } else if (!DataBase.notExists(cardToTransfer)) {
            System.out.println("Such a card does not exist.");
            System.out.println();
            accountManager(cardNumberFrom);
        } else {
            System.out.println("Enter how much money you want to transfer:");
            int moneyToTransfer = scanner.nextInt();
            if (DataBase.getBalance(cardNumberFrom) - moneyToTransfer <= 0) {
                System.out.println("Not enough money!");
                System.out.println();
                accountManager(cardNumberFrom);
            } else {
                DataBase.doTransfer(cardNumberFrom, cardToTransfer, moneyToTransfer);
            }
        }
    }

    private void exitIt() {
        System.out.println();
        System.out.println("Bye!");
        System.exit(0);
    }
}
