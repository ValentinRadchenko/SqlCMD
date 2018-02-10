package ua.com.juja.sqlcmd.Controller.Command;

import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.Model.DataSet;
import ua.com.juja.sqlcmd.Model.DataSetImpl;
import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 03.01.2018.
 */
public class Create implements Command {
    private DataBaseManager manager;
    private View view;

    public Create(DataBaseManager manager, View view) {

        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
         String[] data = command.split( "\\|" );
         if(data.length%2!=0){
             throw new IllegalArgumentException(String.format("Должно быть четное количество параметров, а вы ввели %s", command));
         }


        String tableName=data[1];
        DataSet dataset=new DataSetImpl();
        for (int index =1 ; index <(data.length/2) ; index++) {
            String columnName=data[index*2];
            String value=data[index*2+1];

            dataset.put(columnName,value);
        }
        manager.create(tableName,dataset);
        view.write(String.format("Запись %s была успешно создана в таблице %s", dataset, tableName));
    }
}
