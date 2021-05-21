package core;

import commands.*;

import java.io.InputStream;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.Main.addToHistory;

public class Interpreter implements Serializable {
    private final HashMap<String, Command> commands = new HashMap<>();

    {
        commands.put("add", new Add());
        commands.put("clear", new Clear());
        commands.put("exit", new Exit());
        commands.put("history", new History());
        commands.put("help", new Help());
        commands.put("execute_script", new ExecuteScript());
        commands.put("show", new Show());
        commands.put("remove_by_id", new RemoveById());
        commands.put("update", new Update());
        commands.put("info", new Info());
        commands.put("remove_any_by_unit_of_measure", new RemoveByUOM());
        commands.put("filter_by_manufacturer", new FilterByManufacturer());
        commands.put("save", new Save());
        commands.put("add_if_max", new AddIfMax());
        commands.put("print_field_descending_price", new PrintPrice());
        commands.put("remove_greater", new RemoveGreater());
    }

    public void fromStream(InputStream stream) {
        Scanner in = new Scanner(stream);
        while (in.hasNext()) {
            String s = in.nextLine();
            if (!s.matches("\\s*")) {
                String com = "";
                String arg = "";
                Matcher m = Pattern.compile("[^\\s]+").matcher(s);
                if (m.find()) {
                    com = m.group();
                    s = m.replaceFirst("");
                }
                m = Pattern.compile("[\\s]+").matcher(s);
                if (m.find()) {
                    arg = m.replaceFirst("");
                }
                addToHistory(com);
                Command command = commands.get(com);
                if (command != null) {
                    if (command.prepare(arg, stream.equals(System.in))) {
                        System.out.println(command.execute());
                    }
                } else {
                    System.out.println("Такой команды не существует! Список команд: help");
                }
            }
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}