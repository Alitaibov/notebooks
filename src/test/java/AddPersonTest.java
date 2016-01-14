import model.Age;
import model.Book;
import model.Person;
import model.Phone;
import org.junit.Test;
import services.JDBCStorageService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
/**
 * Created by Ololoev on 14.01.2016.
 */
public class AddPersonTest {
    @Test
    public void equalsTest()
    {
        Book book = JDBCStorageService.TransactionScript.getInstance().defaultBook();

        JDBCStorageService.TransactionScript.getInstance().addPerson("Ivan", "123456", "19", book);

        List<Person> persons = JDBCStorageService.TransactionScript.getInstance().listPersons();

        Boolean isFound = persons.stream()
            .filter(p -> p.getName().equals("Ivan") && p.getPhones().stream().anyMatch(phone -> phone.getPhone().equals("123456")) && p.getAge().getAge().equals("19"))
            .findFirst()
            .map(p -> true).orElse(false);

        assertTrue("Add person service method", isFound);
    }
}
