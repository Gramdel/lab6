package core;

import collection.Product;
import collection.Organization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static final LinkedHashSet<Product> collection = new LinkedHashSet<>();
    private static final ArrayList<Organization> organizations = new ArrayList<>();
    private static final Interpreter interpreter = new Interpreter();
    private static Date date;
    private static String[] args;
    private static Logger logger = Logger.getLogger(Main.class.getName());
    private static final Stack<String> history = new Stack<>();

    public static void main(String[] args) {
        if (args.length<2) {
            System.out.println("Программа не запущена, так как не переданы IP и порт сервера!\n(Они должны быть переданы через аргументы командной строки. Формат IP: xxx.xxx.xxx.xxx; формат порта: число от 0 до 65535.)");
        } else {
            Main.args = args;
            try {
                String loggerCfg = "handlers = java.util.logging.FileHandler\n" +
                        "java.util.logging.FileHandler.level     = ALL\n" +
                        "java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter\n" +
                        "java.util.logging.FileHandler.append    = true\n" +
                        "java.util.logging.FileHandler.pattern   = log.txt";
                LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(loggerCfg.getBytes()));
                logger = Logger.getLogger(Main.class.getName());
                System.out.println("Логгер успешно инициализирован!");
            } catch (IOException e) {
                System.out.println("Не удалось инициализировать логгер!");
            }
            System.out.println("Вас приветствует программа-клиент для управления коллекцией продуктов. Для получения списка команд введите help. \n" + "Введите команду:");
            interpreter.fromStream(System.in);
        }
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

    public static Logger getLogger() {
        return logger;
    }

    public static String[] getArgs() {
        return args;
    }

    public static Stack<String> getHistory() {
        return history;
    }

    public static void addToHistory(String com) {
        history.add(com);
        if (history.size() > 7) history.removeElementAt(0);
    }
}