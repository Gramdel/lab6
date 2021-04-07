package commands;

import collection.Product;

import java.util.ArrayList;
import java.util.Collections;

import static core.Main.getCollection;

public class RemoveGreater extends Command{
    public RemoveGreater() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException{
        if (caller != null) argCount = 11;
        rightArg(args);
        if (getCollection().size() > 0) {
            int prevSize = getCollection().size();

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
                throw new ExecuteException("Удаление из коллекции элементов, цена которых больше цены данного, невозможно из-за ошибок ввода:"+e.getMessage());
            }

            ArrayList<Product> sortedCollection = new ArrayList<>(getCollection());
            Collections.sort(sortedCollection);
            Product product = sortedCollection.get(sortedCollection.size() - 1);

            RemoveById removeById = new RemoveById();
            removeById.hideSuccessMsg();
            for (int i = 0; i < sortedCollection.size() - 1; i++) {
                if (Product.byPriceComparator.compare(sortedCollection.get(i), product) > 0) {
                    ArrayList<String> arg = new ArrayList<>();
                    arg.add(sortedCollection.get(i).getId().toString());
                    removeById.execute(arg, null);
                }
            }
            ArrayList<String> arg = new ArrayList<>();
            arg.add(product.getId().toString());
            removeById.execute(arg, null);

            if (prevSize > getCollection().size()) {
                System.out.println("Элементы, цена которых больше цены данного, успешно удалены!");
            } else {
                throw new ExecuteException("В коллекции нет элементов, цена которых больше цены данного, ничего не удалено.");
            }
        } else {
            throw new ExecuteException("Т.к. коллекция пуста, невозможно удалить из неё элементы, цена которых больше цены данного.");
        }
    }

    @Override
    public String description() {
        return "Удаляет из коллеккции все элементы, цена которых больше цены данного." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: remove_greater \n\t\t(В скриптах - remove_greater {element}, где {element} - JSON-строка)";
    }
}
