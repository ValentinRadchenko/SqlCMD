package ua.com.juja.sqlcmd.Controller.Command;

import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 02.01.2018.
 */
public class Connect implements Command {
    private DataBaseManager manager;
    private View view;

    public Connect(DataBaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {


            try{

                String[] data = command.split("[|]");
                if(data.length!=4){
                    throw new IllegalArgumentException(" - Неверно количество параметров разделенных знаком | ожидается 4 а введено " +" "+data.length);
                }
                String databaseName = data[1];
                String userName = data[2];
                String password = data[3];
                manager.connect(databaseName, userName, password);
                view.write("Успешно подключились");

            } catch (Exception e) {

                printError(e);
            }

        }



    private void printError(Exception e) {
        String message=e.getMessage();
        if(e.getCause()!=null){message+=" "+e.getCause().getMessage();}
        view.write("Неудача по причине"+ message);
        view.write("Повторите попытку");
    }
}



