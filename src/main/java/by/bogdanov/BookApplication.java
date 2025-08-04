package by.bogdanov;

import by.bogdanov.config.AppModule;
import by.bogdanov.model.Book;
import by.bogdanov.service.BookService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookApplication {
    private static final Logger logger = LogManager.getLogger(BookApplication.class);
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        PersistService persistService = injector.getInstance(PersistService.class);
        persistService.start();

        BookService service = injector.getInstance(BookService.class);
        try {
            Book book1 = new Book("Война и мир", "Лев Толстой");
            Book book2 = new Book("Преступление и наказание", "Фёдор Достоевский");
            service.save(book1);
            service.save(book2);
            logger.info("Книги сохранены");

            List<Book> books = service.findAll();
            books.forEach(logger:: info);

            service.update(1L, "Война и мир (обновлено)")
                    .ifPresent(b -> logger.info("Книга обновлена: " + b));
        } catch (Exception e){
            logger.error("Ошибка в работе с БД", e);
        }
        finally {
            persistService.stop();
        }
    }
}
