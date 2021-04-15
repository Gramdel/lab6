package commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static core.Main.*;

public class Info extends Command {
    public Info() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        rightArg(args);
        System.out.println("Тип коллекции:");
        System.out.println(getCollection().getClass());
        System.out.println("Дата инициализации коллекции:");
        System.out.println(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(getDate()));
        System.out.println("Количество элементов коллекции:");
        System.out.println(getCollection().size());
    }

    @Override
    public void prepare(String arg, boolean isInteractive) {
        if (!arg.matches("\\s*")) {
            System.out.println("У команды info не может быть аргументов!");
        }
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
