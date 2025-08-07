package by.bogdanov.service;

import by.bogdanov.model.Book;
import by.bogdanov.repository.BookRepository;
import com.google.inject.persist.Transactional;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookRepository repository;

    @Inject
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(){
        return repository.findAll();
    }

    public Optional <Book> findByID(Long id){
        return repository.findById(id);
    }

    @Transactional // гарантирует, что если что то произойдет, можно откатить
    public Book save (Book book){
        return repository.save(book);
    }

    @Transactional
    public Book autoUpdateBook(Long id, String newTitle, String author){
        return repository.autoupdateBook(id, newTitle, author);
    }

    @Transactional
    public void deleteByID(Long id){
       repository.deleteByID(id);
    }

    @Transactional
    public List<Book> findByTitle(String title){
        return repository.findByTitle(title);
    }

    public List<Book> findByAuthor(String author){
        return repository.findByAuthor(author);
    }

    @Transactional
    public Optional<Book> update(Long id, String newTitle){
       Optional<Book> bookOptional = repository.findById(id);
       if(bookOptional.isPresent()){
           Book book = bookOptional.get();
           book.setTitle(newTitle);
           Book saveBook = repository.save(book);
           return Optional.of(saveBook);
       }
       return Optional.empty();
    }

}
