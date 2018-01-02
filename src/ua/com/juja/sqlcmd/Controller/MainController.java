package ua.com.juja.sqlcmd.Controller;

import ua.com.juja.sqlcmd.Controller.Command.Command;
import ua.com.juja.sqlcmd.Controller.Command.Exit;
import ua.com.juja.sqlcmd.Controller.Command.Help;
import ua.com.juja.sqlcmd.Model.DataBaseManager;

import ua.com.juja.sqlcmd.Model.DataSet;
import ua.com.juja.sqlcmd.View.View;


import java.util.Arrays;

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
       this.commands= new Command[]{new Exit(view),new Help(view)};
   }


    public void run() {
       connectToDb();
      while (true) {
          view.write("Введи команду или help для помощи ");
          String command = view.read();
          if (command.equals("list")) {
              doList();

          } else if (commands[1].canProcess(command)) {
              commands[1].process(command);
          } else if (command.startsWith("find|")) {
              doFind(command);
          } else if (commands[0].canProcess(command)) {
              commands[0].process(command);
          } else {
              view.write("Несуществующая команда");
          }

      }
    }

    private void doFind(String command) {
        String[] data =command.split("\\|");
        String tableName=data[1];
        DataSet[]tableData=manager.getTableData(tableName);
        String[] tableColumns= manager.getTableColumns(tableName);
       printHeader(tableColumns);
       printTable(tableData);

    }

    private void printTable(DataSet[] tableData) {
   for(DataSet row: tableData){
       printRow(row);
   }

    }

    private void printRow(DataSet row) {
        Object[] values = row.getValues();
        String result = "|";

        for (Object value : values) {

            result += value + "|";
        }
         view.write(result);
    }
    private void printHeader(String[] tableColumns) {
        String result="|";
        for(String name: tableColumns){
            result+= name+"|";
        }
     view.write("===================");
     view.write(result);
     view.write("===================");
   }

    private void doList() {

       view.write(Arrays.toString(manager.getTableNames()));
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
