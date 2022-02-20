import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class P04AddMinion {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        String[] minionData = scanner.nextLine().split("\\s+");
        String minionName = minionData[1];
        int minionAge = Integer.parseInt(minionData[2]);
        String minionTown = minionData[3];

        String villainName = scanner.nextLine().split("\\s+")[1];

        PreparedStatement statementTown = connection.prepareStatement(
                "SELECT id, name FROM towns WHERE name = ?"
        );

        statementTown.setString(1, minionTown);
        ResultSet setTowns = statementTown.executeQuery();

        if (!setTowns.next()) {
            PreparedStatement statementTownInsert = connection.prepareStatement(
                    "INSERT INTO towns(name) VALUES (?)");
            statementTownInsert.setString(1, minionTown);
            statementTownInsert.executeUpdate();
            System.out.printf("Town %s was added to the database.%n", minionTown);
        }

        PreparedStatement statementVillain = connection.prepareStatement(
                "SELECT id, name FROM villains WHERE name = ?"
        );

        statementVillain.setString(1, villainName);
        ResultSet setVillain = statementVillain.executeQuery();

        if (!setVillain.next()) {
            PreparedStatement statementVillainInsert = connection.prepareStatement(
                    "INSERT INTO villains(name, evilness_factor) VALUES (?, ?)");
            statementVillainInsert.setString(1, villainName);
            statementVillainInsert.setString(2, "evil");
            statementVillainInsert.executeUpdate();
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        PreparedStatement statementMinion = connection.prepareStatement(
                "SELECT id, name FROM minions WHERE name = ?");

        statementMinion.setString(1, minionName);
        ResultSet setMinion = statementMinion.executeQuery();

        if (!setMinion.next()) {
            setTowns = statementTown.executeQuery();
            setTowns.next();
            int minionTownId = Integer.parseInt(setTowns.getString("id"));
            PreparedStatement statementMinionInsert = connection.prepareStatement(
                    "INSERT INTO minions(name, age, town_id) VALUES (?, ?, ?)");
            statementMinionInsert.setString(1, minionName);
            statementMinionInsert.setInt(2, minionAge);
            statementMinionInsert.setInt(3, minionTownId);
            statementMinionInsert.executeUpdate();
        }

        PreparedStatement statementMinionServeToVillainInsert = connection.prepareStatement(
                "INSERT INTO minions_villains(minion_id, villain_id) VALUES (?, ?)");

        setMinion = statementMinion.executeQuery();
        setMinion.next();

        setVillain = statementVillain.executeQuery();
        setVillain.next();

        int minionId = Integer.parseInt(setMinion.getString("id"));
        int villainId = Integer.parseInt(setVillain.getString("id"));

        statementMinionServeToVillainInsert.setInt(1, minionId);
        statementMinionServeToVillainInsert.setInt(2, villainId);
        statementMinionServeToVillainInsert.executeUpdate();

        System.out.printf("Successfully added %s to be minion of %s", minionName, villainName);

        connection.close();
    }
}
