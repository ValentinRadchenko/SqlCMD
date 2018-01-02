package ua.com.juja.sqlcmd.Controller.Command;

import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 02.01.2018.
 */
public class isConnect implements Command {
    private DataBaseManager manager;
    private View view;

    public isConnect(DataBaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process(String command) {
          view.write(String.format("Вы не можете пользоваться командой $s пока не подключитесь с помощью комманды connect|database|userName|Password",command));
    }
}
