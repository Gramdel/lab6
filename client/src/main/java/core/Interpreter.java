package core;

import commands.*;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.ParseUnit.parseJson;

public class Interpreter {
    private final HashMap<Command, String> commands = new HashMap<>();
    private final LinkedList<String> history = new LinkedList<>();
    private Command caller = null;

    {
        commands.put(new Add(), "add");
        commands.put(new Clear(), "clear");
        commands.put(new Exit(), "exit");
        commands.put(new History(), "history");
        commands.put(new Help(), "help");
        commands.put(new ExecuteScript(), "execute_script");
        commands.put(new Show(), "show");
        commands.put(new RemoveById(), "remove_by_id");
        commands.put(new Update(), "update");
        commands.put(new Info(), "info");
        commands.put(new RemoveByUOM(), "remove_any_by_unit_of_measure");
        commands.put(new FilterByManufacturer(), "filter_by_manufacturer");
        commands.put(new Save(), "save");
        commands.put(new AddIfMax(), "add_if_max");
        commands.put(new PrintPrice(), "print_field_descending_price");
        commands.put(new RemoveGreater(), "remove_greater");
    }

    public void fromStream(InputStream stream) {
        Scanner in = new Scanner(stream);
        while (in.hasNext()) {
            String s = in.nextLine();

            if (!s.matches("\\s*")) {
                String jsonString = "";
                if (!stream.equals(System.in) && s.matches("\\s*\\w+\\s+(\\d+\\s+)?\\{.*}")) {
                    Matcher m = Pattern.compile("\\{.*}").matcher(s);
                    if (m.find()) jsonString = m.group();
                    s = s.replaceAll("\\{.*}", "");
                }

                ArrayList<String> args = new ArrayList<>();
                Matcher m = Pattern.compile("[^\\s]+").matcher(s);
                while (m.find()) args.add(m.group());
                String com = args.remove(0);

                boolean script = com.equals("execute_script");

                if (commands.containsValue(com)) {
                    try {
                        for (Map.Entry<Command, String> entry : commands.entrySet()) {
                            if (entry.getValue().equals(com)) {
                                if (script) addToHistory(com);
                                if (!jsonString.isEmpty()) args.addAll(parseJson(jsonString));

                                try {
                                    entry.getKey().execute(args, caller);
                                } catch (ExecuteException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }
                        }
                    } catch (ParseException e) {
                        System.out.println("В структуре JSON-строки команды " + com + " содержится ошибка!");
                    }
                } else {
                    System.out.println("Такой команды не существует! Список команд: help");
                }

                if (!script) addToHistory(com);
            }
        }
        caller = null;
    }

    public HashMap<Command, String> getCommands() {
        return commands;
    }

    public LinkedList<String> getHistory() {
        return history;
    }

    private void addToHistory(String com) {
        history.add(com);
        if (history.size() > 7) history.remove();
    }

    public void setCaller(Command caller) {
        this.caller = caller;
    }
}
