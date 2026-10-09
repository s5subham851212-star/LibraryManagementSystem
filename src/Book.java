public class Book {

    private int bookId;
    private String title;
    private String author;
    private String category;
    private boolean available;
    private int quantity;

    public Book(int bookId, String title, String author, String category,
                boolean available, int quantity) {

        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = available;
        this.quantity = quantity;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getQuantity() {
        return quantity;
    }
}