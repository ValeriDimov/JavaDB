import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P08IncreaseMinionAge {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/minions_db", properties);

        String input = scanner.nextLine();
        List<String> inputNumbers = Arrays.stream(input.split("\\s+"))
                .collect(Collectors.toList());

        String query = String.format("UPDATE minions SET name = lower(name), age = age + 1 WHERE id IN (%s);"
                , String.join(", ", inputNumbers));

        PreparedStatement statementUpdate = connection.prepareStatement(query);
        statementUpdate.executeUpdate();

        PreparedStatement statementSelect = connection.prepareStatement(
                "SELECT  name, age FROM minions");

        ResultSet resultSet = statementSelect.executeQuery();

        while (resultSet.next()) {
            String minionName = resultSet.getString("name");
            int minionAge = resultSet.getInt("age");

            System.out.printf("%s %d%n", minionName, minionAge);
        }
        connection.close();
    }
}
