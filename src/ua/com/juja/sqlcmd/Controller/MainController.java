package ua.com.juja.sqlcmd.Controller;

import ua.com.juja.sqlcmd.Controller.Command.*;
import ua.com.juja.sqlcmd.Model.DataBaseManager;

import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 04.12.2017.
 */
public class MainController {

    private Command[] commands;
    private View view;
    private DataBaseManager  manager;

    public MainController(View view, DataBaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{
                new Connect(manager, view),
                new Help(view),
                new Exit(view),
                new isConnect(manager,view),
                new list(manager, view),
                new Clear(manager,view),
                new Create(manager,view),
                new Find(manager, view),
                new Unsupported(view)};
    }


    public void run() {
        try{
            doWork();
         }catch (ExitException e){

        }
    }

    private void doWork() {
        view.write("Привет пользователь");
        view.write("Введите имя базы пользователя и пароль в формате: connect|database|userName|Password");

        while (true) {

            String input = view.read();
            if (input == null) {
                new Exit(view).process(input);
                break;
            }
            for (Command command : commands) {
                try{
                if (command.canProcess(input)) {

                    command.process(input);
                    break;
                    }
                    } catch (Exception e) {
                      if(e instanceof ExitException){
                        throw new ExitException();
                      }
                      printError(e);
                      break;
                    }

                }
                view.write("Введи команду или help для помощи ");
            }
        }



    private void printError(Exception e) {
        String message=e.getMessage();
        if(e.getCause()!=null){message+=" "+e.getCause().getMessage();}
        view.write("Неудача по причине"+ message);
        view.write("Повторите попытку");
    }

}


