import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class P06RemoveVillain {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/minions_db", properties);

        int villainIdInput = Integer.parseInt(scanner.nextLine());

        PreparedStatement statementSelectVillain = connection.prepareStatement(
                "SELECT name FROM villains WHERE id = ?");

        statementSelectVillain.setInt(1, villainIdInput);
        ResultSet villainSet = statementSelectVillain.executeQuery();

        if (!villainSet.next()) {
            System.out.println("No such villain was found");
            return;
        }

        String villainName = villainSet.getString("name");

        connection.setAutoCommit(false);

        try {
            PreparedStatement statementNumberMinions = connection.prepareStatement(
                    "SELECT count(minion_id) AS minions_count FROM minions_villains WHERE villain_id = ?;");

            statementNumberMinions.setInt(1, villainIdInput);
            ResultSet numberMinionsSet = statementNumberMinions.executeQuery();
            numberMinionsSet.next();
            int minionsCount = Integer.parseInt(numberMinionsSet.getString("minions_count"));

            PreparedStatement statementDeleteMinionsVillains = connection.prepareStatement(
                    "DELETE FROM minions_villains WHERE villain_id = ?;");

            statementDeleteMinionsVillains.setInt(1, villainIdInput);
            statementDeleteMinionsVillains.executeUpdate();

            PreparedStatement statementDeleteVillain = connection.prepareStatement(
                    "DELETE FROM villains WHERE name = ?;");

            statementDeleteVillain.setString(1, villainName);
            statementDeleteVillain.executeUpdate();

            connection.commit();

            System.out.printf("%s was deleted%n", villainName);
            System.out.printf("%d minions released%n", minionsCount);

        } catch (Exception e) {
            connection.rollback();
        }

        connection.close();
    }
}
