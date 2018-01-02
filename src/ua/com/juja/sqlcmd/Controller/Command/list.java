package ua.com.juja.sqlcmd.Controller.Command;

import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.View.View;

import java.util.Arrays;

/**
 * Created by Valentin_R on 02.01.2018.
 */
public class list implements Command {

   DataBaseManager manager;
   View view;

    public list(DataBaseManager manager, View view){
        this.manager = manager;
        this.view=view;
    }



    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) {

        String [] tableNames=manager.getTableNames();
        String message=Arrays.toString(tableNames);
        view.write(message);
    }
}
