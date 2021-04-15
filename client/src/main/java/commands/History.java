package commands;

import java.util.ArrayList;
import java.util.LinkedList;

import static core.Main.getInterpreter;

public class History extends Command {
    private LinkedList<String> history;

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
    public void prepare(String arg, boolean isInteractive) {
        if (!arg.matches("\\s*")) {
            System.out.println("У команды history не может быть аргументов!");
        }
        history = getInterpreter().getHistory();
    }

    @Override
    public String execute() {
        if (history.size() > 0) {
            StringBuilder s = new StringBuilder();
            history.forEach(x -> s.append("\n\t").append(x));
            return "Комманда history выполнена, последние 7 комманд:" + s.toString();
        } else {
            return "Список выполненных комманд пуст!";
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
