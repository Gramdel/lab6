package commands;

import core.ParseUnit;
import java.util.ArrayList;

public class Save extends Command {
    public Save() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        rightArg(args);
        ParseUnit.toCSV();
    }

    @Override
    public String description() {
        return "Сохраняет коллекцию." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: save";
    }
}

