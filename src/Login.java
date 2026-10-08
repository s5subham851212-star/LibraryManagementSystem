import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class Login extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;

    public Login() {

        setTitle("Library Management System");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 40, 100, 25);

        usernameField = new JTextField();
        usernameField.setBounds(150, 40, 180, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 80, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 80, 180, 25);

        loginButton = new JButton("Login");
        loginButton.setBounds(150, 130, 100, 30);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                JOptionPane.showMessageDialog(
                this,
                "Login successful!"
                );

                new Dashboard();
                dispose();

            }  else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password"
                );
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}