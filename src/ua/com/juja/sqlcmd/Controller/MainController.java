package ua.com.juja.sqlcmd.Controller;

import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.Model.InMemoryDatabaseManager;
import ua.com.juja.sqlcmd.Model.JDBCDataBaseManager;
import ua.com.juja.sqlcmd.View.Console;
import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 04.12.2017.
 */
public class MainController {

    public static void main(String[] args) {
        View view=new Console();
        DataBaseManager manager=new JDBCDataBaseManager();

        view.write("Привет мой господин");
        view.write("Введите имя базы пользователя и пароль в формате: database|userName|password");
        while (true) {
            String string = view.read();
            String[] data = string.split("[|]");
            String databaseName = data[0];
            String userName = data[1];
            
            String password = data[2];
            try {
                manager.connect(databaseName, userName, password);
                 break;
            } catch (Exception e) {

                String message=e.getMessage();
                if(e.getCause()!=null){message+=" "+e.getCause().getMessage();}
                view.write("Неудача по причине"+message);
                view.write("Повторите попытку");
            }

        }
        view.write("Успешно подключились");
    }
}
