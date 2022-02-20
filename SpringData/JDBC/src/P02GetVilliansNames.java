import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class P02GetVilliansNames {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        PreparedStatement statement = connection.prepareStatement("SELECT v.name, count(DISTINCT(mv.minion_id)) AS count_minions FROM villains AS v" +
                " JOIN minions_villains AS mv ON mv.villain_id = v.id" +
                " GROUP BY v.id" +
                " HAVING count_minions > ?" +
                " ORDER BY count_minions DESC;");


        String count_minions = scanner.nextLine();
        statement.setInt(1, Integer.parseInt(count_minions));

        statement.executeQuery();

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next()) {
            String vName = resultSet.getString("name");
            String countMinions = resultSet.getString("count_minions");

            System.out.println(vName + " " + countMinions);

        }

    }
}
