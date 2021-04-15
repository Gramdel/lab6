package commands;

import collection.Product;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Command implements Serializable {
    protected ArrayList<String> args;
    protected int argCount;
    protected Product receivedProduct;
    public Command(int argCount){
        this.argCount = argCount;
    }
    public void execute (ArrayList<String> args, Command caller) throws ExecuteException {

    }
    public String execute(){
        return "lalala";
    }
    public abstract String description();
    public abstract String syntax();
    protected void rightArg(ArrayList<String> args) throws ExecuteException {
        if(args.size() != argCount) throw new ExecuteException("У этой комманды должно быть " + argCount + " аргументов." + syntax());
    }
    public void setReceivedProduct(Product receivedProduct) {
        this.receivedProduct = receivedProduct;
    }
    public void prepare(String arg, boolean isInteractive) {
        System.out.println("Вызван prepare у " + this.getClass().getName());
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }
}
