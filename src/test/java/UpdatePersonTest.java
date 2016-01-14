import model.Book;
import model.Person;
import org.junit.Test;
import services.JDBCStorageService;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Ololoev on 14.01.2016.
 */
public class UpdatePersonTest {
    @Test
    public void equalsTest()
    {
        Book book = JDBCStorageService.TransactionScript.getInstance().defaultBook();

        JDBCStorageService.TransactionScript.getInstance().addPerson("Ivan", "123456", "19", book);

        List<Person> persons = JDBCStorageService.TransactionScript.getInstance().listPersons();

        long id1 = persons.stream()
                .filter(p -> p.getName().equals("Ivan") && p.getPhones().stream().anyMatch(phone -> phone.getPhone().equals("123456")) && p.getAge().getAge().equals("19"))
                .findFirst()
                .map(p -> p.getId()).orElse(-1L);

        JDBCStorageService.TransactionScript.getInstance().updatePerson((int)id1, "Petr", "654321", "91", book);

        persons = JDBCStorageService.TransactionScript.getInstance().listPersons();

        long id2 = persons.stream()
                .filter(p -> p.getName().equals("Petr") && p.getPhones().stream().anyMatch(phone -> phone.getPhone().equals("654321")) && p.getAge().getAge().equals("91"))
                .findFirst()
                .map(p -> p.getId()).orElse(-1L);

        assertEquals("Update person service method", id1, id2);
    }
}