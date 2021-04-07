package core;

import collection.*;

import java.util.Scanner;
import java.util.Stack;

import static core.Main.getCollection;

public class Creator {
    public static Product createProduct(Product product, boolean isInteractive) throws NullPointerException {
        Stack<String> errors = new Stack<>();
        String name;
        while (true) {
            if (isInteractive) {
                System.out.println("Введите название продукта:");
                Scanner in = new Scanner(System.in);
                name = in.nextLine();
            } else {
                name = product.getName();
            }
            if (name.matches("\\s*")) {
                errors.push("Неправильный ввод названия продукта! Оно не может быть пустой строкой.");
                if (isInteractive) {
                    System.out.println(errors.pop());
                    continue;
                }
            }
            break;
        }
        Double x = null;
        while (true) {
            try {
                if (isInteractive) {
                    System.out.println("Введите координату x (дробное число):");
                    Scanner in = new Scanner(System.in);
                    x = Double.parseDouble(in.nextLine());
                } else {
                    x = product.getCoordinates().getX();
                    if (x == null) {
                        throw new NumberFormatException();
                    }
                }
            } catch (NumberFormatException e) {
                errors.push("Неправильный ввод координаты x! Требуемый формат: дробное число.");
                if (isInteractive) {
                    System.out.println(errors.pop());
                    continue;
                }
            }
            break;
        }
        Long y = null;
        while (true) {
            try {
                if (isInteractive) {
                    System.out.println("Введите координату y (целое число):");
                    Scanner in = new Scanner(System.in);
                    y = Long.parseLong(in.nextLine());
                } else {
                    y = product.getCoordinates().getY();
                    if (y == null) {
                        throw new NumberFormatException();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Неправильный ввод координаты y! Требуемый формат: целое число.");
                if (isInteractive) {
                    System.out.println(errors.pop());
                    continue;
                }
            }
            break;
        }
        float price = -1;
        while (true) {
            try {
                if (isInteractive) {
                    System.out.println("Введите цену продукта (положительное дробное число):");
                    Scanner in = new Scanner(System.in);
                    price = Float.parseFloat(in.nextLine());
                } else {
                    price = product.getPrice();
                }
                if (price <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                errors.push("Неправильный ввод цены продукта! Требуемый формат: положительное дробное число.");
                if (isInteractive) {
                    System.out.println(errors.pop());
                    continue;
                }
            }
            break;
        }
        String partNumber;
        while (true) {
            if (isInteractive) {
                System.out.println("Введите код производителя (#xxxxxx, где x - цифры):");
                Scanner in = new Scanner(System.in);
                partNumber = in.nextLine();
            } else {
                partNumber = product.getPartNumber();
            }
            if (partNumber.matches("#\\d{6}")) {
                String finalPartNumber = partNumber;
                if(getCollection().stream().anyMatch(p -> p.getPartNumber().equals(finalPartNumber))) {
                    errors.push("Неправильный ввод кода производителя! Элемент с таким номером уже есть в коллекции.");
                    if (isInteractive) {
                        System.out.println(errors.pop());
                        continue;
                    }
                }
            } else {
                errors.push("Неправильный ввод кода производителя! Требуемый формат: #xxxxxx, где x - цифры.");
                if (isInteractive) {
                    System.out.println(errors.pop());
                    continue;
                }
            }
            break;
        }
        float manufactureCost = -1;
        while (true) {
            try {
                if (isInteractive) {
                    System.out.println("Введите цену производства продукта (положительное дробное число):");
                    Scanner in = new Scanner(System.in);
                    manufactureCost = Float.parseFloat(in.nextLine());
                } else {
                    manufactureCost = product.getManufactureCost();
                }
                if (manufactureCost <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                errors.push("Неправильный ввод цены производства продукта! Требуемый формат: положительное дробное число.");
                if (isInteractive) {
                    System.out.println(errors.pop());
                    continue;
                }
            }
            break;
        }
        UnitOfMeasure unitOfMeasure;
        while (true) {
            try {
                if (isInteractive) {
                    System.out.println("Введите единицу измерения (" + UnitOfMeasure.valueList() + "):");
                    Scanner in = new Scanner(System.in);
                    unitOfMeasure = UnitOfMeasure.fromString(in.nextLine());
                } else {
                    unitOfMeasure = product.getUnitOfMeasure();
                    if (unitOfMeasure == null) {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильный ввод единиц измерения! Возможные варианты ввода: " + UnitOfMeasure.valueList() + ".");
                continue;
            }
            break;
        }
        String manufacturerName;
        while (true) {
            if (isInteractive) {
                System.out.println("Введите название компании-производителя:");
                Scanner in = new Scanner(System.in);
                manufacturerName = in.nextLine();
            } else {
                manufacturerName = product.getManufacturer().getName();
            }
            if (manufacturerName.matches("\\s*")) {
                errors.push("Неправильный ввод названия компании-производителя! Оно не может быть пустой строкой.");
                if (isInteractive) {
                    System.out.println(errors.pop());
                    continue;
                }
            }
            break;
        }
        Long annualTurnover = null;
        while (true) {
            try {
                if (isInteractive) {
                    System.out.println("Введите годовой оборот компании-производителя (пустая строка или целое положительное число):");
                    Scanner in = new Scanner(System.in);
                    String s = in.nextLine();
                    if (!s.matches("\\s*")) {
                        annualTurnover = Long.parseLong(s);
                    }
                } else {
                    annualTurnover = product.getManufacturer().getAnnualTurnover();
                }
                if (annualTurnover != null && annualTurnover <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                errors.push("Неправильный ввод ежегодного оборота компании-производителя! Требуемый формат: пустая строка или целое положительное число.");
                if (isInteractive) {
                    System.out.println(errors.pop());
                    continue;
                }
            }
            break;
        }
        Long employeesCount = null;
        while (true) {
            try {
                if (isInteractive) {
                    System.out.println("Введите количество сотрудников компании-производителя (пустая строка или целое положительное число):");
                    Scanner in = new Scanner(System.in);
                    String s = in.nextLine();
                    if (!s.matches("\\s*")) {
                        employeesCount = Long.parseLong(s);
                    }
                } else {
                    employeesCount = product.getManufacturer().getEmployeesCount();
                }
                if (employeesCount != null && employeesCount <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                errors.push("Неправильный ввод количества сотрудников компании-производителя! Требуемый формат: пустая строка или целое положительное число.");
                if (isInteractive) {
                    System.out.println(errors.pop());
                    continue;
                }
            }
            break;
        }
        OrganizationType type = null;
        while (true) {
            try {
                if (isInteractive) {
                    System.out.println("Введите тип компании-производителя (пустая строка, "+OrganizationType.valueList()+"):");
                    Scanner in = new Scanner(System.in);
                    String s = in.nextLine();
                    if (!s.equals("")) {
                        type = OrganizationType.fromString(s);
                    }
                } else {
                    type = product.getManufacturer().getType();
                }
            } catch (IllegalArgumentException e) {
                String s = "Неправильный ввод типа компании-производителя! Возможные варианты ввода: пустая строка, "+OrganizationType.valueList()+".";
                continue;
            }
            break;
        }
        return new Product(name, new Coordinates(x,y), price, partNumber, manufactureCost, unitOfMeasure,new Organization(manufacturerName,annualTurnover,employeesCount,type));
        //return null;
    }
}
