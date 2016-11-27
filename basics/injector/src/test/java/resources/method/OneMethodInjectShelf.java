package resources.method;

import resources.Book;
import resources.BookShelf;

import javax.inject.Inject;

public class OneMethodInjectShelf implements BookShelf{
    private Book book;

    @Inject
    public void load(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
