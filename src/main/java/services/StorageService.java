package services;

import model.Book;
import model.Person;

import java.util.List;

/**
 * (c) Roman Gordeev
 * <p/>
 * 2014 июн 18
 */
public interface StorageService
{
    void add(String personName, String phone, String age);
    void delete(Integer id);
    void update(Integer id, String personName, String phone, String age);
    void select(Integer id);
    List<Person> list();

    Book defaultBook();

    void close();
}
