package ua.com.juja.sqlcmd.Model;

import java.util.Arrays;

/**
 * Created by Valentin_R on 03.12.2017.
 */
public class InMemoryDatabaseManager implements DataBaseManager{

    public static final String TABLE_NAME = "users";


    private DataSet [] data =new DataSet[1000];
    private int freeIndex =0;


    @Override
    public DataSet[] getTableData(String tableName) {
      validateTable(tableName);
        return Arrays.copyOf(data, freeIndex);
    }

    private void validateTable(String tableName) {
        if(!"users".equals(tableName)){
            throw new UnsupportedOperationException("Only for 'users' table ");
        }
    }

    @Override
    public String[] getTableNames() {
        return new String [] {TABLE_NAME};
    }

    @Override
    public void connect(String database, String userName, String password) {

    }

    @Override
    public void clear(String tableName) {
        validateTable(tableName);
            data=new DataSet[1000];
            freeIndex =0;
    }

    @Override
    public void create(String tablaName, DataSet input) {
       data[freeIndex]=input;
       freeIndex++;
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (int index = 0; index < freeIndex; index++) {
            if(data[index].get("id").equals(id)){
               data[index].updateFrom(newValue);

            }
        }
    }
}
