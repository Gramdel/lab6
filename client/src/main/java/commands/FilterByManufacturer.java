package commands;

import collection.Organization;
import collection.OrganizationType;
import collection.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import static core.Main.getCollection;
import static core.Main.getOrganizations;

public class FilterByManufacturer extends Command {
    private final Stack<String> errors = new Stack<>();

    private String name;
    private Long annualTurnover;
    private Long employeesCount;
    private OrganizationType type;

    public FilterByManufacturer() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        boolean b = caller != null;
        if (b) argCount = 4;
        rightArg(args);
        if (!b) args.addAll(Arrays.asList(new String[4]));

        if (checkName(args.get(0), b) & checkAnnualTurnover(args.get(1), b) &
                checkEmployeesCount(args.get(2), b) & checkType(args.get(3), b)) {

            Organization manufacturer = new Organization(name, annualTurnover, employeesCount, type);

            if (getOrganizations().contains(manufacturer)) {
                System.out.println("Элементы коллекции с такой компанией-производителем:");
                ArrayList<Product> sortedCollection = new ArrayList<>(getCollection());
                sortedCollection.sort(Product.byIdComparator);
                for (Product product : sortedCollection) {
                    if (product.getManufacturer().equals(manufacturer)) System.out.println(product);
                }
            } else {
                throw new ExecuteException("В коллекции нет ни одного элемента с такой компанией-производителем!");
            }
        } else {
            StringBuilder s = new StringBuilder();
            s.append("Невозможно вывести элементы с такой компанией-производителем из-за ошибок ввода:");
            for (String error : errors)
                s.append("\n\t").append(error);
            errors.clear();
            throw new ExecuteException(s.toString());
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

    private boolean checkName(String name, boolean b){
        if (!b) {
            System.out.println("Введите название компании-производителя:");
            Scanner in = new Scanner(System.in);
            name = in.nextLine();
            if(!checkName(name,true)) {
                System.out.println(errors.peek());
                checkName(name,false);
            }
        } else if (name.matches("\\s*")){
            errors.push("Неправильный ввод названия компании-производителя! Оно не может быть пустой строкой.");
            return false;
        }
        this.name = name;
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
}