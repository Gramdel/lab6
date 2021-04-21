package core;

import collection.Organization;
import collection.Product;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static core.Main.*;
import static core.Main.getOrganizations;

public class CSVUnit {
    private static Product product;
    public static void read(String[] args){
        if (args.length > 0) {
            System.out.println("Пытаемся загрузить коллекцию из файла \""+args[0]+"\"...");
            if (!Files.exists(Paths.get(args[0]))) {
                System.out.println("Коллекция не загружена, так как файл коллекции не найден!");
            } else if (Files.isDirectory(Paths.get(args[0]))) {
                System.out.println("Коллекция не загружена, так как в качестве файла коллекци была передана директория!");
            } else if (!Files.isRegularFile(Paths.get(args[0]))) {
                System.out.println("Коллекция не загружена, так как в качестве файла коллекции передан специальный файл!");
            } else if (Files.isReadable(Paths.get(args[0]))) {
                try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
                    String s;
                    int count = 1;
                    while ((s = in.readLine()) != null) {
                        if (s.matches("[^,]*(,[^,]*){11}")) {
                            String[] values = s.split("[,]", -1);
                            StringBuilder builder = new StringBuilder();
                            builder.append("{\"name\":\"").append(values[1]);
                            builder.append("\",\"coordinates\":{\"x\":").append(values[2]).append(",\"y\":").append(values[3]);
                            builder.append("},\"price\":").append(values[4]).append(",\"partNumber\":\"").append(values[5]);
                            builder.append("\",\"manufactureCost\":").append(values[6]).append(",\"unitOfMeasure\":\"").append(values[7]);
                            builder.append("\",\"manufacturer\":{\"name\":\"").append(values[8]).append("\",\"annualTurnover\":").append(values[9]);
                            builder.append(",\"employeesCount\":").append(values[10]).append(",\"type\":\"").append(values[11]).append("\"}}");
                            try {
                                product = new Gson().fromJson(builder.toString(), Product.class);
                                product = Creator.createProduct(product, false);
                                if (Long.parseLong(values[0]) <= 0) {
                                    System.out.println("Неправильный ввод id! Требуемый формат: положительное целое число.");
                                } else if (product != null) {
                                    product.setId(Long.parseLong(values[0]));
                                }
                                if (product == null || product.getId() == null) {
                                    System.out.println("Элемент со строки #"+count+" не добавлен из-за перечисленных выше ошибок формата данных!");
                                } else if (!getCollection().isEmpty() && getCollection().stream().anyMatch(x -> x.getId().equals(product.getId()))) {
                                    System.out.println("Элемент со строки #"+count+" не добавлен, т.к. в коллекции уже есть элемент с id "+product.getId()+"!");
                                } else {
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
                                    getCollection().add(product);
                                }
                            } catch (JsonSyntaxException | NumberFormatException e) {
                                System.out.println("Элемент со строки #"+count+" не добавлен из-за ошибки в структуре CSV!");
                            }
                        } else {
                            System.out.println("Элемент со строки #"+count+" не добавлен из-за ошибки в структуре CSV!");
                        }
                        count++;
                    }
                    if (getCollection().size() == 0) {
                        System.out.println("Коллекция не загружена, так как файл коллекции пуст или не содержит ни одной корректной строки!");
                    } else {
                        System.out.println("Коллекция успешно загружена!");
                    }
                } catch (IOException e) {
                    System.out.println("Коллекция не загружена, так как файл коллекции не найден.");
                }
            } else {
                System.out.println("Коллекция не заполнена данными, так как у файла коллекции нет прав на чтение.");
            }
        } else {
            System.out.println("Коллекция не заполнена данными, так как файл коллекции не указан.");
        }
    }
}
