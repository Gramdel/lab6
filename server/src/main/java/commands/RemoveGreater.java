package commands;

import collection.Product;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import core.Creator;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.Main.getCollection;
import static core.Main.getOrganizations;

public class RemoveGreater extends Command {
    private Product product;

    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        Product product = null;
        try {
            if (isInteractive) {
                if (!arg.matches("\\s*")){
                    throw new IllegalArgumentException("У команды remove_greater не может быть аргументов!");
                }
            } else {
                if (!arg.matches("\\s*\\{.*}\\s*")){
                    throw new IllegalArgumentException("У команды remove_greater должен быть 1 аргумент: JSON-строка!");
                } else {
                    Matcher m = Pattern.compile("\\{.*}").matcher(arg);
                    if (m.find()) {
                        product = new Gson().fromJson(m.group(), Product.class);
                        product.getManufacturer().createId();
                    }
                }
            }
            product = Creator.createProduct(product,isInteractive);
            if (product == null) {
                System.out.println("Команда remove_greater не выполнена!");
                return false;
            }
        } catch (JsonSyntaxException | NumberFormatException e) {
            System.out.println("Ошибка в синтаксисе JSON-строки!");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        this.product = product;
        return true;
    }

    @Override
    public String execute() {
        int prevSize = getCollection().size();
        if (prevSize == 0) {
            return "Т.к. коллекция пуста, невозможно удалить из неё элементы, цена которых больше цены данного.";
        } else {
            for (Iterator<Product> iter = getCollection().iterator(); iter.hasNext(); ) {
                Product product = iter.next();
                if (this.product.getPrice() < product.getPrice()) {
                    if (getCollection().stream().filter(x -> x.getManufacturer().equals(product.getManufacturer())).count() == 1) {
                        getOrganizations().remove(product.getManufacturer());
                    }
                    iter.remove();
                }
            }
            getCollection().stream().filter(x -> x.getPrice() > product.getPrice()).forEach(x -> getCollection().remove(x));
            if (prevSize > getCollection().size()) {
                return "Элементы, цена которых больше цены данного, успешно удалены!";
            } else {
                return "В коллекции нет элементов, цена которых больше цены данного, ничего не удалено.";
            }
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