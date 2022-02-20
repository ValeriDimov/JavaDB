import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class P03GetMinionNames {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",properties);

        PreparedStatement statementVillainName = connection.prepareStatement("SELECT name FROM villains" +
                " WHERE id = ?;");

        int villainId = Integer.parseInt(scanner.nextLine());
        statementVillainName.setInt(1, villainId);

        ResultSet resultSetVillainName = statementVillainName.executeQuery();

        if (resultSetVillainName.next()) {
            String villainName = resultSetVillainName.getString("name");
            System.out.printf("Villain: %s%n",villainId);
        } else {
            System.out.printf("No villain with ID %d exists in the database.", villainId);
            return;
        }

        PreparedStatement statementMinions = connection.prepareStatement("SELECT m.name AS mName, m.age AS mAge FROM villains AS v" +
                " JOIN minions_villains AS mv ON mv.villain_id = v.id" +
                " JOIN minions AS m ON mv.minion_id = m.id" +
                " WHERE v.id = ?;");

        statementMinions.setInt(1, villainId);
        ResultSet resultSetMinions = statementMinions.executeQuery();

        int index = 1;

        while (resultSetMinions.next()) {
            String minionName = resultSetMinions.getString("mName");
            int minionAge = Integer.parseInt(resultSetMinions.getString("mAge"));

            System.out.printf("%d. %s %d%n", index, minionName, minionAge);
            index++;
        }


    }
}
