import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookWindow extends JFrame {

    JTable bookTable;
    DefaultTableModel model;

    BookDAO bookDAO;

    public BookWindow() {

        setTitle("Manage Books");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        bookDAO = new BookDAO();

        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Category");
        model.addColumn("Quantity");

        bookTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(bookTable);

        JButton addButton = new JButton("Add Book");
        JButton updateButton = new JButton("Update Book");
        JButton deleteButton = new JButton("Delete Book");

        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());

        JPanel bottomPanel = new JPanel();

        bottomPanel.add(addButton);
        bottomPanel.add(updateButton);
        bottomPanel.add(deleteButton);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadBooks();

        setVisible(true);
    }

    private void loadBooks() {

        model.setRowCount(0);

        List<Book> books = bookDAO.getAllBooks();

        for (Book book : books) {

            model.addRow(new Object[]{
                    book.getBookId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCategory(),
                    book.getQuantity()
            });
        }
    }

    private void addBook() {

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        panel.add(new JLabel("Title:"));
        panel.add(titleField);

        panel.add(new JLabel("Author:"));
        panel.add(authorField);

        panel.add(new JLabel("Category:"));
        panel.add(categoryField);

        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add New Book",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = categoryField.getText().trim();

        if (title.isEmpty() ||
            author.isEmpty() ||
            category.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return;
        }

        int quantity;

        try {

            quantity = Integer.parseInt(
                    quantityField.getText().trim()
            );

            if (quantity < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Quantity cannot be negative!"
                );
                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be a number!"
            );

            return;
        }

        Book book = new Book(
                0,
                title,
                author,
                category,
                quantity > 0,
                quantity
        );

        bookDAO.addBook(book);

        JOptionPane.showMessageDialog(
                this,
                "Book added successfully!"
        );

        loadBooks();
    }

    private void updateBook() {

        int selectedRow = bookTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a book first!"
            );

            return;
        }

        int bookId =
                (int) model.getValueAt(selectedRow, 0);

        String title =
                (String) model.getValueAt(selectedRow, 1);

        String quantityText = JOptionPane.showInputDialog(
                this,
                "Enter new quantity for " + title + ":"
        );

        if (quantityText == null) {
            return;
        }

        int quantity;

        try {

            quantity = Integer.parseInt(
                    quantityText.trim()
            );

            if (quantity < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantity cannot be negative!"
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be a number!"
            );

            return;
        }

        boolean updated =
                bookDAO.updateBookQuantity(
                        bookId,
                        quantity
                );

        if (updated) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book updated successfully!"
            );

            loadBooks();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Book could not be updated!"
            );
        }
    }

    private void deleteBook() {

        int selectedRow = bookTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a book first!"
            );

            return;
        }

        int bookId =
                (int) model.getValueAt(selectedRow, 0);

        String title =
                (String) model.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete \"" +
                title + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean deleted =
                bookDAO.deleteBook(bookId);

        if (deleted) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book deleted successfully!"
            );

            loadBooks();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Book could not be deleted!"
            );
        }
    }
}