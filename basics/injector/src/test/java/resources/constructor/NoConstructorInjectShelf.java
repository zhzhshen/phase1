package resources.constructor;

import resources.Book;
import resources.BookShelf;

public class NoConstructorInjectShelf implements BookShelf {
    private Book book;

    public Book getBook() {
        return book;
    }
}
