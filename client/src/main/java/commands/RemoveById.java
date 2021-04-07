package commands;

import collection.Product;

import java.util.ArrayList;
import java.util.Iterator;

import static core.Main.getCollection;
import static core.Main.getOrganizations;

public class RemoveById extends Command {
    private boolean successMsg = true;

    public RemoveById() {
        super(1);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        rightArg(args);
        int prevSize = getCollection().size();
        for (Iterator<Product> iter = getCollection().iterator(); iter.hasNext(); ) {
            Product product = iter.next();
            if (args.get(0).equals(product.getId().toString())) {
                if (caller != null) caller.setReceivedProduct(product);
                iter.remove();
                if (successMsg) System.out.println("Элемент с id "+args.get(0)+" успешно удалён!");

                boolean removeOrg = true;
                for (Product p : getCollection()) {
                    if (p.getManufacturer().equals(product.getManufacturer())) {
                        removeOrg = false;
                        break;
                    }
                }
                if (removeOrg) getOrganizations().remove(product.getManufacturer());
                break;
            }
        }
        if (prevSize == getCollection().size()) throw new ExecuteException("В коллекции нет элемента с id "+args.get(0)+"!");
    }

    @Override
    public String description() {
        return "Удаляет элемент из коллекции по его id." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: remove_by_id id, где id - целое положительное число.";
    }

    @Override
    protected void rightArg(ArrayList<String> args) throws ExecuteException {
        super.rightArg(args);
        try {
            Long.parseLong(args.get(0));
        } catch (NumberFormatException e) {
            throw new ExecuteException("Неправильный ввод id! Требуемый формат: положительное целое число.");
        }
    }

    public void hideSuccessMsg() {
        successMsg = false;
    }
}
