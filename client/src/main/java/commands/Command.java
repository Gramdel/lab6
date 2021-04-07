package commands;

import collection.Product;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Command implements Serializable {
    protected int argCount;
    protected Product receivedProduct;
    public Command(int argCount){
        this.argCount = argCount;
    }
    public abstract void execute (ArrayList<String> args, Command caller) throws ExecuteException;
    public abstract String description();
    public abstract String syntax();
    protected void rightArg(ArrayList<String> args) throws ExecuteException {
        if(args.size() != argCount) throw new ExecuteException("У этой комманды должно быть " + argCount + " аргументов." + syntax());
    }

    public void setReceivedProduct(Product receivedProduct) {
        this.receivedProduct = receivedProduct;
    }
}
