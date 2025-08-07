package by.bogdanov.repository;

import by.bogdanov.BookApplication;
import by.bogdanov.model.Book;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class BookRepository {
    private static final Logger logger = LogManager.getLogger(BookRepository.class);
    private final EntityManager em;

    @Inject
    public BookRepository(EntityManager em) {
        this.em = em;
    }


    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public Optional<Book> findById(Long id) {
        Book book = em.find(Book.class, id);
        return Optional.ofNullable(book);
    }

   public List<Book> findByTitle(String title){
        return em.createQuery("SELECT b FROM Book b WHERE b.title LIKE : title", Book.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }

    public List<Book> findByAuthor(String author){
        return em.createNamedQuery("Book.findByAuthor", Book.class)
                .setParameter("author", author)
                .getResultList();
    }

    public Book save(Book book) {
        logger.info("Create book: {}", book);
        em.persist(book);
        return book;
    }

    public Book autoupdateBook(Long id, String newTitle, String author){
        logger.info("Updating book with ID : {}", id);
        Book book = em.find(Book.class, id);
        if(book != null){
            book.setTitle(newTitle);
            book.setAuthor(author);
            logger.info("Modified book in context: {}", book);
        }
        return book;
    }

    public void deleteByID(Long id) {
        logger.info("Deleting book with ID : {}", id);
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
    }
}
