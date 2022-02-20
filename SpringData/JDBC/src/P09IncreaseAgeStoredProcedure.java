import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class P09IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/minions_db", properties);

        int minionId = Integer.parseInt(scanner.nextLine());

        CallableStatement statementUpdateProcedure = connection.prepareCall(
                "CALL usp_get_older(?);");

        statementUpdateProcedure.setInt(1, minionId);
        statementUpdateProcedure.executeUpdate();

        PreparedStatement statementQuery = connection.prepareStatement(
                "SELECT  name, age FROM minions WHERE id = ?");

        statementQuery.setInt(1, minionId);
        ResultSet resultSet = statementQuery.executeQuery();
        resultSet.next();
        String minionName = resultSet.getString("name");
        int minionAge = resultSet.getInt("age");

        System.out.printf("%s %d", minionName, minionAge);

        connection.close();;
    }
}
