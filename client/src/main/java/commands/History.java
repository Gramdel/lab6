package commands;

import java.util.ArrayList;

import static core.Main.getInterpreter;

public class History extends Command {
    public History() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException{
        rightArg(args);
        if (getInterpreter().getHistory().size() > 0) {
            System.out.println("Комманда history выполнена, последние 7 комманд:");
            for (String s : getInterpreter().getHistory())
                System.out.println("\t" + s);
        } else {
            throw new ExecuteException("Список выполненных комманд пуст!");
        }
    }

    @Override
    public String description() {
        return "Выводит последние 7 комманд." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: history";
    }
}
