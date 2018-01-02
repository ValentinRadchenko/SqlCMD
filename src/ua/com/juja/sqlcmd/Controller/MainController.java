package ua.com.juja.sqlcmd.Controller;

import ua.com.juja.sqlcmd.Controller.Command.*;
import ua.com.juja.sqlcmd.Model.DataBaseManager;

import ua.com.juja.sqlcmd.Model.DataSet;
import ua.com.juja.sqlcmd.View.View;


import java.util.Arrays;
import java.util.List;

/**
 * Created by Valentin_R on 04.12.2017.
 */
public class MainController {

    private Command[] commands;
    private View view;
    private DataBaseManager manager;

    public MainController(View view, DataBaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{
                new Connect(manager, view),
                new Help(view),
                new Exit(view),
                new isConnect(manager,view),
                new list(manager, view),
                new Find(manager, view),
                new Unsupported(view)};
    }


    public void run() {
        view.write("Привет мой господин");
        view.write("Введите имя базы пользователя и пароль в формате: connect|database|userName|Password");
        while (true) {

            String input = view.read();

            for (Command command : commands) {

                if (command.canProcess(input)) {
                    command.process(input);
                    break;
                }
            }
            view.write("Введи команду или help для помощи ");
        }
    }
}


