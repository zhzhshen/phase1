package resources.field;

import resources.Book;
import resources.BookShelf;

import javax.inject.Inject;
import javax.inject.Named;

public class NamedFieldInjectShelf implements BookShelf{
    @Inject
    @Named("theBook")
    public Book book;

    @Override
    public Book getBook() {
        return book;
    }
}
