package commands;

import java.util.ArrayList;
import static core.Main.getCollection;
import static core.Main.getOrganizations;

public class Update extends Command {
    public Update() {
        super(1);
    }

    @Override
    public void execute(ArrayList<String> args, Command caller) throws ExecuteException {
        if (caller != null) argCount = 12;
        rightArg(args);
        try {
            RemoveById removeById = new RemoveById();
            removeById.hideSuccessMsg();
            removeById.execute(new ArrayList<>(args.subList(0,1)), this);
        } catch (ExecuteException e) {
            throw new ExecuteException("Элемент не обновлён! "+e.getMessage());
        }
        try {
            Add add = new Add();
            add.hideSuccessMsg();
            add.hideFailureMsg();
            add.setId(receivedProduct.getId().toString());
            add.setCreationDate(receivedProduct.getCreationDate());
            args.remove(0);
            if (args.size()>0) {
                add.execute(args, this);
            } else {
                add.execute(args, null);
            }
            System.out.println("Элемент c id " + receivedProduct.getId() + " успешно обновлён!");
        } catch (ExecuteException e) {
            getCollection().add(receivedProduct);
            if (!getOrganizations().contains(receivedProduct.getManufacturer())) getOrganizations().add(receivedProduct.getManufacturer());
            throw new ExecuteException("Элемент c id " + receivedProduct.getId() + " не обновлён из-за следующих ошибок:"+e.getMessage());
        }
    }

    @Override
    public String description() {
        return "Обновляет значение элемента коллекции, id которого равен заданному." + syntax();
    }

    @Override
    public String syntax() {
        return " Синтаксис: update id, где id - целое положительное число. \n\t\t(В скриптах - update id {element}, где {element} - JSON-строка)";
    }

    @Override
    protected void rightArg(ArrayList<String> args) throws ExecuteException {
        super.rightArg(args);
        try {
            Long.parseLong(args.get(0));
        } catch (NumberFormatException e) {
            throw new ExecuteException("Неправильный ввод id! Требуемый формат: целое положительное число.");
        }
    }
}
