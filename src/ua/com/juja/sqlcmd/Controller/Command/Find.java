package ua.com.juja.sqlcmd.Controller.Command;

import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.Model.DataSet;
import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 02.01.2018.
 */
public class Find implements Command {

    private DataBaseManager manager;
    private View view;

    public Find(DataBaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
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

}
