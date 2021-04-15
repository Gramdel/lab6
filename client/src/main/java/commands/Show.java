package commands;

import collection.Product;
import static core.Main.getCollection;

public class Show extends Command {
    public Show() {
        super(0);
    }

    @Override
    public void prepare(String arg, boolean isInteractive) {
        if (!arg.matches("\\s*")) {
            System.out.println("У команды show не может быть аргументов!");
        }
    }

    @Override
    public String execute() {
        if (getCollection().size() > 0) {
            StringBuilder msg = new StringBuilder();
            getCollection().stream().sorted(Product.byIdComparator).forEach(p -> msg.append("\n").append(p));
            return "Элементы коллекции:" + msg.toString();
        } else {
            return "Коллекция пуста!";
        }
    }

    @Override
    public String description() {
        return "Выводит коллекцию." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: show";
    }
}
