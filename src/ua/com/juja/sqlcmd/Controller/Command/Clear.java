package ua.com.juja.sqlcmd.Controller.Command;

import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 03.01.2018.
 */
public class Clear implements Command{


    private DataBaseManager manager;
    private View view;

    public Clear(DataBaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        String[]data=command.split("\\|");
        if(data.length!=2){
            throw new IllegalArgumentException(String.format("  Формат команды clear|tableName , а ты ввел %s",command));
        }
        manager.clear(data[1]);
        view.write(String.format("Таблица %s была успешно очищена",data[1]));
    }
}
