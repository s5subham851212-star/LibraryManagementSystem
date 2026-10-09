import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class IssueBook extends JFrame {

    JComboBox<String> userBox;
    JComboBox<String> bookBox;
    JButton issueButton;

    TransactionDAO transactionDAO;

    public IssueBook() {

        setTitle("Issue Book");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        transactionDAO = new TransactionDAO();

        JLabel userLabel = new JLabel("Select User:");
        JLabel bookLabel = new JLabel("Select Book:");

        userBox = new JComboBox<>();
        bookBox = new JComboBox<>();

        issueButton = new JButton("Issue Book");

        JPanel panel =
                new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(userLabel);
        panel.add(userBox);

        panel.add(bookLabel);
        panel.add(bookBox);

        panel.add(new JLabel());
        panel.add(issueButton);

        add(panel);

        loadUsers();
        loadBooks();

        issueButton.addActionListener(e -> issueBook());

        setVisible(true);
    }

    private void loadUsers() {

        String sql =
                "SELECT user_id, username FROM users";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result =
                     statement.executeQuery()) {

            while (result.next()) {

                userBox.addItem(
                        result.getInt("user_id")
                        + " - "
                        + result.getString("username")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBooks() {

        String sql =
                "SELECT book_id, title " +
                "FROM books " +
                "WHERE quantity > 0";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result =
                     statement.executeQuery()) {

            while (result.next()) {

                bookBox.addItem(
                        result.getInt("book_id")
                        + " - "
                        + result.getString("title")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void issueBook() {

        if (userBox.getSelectedItem() == null ||
            bookBox.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user and a book!"
            );

            return;
        }

        String selectedUser =
                userBox.getSelectedItem().toString();

        String selectedBook =
                bookBox.getSelectedItem().toString();

        int userId =
                Integer.parseInt(
                        selectedUser.split(" - ")[0]
                );

        int bookId =
                Integer.parseInt(
                        selectedBook.split(" - ")[0]
                );

        issueButton.setEnabled(false);

        Thread issueThread = new Thread(() -> {

            boolean issued =
                    transactionDAO.issueBook(
                            bookId,
                            userId
                    );

            SwingUtilities.invokeLater(() -> {

                issueButton.setEnabled(true);

                if (issued) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book issued successfully!"
                    );

                    bookBox.removeAllItems();
                    loadBooks();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book could not be issued!"
                    );
                }
            });
        });

        issueThread.start();
    }
}