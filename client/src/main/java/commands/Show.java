package commands;

import collection.Product;
import java.util.ArrayList;
import static core.Main.getCollection;

public class Show extends Command {
    public Show() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        rightArg(args);
        if (getCollection().size() > 0) {
            System.out.println("Элементы коллекции:");
            ArrayList<Product> sortedCollection = new ArrayList<>(getCollection());
            sortedCollection.sort(Product.byIdComparator);
            for (Product product : sortedCollection) {
                System.out.println(product);
            }
        } else {
            throw new ExecuteException("Коллекция пуста!");
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
