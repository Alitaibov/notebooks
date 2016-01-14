package services;

import com.google.inject.Singleton;
import configs.DBConnection;
import model.Age;
import model.Book;
import model.Person;
import model.Phone;
import org.apache.commons.lang3.StringUtils;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * (c) Roman Gordeev
 * <p/>
 * 2014 июн 18
 */
@Singleton
public class JDBCStorageService implements StorageService
{
    @Override
    public void add(String personName, String phone, String age)
    {
        TransactionScript.getInstance().addPerson(personName, phone, age, defaultBook());
    }

    @Override
    public void delete(Integer id)
    {
        TransactionScript.getInstance().deletePerson(id, defaultBook());
    }

    @Override
    public void update(Integer id, String personName, String phone, String age)
    {
        TransactionScript.getInstance().updatePerson(id, personName, phone, age, defaultBook());
    }

    @Override
    public void select(Integer id)
    {
        TransactionScript.getInstance().selectPerson(id, defaultBook());
    }

    @Override
    public List<Person> list()
    {
        return TransactionScript.getInstance().listPersons();
    }

    @Override
    public Book defaultBook()
    {
        return TransactionScript.getInstance().defaultBook();
    }

    @Override
    public void close()
    {
        try
        {
            TransactionScript.getInstance().close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static final class TransactionScript
    {
        private static final TransactionScript instance = new TransactionScript();

        public static TransactionScript getInstance() {
            return instance;
        }

        public TransactionScript()
        {
            String url      = DBConnection.JDBC.url();
            String login    = DBConnection.JDBC.username();
            String password = DBConnection.JDBC.password();

            try
            {
                connection = DriverManager.getConnection(url, login,
                        password);
            } catch (Exception e)
            {
                e.printStackTrace();
            };
        }

        public List<Person> listPersons()
        {
            List<Person> result = new ArrayList<>(10);

            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "select name, phone, age from book b \n" +
                        "inner join person p on b.id = p.book_id \n" +
                        "inner join phone ph on p.id = ph.person_id\n" +
                        "inner join age a on p.id = a.person_id\n");

                ResultSet r_set = statement.executeQuery();

                while (r_set.next())
                {
                    Person p = new Person(r_set.getString("name"));
                    Phone ph = new Phone(p, r_set.getString("phone"));
                    Age a = new Age(p, r_set.getString("age"));
                    p.getPhones().add(ph);
                    p.setAge(a);
                    result.add(p);
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }

            return result;
        }

        public void addPerson(String person, String phone, String age, Book book)
        {
            try
            {
                if (book.getId() == null)
                {
                    PreparedStatement addBook = connection.prepareStatement("insert into book (id) values (DEFAULT)", Statement.RETURN_GENERATED_KEYS);
                    addBook.execute();
                    ResultSet generated_book_id = addBook.getGeneratedKeys();

                    if (generated_book_id.next())
                        book.setId(generated_book_id.getLong("id"));
                }

                PreparedStatement addPerson = connection.prepareStatement("insert into person (book_id, name) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
                PreparedStatement addPhone  = connection.prepareStatement("insert into phone (person_id, phone) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
                PreparedStatement addAge  = connection.prepareStatement("insert into age (person_id, age) values (?, ?)", Statement.RETURN_GENERATED_KEYS);

                addPerson.setLong(1, book.getId());
                addPerson.setString (2, person);

                addPerson.execute();

                ResultSet auto_pk = addPerson.getGeneratedKeys();
                while (auto_pk.next())
                {
                    int id = auto_pk.getInt("id");
                    addPhone.setInt(1, id);
                    addPhone.setString(2, phone);
                    addPhone.execute();
                    addAge.setInt(1, id);
                    addAge.setString(2, age);
                    addAge.execute();
                }



            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void selectPerson(Integer id, Book book)
        {
            try
            {
                if (book.getId() == null)
                {
                    throw  new Exception("Table does not exist");
                }

                PreparedStatement name = connection.prepareStatement("Select name from person where book_id = ? and id = ?");
                PreparedStatement phone = connection.prepareStatement("Select phone from phone where person_id = ?");
                PreparedStatement age = connection.prepareStatement("Select age from age where person_id = ?");

                name.setLong(1, book.getId());
                name.setInt(2, id);
                ResultSet queryName = name.executeQuery();
                while (queryName.next())
                {
                    System.out.println("Name: " + queryName.getString("name"));
                }
                phone.setLong(1, id);
                ResultSet queryPhone = phone.executeQuery();
                while (queryPhone.next())
                {
                    System.out.println("Phone: " + queryPhone.getString("phone"));
                }
                age.setLong(1, id);
                ResultSet queryAge = age.executeQuery();
                while (queryAge.next())
                {
                    System.out.println("Age: " + queryAge.getString("age"));
                }



            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void updatePerson(Integer id, String person, String phone, String age, Book book)
        {
            try
            {
                if (book.getId() == null)
                {
                    throw  new Exception("Table does not exist");
                }

                PreparedStatement select = connection.prepareStatement("Select name from person where book_id = ? and id = ?");
                select.setLong (1, book.getId());
                select.setInt(2, id);

                ResultSet query = select.executeQuery();

                while (query.next())
                {
                    PreparedStatement updatePerson = connection.prepareStatement("update person set name = ? where book_id = ? and id = ?");
                    PreparedStatement updatePhone = connection.prepareStatement("update phone set phone = ? where person_id = ?");
                    PreparedStatement updateAge = connection.prepareStatement("update age set age = ? where person_id = ?");

                    updatePhone.setString(1, phone);
                    updatePhone.setLong(2, id);
                    updatePhone.executeUpdate();
                    updateAge.setString(1, age);
                    updateAge.setLong(2, id);
                    updateAge.executeUpdate();

                    updatePerson.setString(1, person);
                    updatePerson.setLong(2, book.getId());
                    updatePerson.setLong(3, id);
                    updatePerson.executeUpdate();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void deletePerson(Integer id, Book book)
        {
            try
            {
                PreparedStatement deletePerson = connection.prepareStatement("delete from person where book_id = ? and id = ?");
                PreparedStatement deletePhone  = connection.prepareStatement("delete from phone where person_id = ?");
                PreparedStatement deleteAge  = connection.prepareStatement("delete from age where person_id = ?");

                deletePhone.setLong(1, id);
                deletePhone.executeUpdate();
                deleteAge.setLong(1, id);
                deleteAge.executeUpdate();

                deletePerson.setLong(1, book.getId());
                deletePerson.setLong(2, id);
                deletePerson.executeUpdate();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public Book defaultBook()
        {
            // создаем новый экземпляр, который в дальнейшем и сохраним,
            // если не найдем для него записи в БД
            Book book = new Book();

            try
            {
                Statement statement = connection.createStatement();
                // выбираем из таблицы book единственную запись
                ResultSet books = statement.executeQuery("select id from book limit 1");
                // если хоть одна зепись в таблице нашлась, инициализируем наш объект полученными значениями
                if (books.next())
                {
                    book.setId(books.getLong("id"));
                }

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

            // возвращаем проинициализированный или пустой объект книги
            return book;
        }

        public void close() throws SQLException
        {
            if (connection != null && !connection.isClosed())
                connection.close();
        }

        private Connection connection;
    }
}
