package resources.field;

import resources.Book;
import resources.BookShelf;

import javax.inject.Inject;

public class OneFieldInjectShelf implements BookShelf {
    @Inject
    public Book book;

    @Override
    public Book getBook() {
        return book;
    }
}
