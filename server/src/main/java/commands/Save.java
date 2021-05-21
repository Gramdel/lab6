package commands;

import core.CSVUnit;

public class Save extends Command {
    @Override
    public synchronized String execute() {
        return CSVUnit.write();
    }

    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        if (!arg.matches("\\s*")) {
            System.out.println("У команды save не может быть аргументов!");
            return false;
        }
        return true;
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

