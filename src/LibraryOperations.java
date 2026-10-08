public interface LibraryOperations {

    void performOperation();

    public static void main(String[] args) {

        LibraryOperations operation;

        operation = new AddBookOperation();
        operation.performOperation();

        operation = new IssueBookOperation();
        operation.performOperation();
    }
}

class AddBookOperation implements LibraryOperations {

    @Override
    public void performOperation() {
        System.out.println("Add Book operation");
    }
}

class IssueBookOperation implements LibraryOperations {

    @Override
    public void performOperation() {
        System.out.println("Issue Book operation");
    }
}