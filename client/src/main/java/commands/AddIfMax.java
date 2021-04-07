package commands;

import collection.Product;

import java.util.ArrayList;
import java.util.Collections;

import static core.Main.getCollection;

public class AddIfMax extends Command{
    public AddIfMax() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        if (caller != null) argCount = 11;
        rightArg(args);

        if (getCollection().size() > 0) {
            try {
                Add add = new Add();
                add.hideSuccessMsg();
                add.hideFailureMsg();
                if (args.size() > 0) {
                    add.execute(args, this);
                } else {
                    add.execute(args, null);
                }
            } catch (ExecuteException e) {
                throw new ExecuteException("Невозможно сравнить цену добавляемого элемента из-за ошибок при его вводе:"+e.getMessage());
            }
            ArrayList<Product> sortedCollection = new ArrayList<>(getCollection());
            Collections.sort(sortedCollection);
            Product product = sortedCollection.get(sortedCollection.size() - 1);
            sortedCollection.sort(Product.byPriceComparator);

            if (sortedCollection.get(sortedCollection.size()-1).getPrice()==product.getPrice()) {
                System.out.println("Элемент добавлен в коллекцию, т.к. его цена - наибольшая в коллекции!");
            } else {
                RemoveById removeById = new RemoveById();
                removeById.hideSuccessMsg();
                ArrayList<String> arg = new ArrayList<>();
                arg.add(product.getId().toString());
                removeById.execute(arg, null);
                throw new ExecuteException("Элемент не добавлен в коллекцию, т.к. его цена не является наибольшей ценой элемента в коллекции.");
            }
        } else {
            throw new ExecuteException("Коллекция пуста, поэтому цену добавляемого элемента не с чем сравнить.");
        }
    }

    @Override
    public String description() {
        return "Добавляет новый элемент в коллекцию, если его цена - наибольшая в коллекции." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: add_if_max \n\t\t(В скриптах - add_if_max {element}, где {element} - JSON-строка)";
    }
}
