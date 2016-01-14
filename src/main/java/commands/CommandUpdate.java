package commands;

import controllers.ApplicationContext;
import services.StorageService;

/**
 * Created by Ololoev on 14.01.2016.
 */
public class CommandUpdate extends AbstractCommand
{
    public static final String NAME = "update";


    public CommandUpdate(String id, String person, String phone, String age, StorageService storage)
    {
        super(storage);
        this.id = Integer.valueOf(id);
        this.person = person;
        this.phone = phone;
        this.age = age;
    }

    @Override
    public void execute(ApplicationContext ap)
    {

        getStorage().update(this.id, this.person, this.phone, this.age);

        System.out.println(getName() + ": person " + this.person + " was updated in the book, phone is: " + this.phone + " and age is: " + this.age);
    }

    @Override
    public String getName() {
        return NAME;
    }

    private String person;
    private String phone;

    private String age;
    private Integer id;

}
