package commands.builders;

import com.google.inject.Inject;
import commands.Command;
import commands.CommandSelect;
import commands.CommandUpdate;
import commands.factories.ConsoleCommandsFactory;
import model.Params;
import org.apache.commons.lang3.StringUtils;
import services.StorageService;

/**
 * Created by Ololoev on 14.01.2016.
 */
public class CommandSelectBuilder extends AbstractCommandBuilder
{

    @Inject
    public CommandSelectBuilder(StorageService storageService)
    {
        super(storageService);
    }

    @Override
    public Command createCommand(Params params)
    {
        String[] args = null;

        if (StringUtils.isNotEmpty(params.getCommandArgs()))
            args = StringUtils.split(params.getCommandArgs());

        if (args == null || args.length != 1)
            return ConsoleCommandsFactory.getInstance().createUnknownCommand(params);

        Command select = new CommandSelect(args[0], getStorage());

        return select;

    }
}
