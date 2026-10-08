import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransactionDAO {

    // Issue a book
    public synchronized boolean issueBook(int bookId, int userId) {
        String insertTransaction =
                "INSERT INTO transactions " +
                "(book_id, user_id, issue_date, status) " +
                "VALUES (?, ?, CURDATE(), 'Issued')";

        String updateBook =
                "UPDATE books " +
                "SET quantity = quantity - 1, " +
                "available = CASE WHEN quantity - 1 > 0 " +
                "THEN true ELSE false END " +
                "WHERE book_id = ? AND quantity > 0";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try (PreparedStatement transactionStatement =
                         connection.prepareStatement(insertTransaction);
                 PreparedStatement bookStatement =
                         connection.prepareStatement(updateBook)) {

                bookStatement.setInt(1, bookId);

                int updated =
                        bookStatement.executeUpdate();

                if (updated == 0) {
                    connection.rollback();
                    return false;
                }

                transactionStatement.setInt(1, bookId);
                transactionStatement.setInt(2, userId);

                transactionStatement.executeUpdate();

                connection.commit();

                return true;

            } catch (Exception e) {

                connection.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    // Return a book
    public boolean returnBook(int transactionId) {

        String findBook =
                "SELECT book_id FROM transactions " +
                "WHERE transaction_id = ? " +
                "AND status = 'Issued'";

        String updateTransaction =
                "UPDATE transactions " +
                "SET return_date = CURDATE(), " +
                "status = 'Returned' " +
                "WHERE transaction_id = ? " +
                "AND status = 'Issued'";

        String updateBook =
                "UPDATE books " +
                "SET quantity = quantity + 1, " +
                "available = true " +
                "WHERE book_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                int bookId = 0;

                try (PreparedStatement findStatement =
                             connection.prepareStatement(findBook)) {

                    findStatement.setInt(1, transactionId);

                    ResultSet result =
                            findStatement.executeQuery();

                    if (result.next()) {
                        bookId = result.getInt("book_id");
                    } else {
                        connection.rollback();
                        return false;
                    }
                }

                try (PreparedStatement transactionStatement =
                             connection.prepareStatement(updateTransaction)) {

                    transactionStatement.setInt(1, transactionId);

                    int updated =
                            transactionStatement.executeUpdate();

                    if (updated == 0) {
                        connection.rollback();
                        return false;
                    }
                }

                try (PreparedStatement bookStatement =
                             connection.prepareStatement(updateBook)) {

                    bookStatement.setInt(1, bookId);
                    bookStatement.executeUpdate();
                }

                connection.commit();

                return true;

            } catch (Exception e) {

                connection.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}