package commands;

import java.util.ArrayList;

public class Exit extends Command {
    public Exit() {
        super(0);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException{
        rightArg(args);
        System.out.println("Комманда exit выполнена, программа завершает работу.");
        System.exit(0);
    }

    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        if (!arg.matches("\\s*")) {
            System.out.println("У команды exit не может быть аргументов!");
            return false;
        } else {
            System.out.println("Комманда exit выполнена, программа завершает работу.");
            System.exit(0);
        }
        return true;
    }

    @Override
    public String description() {
        return "Прекращает работу программы (без сохранения коллекции в файл)." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: exit";
    }
}
