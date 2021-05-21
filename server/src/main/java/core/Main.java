package core;

import collection.Organization;
import collection.Product;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static final LinkedHashSet<Product> collection = new LinkedHashSet<>();
    private static final ArrayList<Organization> organizations = new ArrayList<>();
    private static final Interpreter interpreter = new Interpreter();
    private static Date date;
    private static String[] args;
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
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

        date = new Date();
        Main.args = args;
        CSVUnit.read();
        Server server = new Server();
        server.setDaemon(true);
        System.out.println("Вас приветствует программа для управления коллекцией продуктов. Для получения списка команд введите help. \n" + "Введите команду:");
        server.start();
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

    public static Logger getLogger() {
        return logger;
    }
}