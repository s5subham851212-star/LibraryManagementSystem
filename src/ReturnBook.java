import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class ReturnBook extends JFrame {

    JComboBox<String> transactionBox;
    JButton returnButton;

    TransactionDAO transactionDAO;

    public ReturnBook() {

        setTitle("Return Book");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        transactionDAO = new TransactionDAO();

        JLabel transactionLabel =
                new JLabel("Select Issued Book:");

        transactionBox = new JComboBox<>();

        returnButton = new JButton("Return Book");

        JPanel panel =
                new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(transactionLabel);
        panel.add(transactionBox);

        panel.add(new JLabel());
        panel.add(returnButton);

        add(panel);

        loadIssuedBooks();

        returnButton.addActionListener(
                e -> returnBook()
        );

        setVisible(true);
    }

    private void loadIssuedBooks() {

        String sql =
                "SELECT t.transaction_id, b.title, u.username " +
                "FROM transactions t " +
                "JOIN books b ON t.book_id = b.book_id " +
                "JOIN users u ON t.user_id = u.user_id " +
                "WHERE t.status = 'Issued'";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result =
                     statement.executeQuery()) {

            while (result.next()) {

                transactionBox.addItem(
                        result.getInt("transaction_id")
                        + " - "
                        + result.getString("title")
                        + " - "
                        + result.getString("username")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnBook() {

        if (transactionBox.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an issued book!"
            );

            return;
        }

        String selected =
                transactionBox.getSelectedItem().toString();

        int transactionId =
                Integer.parseInt(
                        selected.split(" - ")[0]
                );

        boolean returned =
                transactionDAO.returnBook(
                        transactionId
                );

        if (returned) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book returned successfully!"
            );

            transactionBox.removeAllItems();
            loadIssuedBooks();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Book could not be returned!"
            );
        }
    }
}