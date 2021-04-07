package commands;

import collection.Product;
import collection.UnitOfMeasure;

import java.util.ArrayList;
import java.util.Iterator;
import static core.Main.getCollection;

public class RemoveByUOM extends Command {
    public RemoveByUOM() {
        super(1);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        rightArg(args);
        int prevSize = getCollection().size();
        for (Iterator<Product> iter = getCollection().iterator(); iter.hasNext(); ) {
            if (args.get(0).equals(iter.next().getUnitOfMeasure().toString())) {
                iter.remove();
                break;
            }
        }
        if (getCollection().size() < prevSize) {
            throw new ExecuteException("С коллекции удалён один из элементов с unitOfMeasure " + args.get(0) + ".");
        } else {
            throw new ExecuteException("В коллекции нет ни одного элемента с unitOfMeasure " + args.get(0) + "!");
        }
    }

    @Override
    public String description() {
        return "Удаляет из коллекции один из элементов с unitOfMeasure эквивалентным заданному." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: remove_any_by_unit_of_measure unitOfMeasure, где unitOfMeasure: " + UnitOfMeasure.valueList() + ".";
    }

    @Override
    protected void rightArg(ArrayList<String> args) throws ExecuteException{
        super.rightArg(args);
        try {
            UnitOfMeasure.fromString(args.get(0));
        } catch (IllegalArgumentException e) {
            throw new ExecuteException("Неправильный ввод единиц измерения! Возможные варианты ввода: " + UnitOfMeasure.valueList() + ".");
        }
    }
}
