import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class P05ChangeTownNames {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/minions_db", properties);

        String countryInput = scanner.nextLine();

        PreparedStatement statementUpdateTownNamesUpper = connection.prepareStatement(
                "UPDATE towns SET name = upper(name) WHERE country = ?;");

        statementUpdateTownNamesUpper.setString(1, countryInput);
        int countOfTownsUpdated = statementUpdateTownNamesUpper.executeUpdate();

        if (countOfTownsUpdated == 0) {
            System.out.println("No town names were affected.");
            return;
        }

        System.out.printf("%d town names were affected.%n", countOfTownsUpdated);

        List<String> townsUpdated = new ArrayList<>();

        PreparedStatement statementTownsList = connection.prepareStatement(
                "SELECT name FROM towns WHERE country = ?;");

        statementTownsList.setString(1, countryInput);
        ResultSet townsSet = statementTownsList.executeQuery();

        while (townsSet.next()) {
            String townName = townsSet.getString("name");
            townsUpdated.add(townName);
        }

        System.out.println(townsUpdated);

        connection.close();
    }
}
