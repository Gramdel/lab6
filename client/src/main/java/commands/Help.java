package commands;

import java.util.ArrayList;
import java.util.Map;

import static core.Main.getInterpreter;

public class Help extends Command {
    public Help() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        rightArg(args);
        System.out.println("Список допустимых команд:");
        for (Map.Entry<Command, String> entry : getInterpreter().getCommands().entrySet())
            System.out.println("\t" + entry.getValue() + " - " + entry.getKey().description());
    }

    @Override
    public String description() {
        return "Выводит справку по доступным коммандам." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: help";
    }
}
