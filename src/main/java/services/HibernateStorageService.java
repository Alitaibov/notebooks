package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.Book;
import model.Person;
import model.Phone;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * (c) Roman Gordeev
 * <p/>
 * 2014 июн 25
 */
@Singleton
public class HibernateStorageService implements StorageService
{
    @Inject
    public HibernateStorageService(EntityManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void add(String personName, String phone, String age)
    {
        Book   book   = defaultBook();
        Person person = new Person(personName);
        Phone  ph     = new Phone(person, phone);
        person.getPhones().add(ph);

        book.getPersons().add(person);

        manager.getTransaction().begin();
        manager.persist(ph);
        manager.persist(person);
        manager.persist(book);

        manager.getTransaction().commit();

    }

    @Override
    public void update(Integer id, String personName, String phone, String age)
    {
        //
    }

    @Override
    public void delete(Integer id)
    {
        //
    }

    @Override
    public void select(Integer id)
    {
        //
    }

    @Override
    public List<Person> list() {
        return manager.createQuery("select p from model.Person p").getResultList();
    }

    public Book defaultBook()
    {
        List<Book> books = manager.createQuery("from model.Book").setMaxResults(1).getResultList();
        if (books.isEmpty())
            return new Book();
        return books.get(0);
    }

    @Override
    public void close()
    {
        manager.getEntityManagerFactory().close();
    }

    private EntityManager manager;
}
