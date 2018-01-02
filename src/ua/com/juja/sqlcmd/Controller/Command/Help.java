package ua.com.juja.sqlcmd.Controller.Command;

import ua.com.juja.sqlcmd.Controller.MainController;
import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 29.12.2017.
 */
public class Help implements Command {

   View view;
    public Help(View view) {
        this.view=view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        view.write("Существующие комманды");

        view.write("\tconnect|databaseName|userName|password");
        view.write("\t\t: Для подключения к базе данных, с которой будем работать");

        view.write("\tfind|tableNames");
        view.write("\t\t: Для получения содержимого таблицы tableNames");

        view.write("\tlist");
        view.write("\t\t: Для вывода списка всех таблиц");

        view.write("\tHelp");
        view.write("\t\t: Для вывода сриска всех комманд");

        view.write("\texit");
        view.write("\t\tДля выхода из программы");
    }


}
