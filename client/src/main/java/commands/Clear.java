package commands;

import java.util.ArrayList;

import static core.Main.getCollection;

public class Clear extends Command {
    public Clear() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        rightArg(args);
        if(getCollection().size()>0) {
            getCollection().clear();
            System.out.println("Коллекция очищена.");
        } else {
            throw new ExecuteException("Коллекция уже очищена!");
        }
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
