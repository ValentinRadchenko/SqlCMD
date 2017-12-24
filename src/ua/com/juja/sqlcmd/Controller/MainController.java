package ua.com.juja.sqlcmd.Controller;

import ua.com.juja.sqlcmd.Model.DataBaseManager;

import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 04.12.2017.
 */
public class MainController {

    private View view;
   private DataBaseManager manager;

   public MainController(View view,DataBaseManager manager){
       this.view=view;
       this.manager=manager;
   }



    public static void main(String[] args) {

    }

    public void run() {
       connectToDb();
    }

    private void connectToDb() {
        view.write("Привет мой господин");
        view.write("Введите имя базы пользователя и пароль в формате: Database|userName|Password");
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
