package commands;

import core.Interpreter;

import static core.Main.getInterpreter;

public class Help extends Command {
    private Interpreter interpreter;

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
    public synchronized String execute() {
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
