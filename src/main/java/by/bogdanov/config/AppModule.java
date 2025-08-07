package by.bogdanov.config;

import by.bogdanov.repository.BookRepository;
import by.bogdanov.service.BookService;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.persist.jpa.JpaPersistOptions;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        JpaPersistOptions options = JpaPersistOptions.builder()
                .setAutoBeginWorkOnEntityManagerCreation(true)
                .build();
        install(new JpaPersistModule("book-pu", options));
        bind(BookRepository.class);
        bind(BookService.class);
    }
}
