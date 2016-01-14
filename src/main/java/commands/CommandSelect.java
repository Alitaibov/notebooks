package commands;

import controllers.ApplicationContext;
import services.StorageService;

/**
 * Created by Ololoev on 14.01.2016.
 */
public class CommandSelect extends AbstractCommand
{
    public static final String NAME = "select";


    public CommandSelect(String id, StorageService storage)
    {
        super(storage);
        this.id = Integer.valueOf(id);
    }

    @Override
    public void execute(ApplicationContext ap)
    {
        getStorage().select(this.id);
    }

    @Override
    public String getName() {
        return NAME;
    }

    private Integer id;

}
