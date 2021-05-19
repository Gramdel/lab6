package commands;

import collection.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import core.Creator;

import static core.Main.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Add extends Command {
    private Product product;

    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        Product product = null;
        try {
            if (isInteractive) {
                if (!arg.matches("\\s*")){
                    throw new IllegalArgumentException("У команды add не может быть аргументов!");
                }
            } else {
                if (!arg.matches("\\s*\\{.*}\\s*")){
                    throw new IllegalArgumentException("У команды add должен быть 1 аргумент: JSON-строка!");
                } else {
                    Matcher m = Pattern.compile("\\{.*}").matcher(arg);
                    if (m.find()) {
                        product = new Gson().fromJson(m.group(), Product.class);
                    }
                }
            }
            product = Creator.createProduct(product, isInteractive);
            if (product == null) {
                System.out.println("Команда add не выполнена!");
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
        if (getOrganizations().contains(product.getManufacturer())) {
            for (Organization o : getOrganizations()) {
                if (o.equals(product.getManufacturer())) {
                    product.setManufacturer(o);
                    break;
                }
            }
        } else {
            product.getManufacturer().createId();
            getOrganizations().add(product.getManufacturer());
        }
        product.createId();
        getCollection().add(product);
        return "Элемент успешно добавлен в коллекцию!";
    }

    @Override
    public String description() {
        return "Добавляет новый элемент в коллекцию."+syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: add \n\t\t(В скриптах - add {element}, где {element} - JSON-строка)";
    }
}