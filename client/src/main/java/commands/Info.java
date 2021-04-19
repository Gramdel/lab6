package commands;

import java.text.SimpleDateFormat;

import static core.Main.getCollection;
import static core.Main.getDate;

public class Info extends Command {
    public Info() {
        super(0);
    }

    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        if (!arg.matches("\\s*")) {
            System.out.println("У команды info не может быть аргументов!");
            return false;
        }
        return true;
    }

    @Override
    public String execute() {
        return "Тип коллекции:\n" + getCollection().getClass() + "\n" + "Дата инициализации коллекции:\n" + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(getDate()) + "\n" + "Количество элементов коллекции:\n" + getCollection().size();
    }

    @Override
    public String description() {
        return "Выводит информацию о коллекции." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: info";
    }
}
