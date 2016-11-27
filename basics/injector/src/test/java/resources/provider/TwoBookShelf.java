package resources.provider;

import resources.Book;
import resources.BookShelf;

import javax.inject.Inject;
import javax.inject.Provider;

public class TwoBookShelf implements BookShelf {
    private Book book;
    private Book bookTwo;

    @Inject
    public void load(Provider<Book> bookProvider) {
        this.book = bookProvider.get();
        this.bookTwo = bookProvider.get();
    }

    public Book getBook() {
        return book;
    }

    public Book getBookTwo() {
        return bookTwo;
    }
}
