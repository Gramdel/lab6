package core;

import collection.Product;
import collection.Organization;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    private static final LinkedHashSet<Product> collection = new LinkedHashSet<>();
    private static final ArrayList<Organization> organizations = new ArrayList<>();
    private static final Interpreter interpreter = new Interpreter();
    private static Date date;
    private static String[] args;

    public static void main(String[] args) {
        Main.args = args;
        date = new Date();
        ParseUnit.fromCSV();
        System.out.println("Вас приветствует программа для управления коллекцией продуктов. Для получения списка команд введите help. \n" + "Введите команду:");
        interpreter.fromStream(System.in);
    }

    public static Date getDate() {
        return date;
    }

    public static LinkedHashSet<Product> getCollection() {
        return collection;
    }

    public static ArrayList<Organization> getOrganizations() {
        return organizations;
    }

    public static Interpreter getInterpreter() {
        return interpreter;
    }

    public static String[] getArgs() {
        return args;
    }
}