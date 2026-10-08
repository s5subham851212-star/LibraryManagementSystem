import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Get all users
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql =
                "SELECT user_id, name, username, password, role " +
                "FROM users";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                User user = new User(
                        result.getInt("user_id"),
                        result.getString("name"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("role")
                );

                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    // Add a new user
    public boolean addUser(User user) {

        String sql =
                "INSERT INTO users " +
                "(name, username, password, role) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}