package by.bogdanov.config;

import by.bogdanov.repository.BookRepository;
import by.bogdanov.service.BookService;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {

        install(new JpaPersistModule("book-pu"));
        bind(BookRepository.class);
        bind(BookService.class);
    }
}
