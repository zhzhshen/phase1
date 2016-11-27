package resources.singleton;

import resources.Book;
import resources.BookShelf;

import javax.inject.Inject;

public class SingletonBookShelf implements BookShelf{
    @Inject
    public SingletonBook book;
    public Book getBook() {
        return book;
    }
}
