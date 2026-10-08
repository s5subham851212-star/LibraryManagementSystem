import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Add a new book
    public void addBook(Book book) {

        String sql = "INSERT INTO books " +
                     "(title, author, category, available, quantity) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getCategory());
            statement.setBoolean(4, book.isAvailable());
            statement.setInt(5, book.getQuantity());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all books
    public List<Book> getAllBooks() {

        List<Book> books = new ArrayList<>();

        String sql = "SELECT book_id, title, author, category, available, quantity " +
                     "FROM books";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Book book = new Book(
                        result.getInt("book_id"),
                        result.getString("title"),
                        result.getString("author"),
                        result.getString("category"),
                        result.getBoolean("available"),
                        result.getInt("quantity")
                );

                books.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    // Update book quantity
    public boolean updateBookQuantity(int bookId, int quantity) {

        String sql = "UPDATE books " +
                     "SET quantity = ?, available = ? " +
                     "WHERE book_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, quantity);
            statement.setBoolean(2, quantity > 0);
            statement.setInt(3, bookId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a book
    public boolean deleteBook(int bookId) {

        String sql = "DELETE FROM books WHERE book_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}