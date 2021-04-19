package commands;

import core.Interpreter;

import java.util.ArrayList;
import java.util.Map;

import static core.Main.getInterpreter;

public class Help extends Command {
    private Interpreter interpreter;

    public Help() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        rightArg(args);
        System.out.println("Список допустимых команд:");
        getInterpreter().getCommands().forEach((commandName,command) -> System.out.println("\t" + commandName + " - " + command.description()));
        for (Map.Entry<String, Command> entry : getInterpreter().getCommands().entrySet())
            System.out.println("\t" + entry.getKey() + " - " + entry.getValue().description());
    }

    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        if (!arg.matches("\\s*")) {
            System.out.println("У команды help не может быть аргументов!");
            return false;
        }
        interpreter = getInterpreter();
        return true;
    }

    @Override
    public String execute() {
        StringBuilder s = new StringBuilder();
        interpreter.getCommands().forEach((commandName,command) -> s.append("\n\t").append(commandName).append(" - ").append(command.description()));
        return "Список допустимых команд:" + s.toString();
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
