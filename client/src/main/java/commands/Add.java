package commands;

import collection.*;

import static core.Main.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Add extends Command {
    private boolean successMsg = true;
    private boolean failureMsg = true;

    private final Stack<String> errors = new Stack<>();

    private String id = null;
    private String name;
    private Double x; //coordinates
    private Long y; //coordinates
    private java.time.ZonedDateTime creationDate = null;
    private float price;
    private String partNumber;
    private Float manufactureCost;
    private UnitOfMeasure unitOfMeasure;
    private String name2; //manufacturer
    private Long annualTurnover; //manufacturer
    private Long employeesCount; //manufacturer
    private OrganizationType type; //manufacturer

    public Add(){
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        boolean b = caller != null;
        if (b) argCount = 11;
        rightArg(args);
        if (!b) args.addAll(Arrays.asList(new String[11]));

        if (checkId() & checkName(args.get(0), b) & checkCoordinateX(args.get(1), b) & checkCoordinateY(args.get(2), b) &
                checkPrice(args.get(3), b) & checkPartNumber(args.get(4), b) & checkManufactureCost(args.get(5), b) &
                checkUnitOfMeasure(args.get(6), b) & checkName2(args.get(7), b) & checkAnnualTurnover(args.get(8), b) &
                checkEmployeesCount(args.get(9), b) & checkType(args.get(10), b)) {

            Product product = new Product(name, new Coordinates(x, y), price, partNumber, manufactureCost,
                    unitOfMeasure, addOrganization());
            if (id != null) product.setId(Long.parseLong(id));
            if (creationDate != null) product.setCreationDate(creationDate);

            getCollection().add(product);
            if (successMsg) System.out.println("Элемент успешно добавлен в коллекцию!");
        } else {
            StringBuilder s = new StringBuilder();
            if (failureMsg) s.append("Элемент не добавлен из-за следующих ошибок ввода:");
            for (String error : errors)
                s.append("\n\t").append(error);
            errors.clear();
            throw new ExecuteException(s.toString());
        }
    }

    @Override
    public String description() {
        return "Добавляет новый элемент в коллекцию."+syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: add \n\t\t(В скриптах - add {element}, где {element} - JSON-строка)";
    }

    private Organization addOrganization(){
        Organization o1 = new Organization(name2, annualTurnover, employeesCount, type);
        for (Organization o2 : getOrganizations()){
            if (o1.equals(o2)) {
                return o2;
            }
        }
        getOrganizations().add(o1);
        return o1;
    }

    private boolean checkName(String name, boolean b){
        if (!b) {
            System.out.println("Введите название продукта:");
            Scanner in = new Scanner(System.in);
            name = in.nextLine();
            if(!checkName(name,true)) {
                System.out.println(errors.peek());
                checkName(name,false);
            }
        } else if (name.matches("\\s*")) {
            errors.push("Неправильный ввод названия продукта! Оно не может быть пустой строкой.");
            return false;
        }
        this.name = name;
        return true;
    }

    private boolean checkCoordinateX(String x, boolean b){
        if (!b) {
            System.out.println("Введите координату x (дробное число):");
            Scanner in = new Scanner(System.in);
            x = in.nextLine();
            if(!checkCoordinateX(x,true)) {
                System.out.println(errors.peek());
                checkCoordinateX(x,false);
            }
        } else {
            try {
                this.x = Double.parseDouble(x);
            } catch(NumberFormatException e) {
                errors.push("Неправильный ввод координаты x! Требуемый формат: дробное число.");
                return false;
            }
        }
        return true;
    }

    private boolean checkCoordinateY(String y, boolean b){
        if (!b) {
            System.out.println("Введите координату y (целое число):");
            Scanner in = new Scanner(System.in);
            y = in.nextLine();
            if(!checkCoordinateY(y,true)) {
                System.out.println(errors.peek());
                checkCoordinateY(y,false);
            }
        } else {
            try {
                this.y = Long.parseLong(y);
            } catch (NumberFormatException e) {
                errors.push("Неправильный ввод координаты y! Требуемый формат: целое число.");
                return false;
            }
        }
        return true;
    }

    private boolean checkPrice(String price, boolean b){
        if (!b) {
            System.out.println("Введите цену продукта (целое положительное число):");
            Scanner in = new Scanner(System.in);
            price = in.nextLine();
            if(!checkPrice(price,true)) {
                System.out.println(errors.peek());
                checkPrice(price,false);
            }
        } else {
            try {
                this.price = Float.parseFloat(price);
                if (this.price <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                errors.push("Неправильный ввод цены продукта! Требуемый формат: положительное дробное число.");
                return false;
            }
        }
        return true;
    }

    private boolean checkPartNumber(String partNumber, boolean b){
        if (!b) {
            System.out.println("Введите код производителя (#xxxxxx, где x - цифры):");
            Scanner in = new Scanner(System.in);
            partNumber = in.nextLine();
            if(!checkPartNumber(partNumber,true)) {
                System.out.println(errors.peek());
                checkPartNumber(partNumber,false);
            }
        } else if (partNumber.matches("#\\d{6}")){
            for (Product product : getCollection()){
                if(product.getPartNumber().equals(partNumber)) {
                    errors.push("Неправильный ввод кода производителя! Элемент с таким номером уже есть в коллекции.");
                    return false;
                }
            }
        } else {
            errors.push("Неправильный ввод кода производителя! Требуемый формат: #xxxxxx, где x - цифры.");
            return false;
        }
        this.partNumber = partNumber;
        return true;
    }

    private boolean checkManufactureCost(String manufactureCost, boolean b){
        if (!b) {
            System.out.println("Введите цену производства продукта (целое положительное число):");
            Scanner in = new Scanner(System.in);
            manufactureCost = in.nextLine();
            if(!checkManufactureCost(manufactureCost,true)) {
                System.out.println(errors.peek());
                checkManufactureCost(manufactureCost,false);
            }
        } else {
            try {
                this.manufactureCost = Float.parseFloat(manufactureCost);
                if (this.manufactureCost <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                errors.push("Неправильный ввод цены производства продукта! Требуемый формат: положительное дробное число.");
                return false;
            }
        }
        return true;
    }

    private boolean checkUnitOfMeasure(String unitOfMeasure, boolean b){
        if (!b) {
            System.out.println("Введите единицу измерения ("+UnitOfMeasure.valueList()+"):");
            Scanner in = new Scanner(System.in);
            unitOfMeasure = in.nextLine();
            if(!checkUnitOfMeasure(unitOfMeasure,true)) {
                System.out.println(errors.peek());
                checkUnitOfMeasure(unitOfMeasure,false);
            }
        } else {
            try {
                this.unitOfMeasure = UnitOfMeasure.fromString(unitOfMeasure);
            } catch (IllegalArgumentException e) {
                String s = "Неправильный ввод единиц измерения! Возможные варианты ввода: " + UnitOfMeasure.valueList() + ".";
                errors.push(s);
                return false;
            }
        }
        return true;
    }

    private boolean checkName2(String name2, boolean b){
        if (!b) {
            System.out.println("Введите название компании-производителя:");
            Scanner in = new Scanner(System.in);
            name2 = in.nextLine();
            if(!checkName2(name2,true)) {
                System.out.println(errors.peek());
                checkName2(name2,false);
            }
        } else if (name2.matches("\\s*")){
            errors.push("Неправильный ввод названия компании-производителя! Оно не может быть пустой строкой.");
            return false;
        }
        this.name2 = name2;
        return true;
    }

    private boolean checkAnnualTurnover(String annualTurnover, boolean b){
        if (!b) {
            System.out.println("Введите годовой оборот компании-производителя (пустая строка или целое положительное число):");
            Scanner in = new Scanner(System.in);
            annualTurnover = in.nextLine();
            if(!checkAnnualTurnover(annualTurnover,true)) {
                System.out.println(errors.peek());
                checkAnnualTurnover(annualTurnover,false);
            }
        } else if (!annualTurnover.equals("")){
            try {
                this.annualTurnover = Long.parseLong(annualTurnover);
                if (this.annualTurnover <= 0) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                errors.push("Неправильный ввод ежегодного оборота компании-производителя! Требуемый формат: пустая строка или целое положительное число.");
                return false;
            }
        } else {
            this.annualTurnover = null;
        }
        return true;
    }

    private boolean checkEmployeesCount(String employeesCount, boolean b){
        if (!b) {
            System.out.println("Введите количество сотрудников компании-производителя (пустая строка или целое положительное число):");
            Scanner in = new Scanner(System.in);
            employeesCount = in.nextLine();
            if(!checkEmployeesCount(employeesCount,true)) {
                System.out.println(errors.peek());
                checkEmployeesCount(employeesCount,false);
            }
        } else if (!employeesCount.equals("")) {
            try {
                this.employeesCount = Long.parseLong(employeesCount);
                if (this.employeesCount <= 0) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                errors.push("Неправильный ввод количества сотрудников компании-производителя! Требуемый формат: пустая строка или целое положительное число.");
                return false;
            }
        } else {
            this.employeesCount = null;
        }
        return true;
    }

    private boolean checkType(String type, boolean b){
        if (!b) {
            System.out.println("Введите тип компании-производителя (пустая строка, "+OrganizationType.valueList()+"):");
            Scanner in = new Scanner(System.in);
            type = in.nextLine();
            if(!checkType(type,true)) {
                System.out.println(errors.peek());
                checkType(type,false);
            }
        } else if (!type.equals("")) {
            try {
                this.type = OrganizationType.fromString(type);
            } catch (IllegalArgumentException e) {
                String s = "Неправильный ввод типа компании-производителя! Возможные варианты ввода: пустая строка, "+OrganizationType.valueList()+".";
                errors.push(s);
                return false;
            }
        } else {
            this.type = null;
        }
        return true;
    }

    private boolean checkId() {
        if (id != null) {
            for (Product product : getCollection()) {
                if (product.getId().toString().equals(id)) {
                    errors.push("Неправильный ввод id! Оно должно быть уникальным!");
                    return false;
                }
            }
            try {
                if (Long.parseLong(id) <= 0) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                errors.push("Неправильный ввод id! Требуемый формат: целое положительное число.");
                return false;
            }
        }
        return true;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void hideSuccessMsg(){
        successMsg = false;
    }

    public void hideFailureMsg(){
        failureMsg = false;
    }
}