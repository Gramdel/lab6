package commands;

import collection.Product;

import static core.Main.getCollection;

public class PrintPrice extends Command {
    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        if (!arg.matches("\\s*")) {
            System.out.println("У команды print_field_descending_price не может быть аргументов!");
            return false;
        }
        return true;
    }

    @Override
    public String execute() {
        if (getCollection().size() > 0) {
            StringBuilder s = new StringBuilder();
            getCollection().stream().sorted(Product.byPriceComparator.reversed()).forEach(product -> s.append("\n\t").append(product.getPrice()));
            return "Цены продуктов в коллекции в порядке убывания:" + s.toString();
        } else {
            return "Невозможно вывести цены продуктов, потому что коллекция пуста!";
        }
    }

    @Override
    public String description() {
        return "Выводит цены продуктов в коллекции в порядке убывания." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: print_field_descending_price";
    }
}
