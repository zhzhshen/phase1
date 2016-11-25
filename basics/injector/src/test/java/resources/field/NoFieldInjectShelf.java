package resources.field;

import resources.Book;
import resources.BookShelf;

public class NoFieldInjectShelf implements BookShelf {
    private Book book;

    @Override
    public Book getBook() {
        return book;
    }
}
