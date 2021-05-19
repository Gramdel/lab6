package commands;

import collection.Organization;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import core.Creator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.Main.getCollection;

public class FilterByManufacturer extends Command {
    private Organization manufacturer;

    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        Organization manufacturer = null;
        try {
            if (isInteractive) {
                if (!arg.matches("\\s*")){
                    throw new IllegalArgumentException("У команды filter_by_manufacturer не может быть аргументов!");
                }
            } else {
                if (!arg.matches("\\s*\\{.*}\\s*")){
                    throw new IllegalArgumentException("У команды filter_by_manufacturer должен быть 1 аргумент: JSON-строка!");
                } else {
                    Matcher m = Pattern.compile("\\{.*}").matcher(arg);
                    if (m.find()) {
                        manufacturer = new Gson().fromJson(m.group(), Organization.class);
                    }
                }
            }
            manufacturer = Creator.createManufacturer(manufacturer,isInteractive);
            if (manufacturer == null) {
                System.out.println("Команда filter_by_manufacturer не выполнена!");
                return false;
            }
        } catch (JsonSyntaxException | NumberFormatException e) {
            System.out.println("Ошибка в синтаксисе JSON-строки!");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        this.manufacturer = manufacturer;
        return true;
    }

    @Override
    public String execute() {
        StringBuilder s = new StringBuilder();
        getCollection().stream().filter(x -> x.getManufacturer().equals(manufacturer)).forEach(x -> s.append("\n").append(x));
        if (s.toString().isEmpty()) {
            return "В коллекции нет элементов с manufacturer " + manufacturer + "!";
        } else {
            return "Элементы коллекции с manufacturer " + manufacturer + ":" + s.toString();
        }
    }

    @Override
    public String description() {
        return "Выводит элементы коллекции с определённой компанией-производителем." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: filter_by_manufacturer \n\t\t(В скриптах - filter_by_manufacturer {element}, где {element} - JSON-строка)";
    }
}