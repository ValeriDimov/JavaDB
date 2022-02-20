import java.sql.*;
import java.util.*;

public class P07PrintMinionNames {
    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/minions_db", properties);

        PreparedStatement statementMinionNames = connection.prepareStatement(
                "SELECT name FROM minions;");

        ResultSet minionsSet = statementMinionNames.executeQuery();
        List<String> minionsNames = new ArrayList<>();
        List<String> minionsNamesReversed = new ArrayList<>();

        while (minionsSet.next()) {
            String currentName = minionsSet.getString("name");
            minionsNames.add(currentName);
            minionsNamesReversed.add(currentName);
        }

        Collections.reverse(minionsNamesReversed);

        for (int i = 0; i < minionsNames.size() / 2; i++) {
            String currentNameOrdinary = minionsNames.get(i);
            String currentNameReversed = minionsNamesReversed.get(i);

            System.out.println(currentNameOrdinary);
            System.out.println(currentNameReversed);
        }

        if (minionsNames.size() % 2 != 0) {
            System.out.println(minionsNames.get((minionsNames.size() / 2)));
        }

        connection.close();
    }
}
