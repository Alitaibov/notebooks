package commands;

import controllers.ApplicationContext;
import services.StorageService;

/**
* User: rgordeev
* Date: 25.06.14
* Time: 17:20
*/
public class CommandAdd extends AbstractCommand
{
    public static final String NAME = "add";


    public CommandAdd(String person, String phone, String age, StorageService storage)
    {
        super(storage);
        this.person = person;
        this.phone = phone;
        this.age = age;
    }

    @Override
    public void execute(ApplicationContext ap)
    {

        getStorage().add(this.person, this.phone, this.age);

        System.out.println(getName() + ": person " + this.person + " was added to the book, phone is: " + this.phone + " and age is: " + this.age);
    }

    @Override
    public String getName() {
        return NAME;
    }

    private String person;
    private String phone;

    private String age;

}
