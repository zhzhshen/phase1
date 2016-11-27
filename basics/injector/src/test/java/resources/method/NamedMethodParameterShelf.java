package resources.method;

import resources.Book;
import resources.BookShelf;

import javax.inject.Inject;
import javax.inject.Named;

public class NamedMethodParameterShelf implements BookShelf {
    private Book book;

    @Inject
    public void load(@Named("theBook") Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
