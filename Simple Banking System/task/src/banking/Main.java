package banking;


public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-fileName")) {
                DataBase.URL = "jdbc:sqlite:" + args[1];
                DataBase.createNewTable();
                BankingSystem bankingSystem = new BankingSystem();
                bankingSystem.startBanking();
            }
        }
    }
}