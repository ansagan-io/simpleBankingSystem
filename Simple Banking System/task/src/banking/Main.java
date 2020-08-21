package banking;


public class Main {
    public static void main(String[] args) {
        /*if (args.length > 0) {
            if (args[0].equals("-fileName")) {
                DataBase.URL = "jdbc:sqlite:" + args[1];
                DataBase.createNewTable();
                BankingSystem bankingSystem = new BankingSystem();
                bankingSystem.startBanking();
            }
        }*/
        String name = "My name is %c. %s ";
        String age ="My age is %d ";
        String height = "My height is %.2fm";
        System.out.println(String.format(name + age + height, 'M', "Anderson", 22, 1.53));
    }
}