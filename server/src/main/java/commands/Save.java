package commands;

public class Save extends Command {
    @Override
    public String execute() {
        return null;
    }

    @Override
    public boolean prepare(String s, boolean isInteractive) {
        return true;
    }

    @Override
    public String description() {
        return "Сохраняет коллекцию." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: save";
    }
}

