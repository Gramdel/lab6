package commands;

import java.util.Stack;

import static core.Main.getHistory;

public class History extends Command {
    private Stack<String> history;

    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        if (!arg.matches("\\s*")) {
            System.out.println("У команды history не может быть аргументов!");
            return false;
        }
        history = getHistory();
        return true;
    }

    @Override
    public synchronized String execute() {
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
