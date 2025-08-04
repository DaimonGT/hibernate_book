package by.bogdanov.repository;

import by.bogdanov.model.Book;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;

public class BookRepository {
    private final EntityManagerFactory emf;
    @Inject
    public BookRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntitymanager(){
        return emf.createEntityManager();
    }

    public List<Book> findAll(){
        return getEntitymanager().createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public Optional<Book> findById(Long id){
        Book book = getEntitymanager().find(Book.class, id);
        return Optional.ofNullable(book);
    }

    public Book save(Book book){
        EntityManager entityManager = getEntitymanager();
        entityManager.getTransaction().begin();
        Book bookResult = null;
        if(book.getId() == null){
            entityManager.persist(book);
            entityManager.flush();
            bookResult = book;
        } else {
            bookResult = entityManager.merge(book);
        }
        entityManager.getTransaction().commit();;
        return bookResult;
    }
}
