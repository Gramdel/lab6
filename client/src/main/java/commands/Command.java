package commands;

import java.io.Serializable;

public abstract class Command implements Serializable {
    public abstract boolean prepare(String arg, boolean isInteractive);
    public String execute() {
        return "Команда успешно выполнена!";
    }
    public abstract String description();
    public abstract String syntax();
}
