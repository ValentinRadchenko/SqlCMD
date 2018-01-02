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

   public MainController(View view,DataBaseManager manager){
       this.view=view;
       this.manager=manager;
       this.commands= new Command[]{new Exit(view),new Help(view),new list(manager,view),
               new Find(manager,view),new Unsupported(view)};
   }


    public void run() {
       connectToDb();
      while (true) {
          view.write("Введи команду или help для помощи ");
          String input = view.read();

          for(Command command: commands){

              if(command.canProcess(input)){
                  command.process(input);
                  break;
              }
          }
      }
    }


    private void connectToDb() {
        view.write("Привет мой господин");
        view.write("Введите имя базы пользователя и пароль в формате: Database|userName|Password");
        while (true) {
            try{
            String string = view.read();
            String[] data = string.split("[|]");
            if(data.length!=3){
                throw new IllegalArgumentException(" - Неверно количество параметров разделенных знаком | ожидается 3 а введено " +" "+data.length);
            }
            String databaseName = data[0];
            String userName = data[1];
            String password = data[2];
                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {

                printError(e);
            }

        }
        view.write("Успешно подключились");
    }

    private void printError(Exception e) {
        String message=e.getMessage();
        if(e.getCause()!=null){message+=" "+e.getCause().getMessage();}
        view.write("Неудача по причине"+ message);
        view.write("Повторите попытку");
    }
}
