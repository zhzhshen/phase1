package resources.method;

import resources.Book;
import resources.BookShelf;

import javax.inject.Inject;
import javax.inject.Named;

public class MultiInjectMethodShelf implements BookShelf {
    private Book book;
    private String tag;

    @Inject
    public void load(Book book) {
        this.book = book;
    }

    @Inject
    public void tag(@Named("fiction") String tag) {
        this.tag = tag;
    }

    public Book getBook() {
        return book;
    }

    public String getTag() {
        return tag;
    }
}
