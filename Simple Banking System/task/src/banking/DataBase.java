package banking;

import java.sql.*;

public class DataBase {
    // SQLite connection string
    static String URL = "jdbc:sqlite:bankAccounts.db";

    public static void createNewTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS card " +
                "(" +
                "id INTEGER PRIMARY KEY, " +
                "number TEXT NOT NULL UNIQUE, " +
                "pin TEXT NOT NULL, " +
                "balance INTEGER DEFAULT 0" +
                ");";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(String number, String pin) {
        String sql = "INSERT INTO card (number,pin) VALUES(?,?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean notExists(String number){
        String sql =String.format("SELECT number FROM card WHERE number = '%s';", number) ;

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            boolean exists;
            exists = rs.next();
            return exists;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean canLogIn(String number, String PIN){
        String sql = String.format("SELECT * FROM card WHERE number = '%s' AND pin = '%s';", number, PIN);

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static int getBalance(String number){
        String sql = String.format("SELECT * FROM card WHERE number = '%s';", number);

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            int s = 0;

            while (rs.next()) {
                s = rs.getInt(4);
            }
            return s;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static void addIncome(String cardNumber, int income) {
        String sql = String.format("UPDATE card SET balance = balance + %d WHERE number = '%s';", income,cardNumber);

        try (Connection conn = DriverManager.getConnection(URL);
             Statement pstmt = conn.createStatement()) {
            pstmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void doTransfer(String cardNumberFrom, String cardToTransfer, int moneyToTransfer) {
        String sql = String.format("UPDATE card SET balance = balance + %d WHERE number = '%s';", moneyToTransfer, cardToTransfer);
        String sql2 = String.format("UPDATE card SET balance = balance - %d WHERE number = '%s';", moneyToTransfer, cardNumberFrom);

        try (Connection conn = DriverManager.getConnection(URL);
             Statement pstmt = conn.createStatement()) {
            pstmt.executeUpdate(sql);
            pstmt.executeUpdate(sql2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeAccount(String cardNumber) {
        String sql = String.format("DELETE FROM card WHERE number = '%s';", cardNumber);

        try (Connection conn = DriverManager.getConnection(URL);
             Statement pstmt = conn.createStatement()) {
            pstmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
