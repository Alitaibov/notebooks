package commands;

import controllers.ApplicationContext;
import services.StorageService;

/**
 * Created by Ololoev on 12.01.2016.
 */
public class CommandDelete extends AbstractCommand
{
    public static final String NAME = "delete";


    public CommandDelete(String id, StorageService storage)
    {
        super(storage);
        this.id = Integer.valueOf(id);
    }

    @Override
    public void execute(ApplicationContext ap)
    {
        getStorage().delete(this.id);

        System.out.println("Person with ID: " + id + " was deleted from the book");
    }

    @Override
    public String getName() {
        return NAME;
    }

    private Integer id;
}
