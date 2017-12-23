package ua.com.juja.sqlcmd.Controller;

import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.Model.JDBCDataBaseManager;
import ua.com.juja.sqlcmd.View.Console;
import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 23.12.2017.
 */
public class Main {
    public static void main(String[] args) {

        View view = new Console();
        DataBaseManager manager=new JDBCDataBaseManager();
        MainController controller =new MainController(view,manager);
        controller.run();

    }
}
