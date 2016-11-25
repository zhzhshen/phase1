package resources;

public class NoConstructorInjectShelf implements BookShelf {
    private Book book;

    public Book getBook() {
        return book;
    }
}
