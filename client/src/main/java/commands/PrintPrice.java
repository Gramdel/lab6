package commands;

import collection.Product;
import java.util.ArrayList;
import static core.Main.getCollection;

public class PrintPrice extends Command {
    public PrintPrice() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        rightArg(args);
        if (getCollection().size() > 0) {
            System.out.println("Цены продуктов в коллекции в порядке убывания:");
            ArrayList<Product> sortedCollection = new ArrayList<>(getCollection());
            sortedCollection.sort(Product.byPriceComparator.reversed());
            for (Product product : sortedCollection) {
                System.out.println("\t"+product.getPrice());
            }
        } else {
            throw new ExecuteException("Невозможно вывести цены продуктов, потому что коллекция пуста!");
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
