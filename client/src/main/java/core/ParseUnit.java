package core;

import collection.Product;

import commands.Add;
import commands.ExecuteException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static core.Main.*;

public class ParseUnit {
    public static void fromCSV() {
        String[] args = getArgs();
        if (args.length > 0) {
            if (!Files.exists(Paths.get(args[0]))) {
                System.out.println("Коллекция не заполнена данными, так как файл коллекции не найден.");
            } else if (Files.isDirectory(Paths.get(args[0]))) {
                System.out.println("Коллекция не заполнена данными, так как в качестве файла коллекци была передана директория.");
            } else if (!Files.isRegularFile(Paths.get(args[0]))) {
                System.out.println("Коллекция не заполнена данными, так как в качестве файла коллекции передан специальный файл.");
            } else if (Files.isReadable(Paths.get(args[0]))) {
                    try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
                        String s;
                        while ((s = in.readLine()) != null) {
                            if (s.matches("[^,]*(,[^,]*){11}")) {
                                ArrayList<String> arg = new ArrayList<>(Arrays.asList(s.split("[,]", -1)));
                                Add add = new Add();
                                add.hideSuccessMsg();
                                add.setId(arg.get(0));
                                try {
                                    add.execute(new ArrayList<>(arg.subList(1,12)), add);
                                } catch (ExecuteException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                        if (getCollection().size() == 0) {
                            System.out.println("Коллекция не заполнена данными, так как файл коллекции не содержит ни одной корректной строки.");
                        } else {
                            System.out.println("Коллекция загружена из файла "+args[0]+"!");
                        }
                    } catch (IOException e) {
                        System.out.println("Коллекция не заполнена данными, так как файл коллекции не найден.");
                    }
            } else {
                System.out.println("Коллекция не заполнена данными, так как у файла коллекции нет прав на чтение.");
            }
        } else {
            System.out.println("Коллекция не заполнена данными, так как файл коллекции не указан.");
        }
    }

    public static void toCSV() {
        String[] args = getArgs();
        if (args.length > 0) {
            if (!Files.exists(Paths.get(args[0]))) {
                System.out.println("Коллекция не сохранена, так как файл коллекции не найден.");
            } else if (Files.isDirectory(Paths.get(args[0]))) {
                System.out.println("Коллекция не сохранена, так как в качестве файла коллекци была передана директория.");
            } else if (!Files.isRegularFile(Paths.get(args[0]))) {
                System.out.println("Коллекция не не сохранена, так как в качестве файла коллекции передан специальный файл.");
            } else if (Files.isWritable(Paths.get(args[0]))) {
                try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(args[0]))) {
                    ArrayList<Product> sortedCollection = new ArrayList<>(getCollection());
                    Collections.sort(sortedCollection);
                    for (Product product : sortedCollection) {
                        out.write(product.toStringForCSV().getBytes());
                    }
                    System.out.println("Коллекция сохранена в файл "+args[0]+"!");
                } catch (IOException e) {
                    System.out.println("Коллекция не сохранена, так как файл для сохранения коллекции не найден.");
                }
            } else {
                System.out.println("Коллекция не сохранена, так как у файла коллекции нет прав на запись.");
            }
        } else {
            System.out.println("Коллекция не сохранена, так как файл коллекции не указан.");
        }

    }

    public static ArrayList<String> parseJson(String s) throws ParseException {
        JSONObject o = (JSONObject) new JSONParser().parse(s);

        if (o.size() != 4 && o.size() != 11) {
            throw new ParseException(0);
        }

        ArrayList<String> args = new ArrayList<>();

        if (o.size() == 11) {
            if (o.containsKey("name") && o.containsKey("x") && o.containsKey("y") && o.containsKey("price")
                    && o.containsKey("partNumber") && o.containsKey("manufactureCost") && o.containsKey("unitOfMeasure")) {
                args.add(o.get("name").toString());
                args.add(o.get("x").toString());
                args.add(o.get("y").toString());
                args.add(o.get("price").toString());
                args.add(o.get("partNumber").toString());
                args.add(o.get("manufactureCost").toString());
                args.add(o.get("unitOfMeasure").toString());
            }
        }
        if (o.containsKey("name2") && o.containsKey("annualTurnover") && o.containsKey("employeesCount") && o.containsKey("type")) {
            args.add(o.get("name2").toString());
            args.add(o.get("annualTurnover").toString());
            args.add(o.get("employeesCount").toString());
            args.add(o.get("type").toString());
        }

        if (args.size() != o.size()) throw new ParseException(0);

        return args;
    }
}
