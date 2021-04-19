package commands;

import collection.Product;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import core.Creator;

import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.Main.getCollection;

public class AddIfMax extends Command{
    private Product product;

    public AddIfMax() {
        super(0);
    }

    @Override
    public String description() {
        return "Добавляет новый элемент в коллекцию, если его цена - наибольшая в коллекции." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: add_if_max \n\t\t(В скриптах - add_if_max {element}, где {element} - JSON-строка)";
    }

    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        Product product = null;
        try {
            if (isInteractive) {
                if (!arg.matches("\\s*")){
                    throw new IllegalArgumentException("У команды add_if_max не может быть аргументов!");
                }
            } else {
                if (!arg.matches("\\s*\\{.*}\\s*")){
                    throw new IllegalArgumentException("У команды add_if_max должен быть 1 аргумент: JSON-строка!");
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
                System.out.println("Команда add_if_max не выполнена!");
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
        try {
            if (product.getPrice() >= getCollection().stream().max(Product.byPriceComparator).get().getPrice()) {
                getCollection().add(product);
                return "Элемент добавлен в коллекцию, т.к. его цена - наибольшая в коллекции!";
            } else {
                return "Элемент не добавлен в коллекцию, т.к. его цена - НЕ наибольшая в коллекции.";
            }
        } catch (NoSuchElementException e) {
            return "Элемент добавлен в коллекцию вне зависимости от цены, потому что коллекция пуста!";
        }
    }
}
