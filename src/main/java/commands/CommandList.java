package commands;

import controllers.ApplicationContext;
import model.Age;
import model.Person;
import model.Phone;
import services.StorageService;

import java.util.List;

/**
* User: rgordeev
* Date: 25.06.14
* Time: 17:21
*/
public class CommandList extends AbstractCommand
{
    public static final String NAME = "list";


    public CommandList(StorageService storage)
    {
        super(storage);
    }

    @Override
    public void execute(ApplicationContext ap)
    {
        List<Person> persons = getStorage().list();

        for (Person p : persons)
            printPerson(p);

    }

    private void printPerson(Person person)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Person: ").append(person.getName()).append("\n").append("phones: ");
        for (Phone phone : person.getPhones())
            sb.append(phone.getPhone()).append("\n");
        Age age = person.getAge();
        sb.append("Age: ").append(age.getAge()).append("\n");
        System.out.println(sb.toString());
    }

    @Override
    public String getName() {
        return NAME;
    }

}
