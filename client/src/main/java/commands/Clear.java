package commands;

import java.util.ArrayList;

import static core.Main.getCollection;

public class Clear extends Command {
    public Clear() {
        super(0);
    }

    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        if (!arg.matches("\\s*")) {
            System.out.println("У команды clear не может быть аргументов!");
            return false;
        }
        return true;
    }

    @Override
    public String execute() {
        if(getCollection().size()>0) {
            getCollection().clear();
            return "Коллекция очищена.";
        }
        return "Коллекция пуста, нечего чистить!";
    }

    @Override
    public String description() {
        return "Очищает коллекцию." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: clear";
    }
}
