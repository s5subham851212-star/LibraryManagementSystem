import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserWindow extends JFrame {

    JTable userTable;
    DefaultTableModel model;

    UserDAO userDAO;

    public UserWindow() {

        setTitle("Users");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        userDAO = new UserDAO();

        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Username");
        model.addColumn("Role");

        userTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(userTable);

        JButton addButton = new JButton("Add User");

        addButton.addActionListener(e -> addUser());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addButton);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadUsers();

        setVisible(true);
    }

    private void loadUsers() {

        model.setRowCount(0);

        List<User> users = userDAO.getAllUsers();

        for (User user : users) {

            model.addRow(new Object[]{
                    user.getUserId(),
                    user.getName(),
                    user.getUsername(),
                    user.getRole()
            });
        }
    }

    private void addUser() {

        JTextField nameField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField roleField = new JTextField();

        JPanel panel = new JPanel(
                new GridLayout(4, 2, 5, 5)
        );

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel("Role:"));
        panel.add(roleField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add New User",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password =
                new String(passwordField.getPassword());
        String role = roleField.getText().trim();

        if (name.isEmpty() ||
            username.isEmpty() ||
            password.isEmpty() ||
            role.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return;
        }

        User user = new User(
                0,
                name,
                username,
                password,
                role
        );

        boolean added = userDAO.addUser(user);

        if (added) {

            JOptionPane.showMessageDialog(
                    this,
                    "User added successfully!"
            );

            loadUsers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "User could not be added!"
            );
        }
    }
}