package core;

import collection.Product;

import java.io.*;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static core.Main.*;

public class ParseUnit {
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
}
