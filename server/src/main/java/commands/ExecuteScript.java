package commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.Main.getInterpreter;

public class ExecuteScript extends Command {
    private final Stack<String> scripts = new Stack<>();
    private String arg;

    @Override
    public boolean prepare(String arg, boolean isInteractive) {
        if (!Files.exists(Paths.get(arg))) {
            System.out.println("Скрипта с именем " + arg + " не существует!");
        } else if (Files.isDirectory(Paths.get(arg))) {
            System.out.println("Скрипт с именем " + arg + " не выполнен, так как в качестве исполняемого файла была передана директория.");
        } else if (!Files.isRegularFile(Paths.get(arg))) {
            System.out.println("Скрипт с именем " + arg + " не выполнен, так как в качестве исполняемого файла был передан специальный файл.");
        } else if (!Files.isReadable(Paths.get(arg))) {
            System.out.println("Скрипт с именем " + arg + " не выполнен, так как у исполняемого файла нет прав на чтение.");
        } else if (scripts.contains(arg)) {
            System.out.println("Скрипт с именем " + arg + " не выполнен, так как он вызывает сам себя!");
        } else {
            try {
                System.out.println("Скрипт из файла " + arg + " начинает выполняться...");
                scripts.push(arg);
                Scanner in = new Scanner(new BufferedInputStream(new FileInputStream(arg)));
                while (in.hasNext()) {
                    String s = in.nextLine();
                    if (!s.matches("\\s*")) {
                        String com = "";
                        String arg2 = "";
                        Matcher m = Pattern.compile("[^\\s]+").matcher(s);
                        if (m.find()) {
                            com = m.group();
                            arg2 = m.replaceFirst("");
                        }
                        //addToHistory(com);
                        Command command = getInterpreter().getCommands().get(com);
                        if (command != null) {
                            if (command.prepare(arg2, false)) {
                                System.out.println(command.execute());
                            }
                        } else {
                            System.out.println("Такой команды не существует! Список команд: help");
                        }
                    }
                }
                //getInterpreter().fromStream();
                this.arg = arg;
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("Скрипта с именем " + arg + " не существует!");
            }
        }
        return false;
    }

    @Override
    public synchronized String execute() {
        scripts.remove(arg);
        return "Скрипт из файла " + arg + " выполнен!";
    }

    @Override
    public String description() {
        return "Выполняет скрипт." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: execute_script file_name, где file_name - полное (с расширением) имя файла.";
    }
}
