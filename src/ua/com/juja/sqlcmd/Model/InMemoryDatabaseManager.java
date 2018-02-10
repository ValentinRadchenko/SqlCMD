package ua.com.juja.sqlcmd.Model;

import java.util.*;

/**
 * Created by Valentin_R on 03.12.2017.
 */
public class InMemoryDatabaseManager implements DataBaseManager{

    public static final String TABLE_NAME = "users";


    private List<DataSet> data =new LinkedList<DataSet>();



    @Override
    public List<DataSet> getTableData(String tableName) {
      validateTable(tableName);
        return data;
    }

    private void validateTable(String tableName) {
        if(!"users".equals(tableName)){
            throw new UnsupportedOperationException("Only for 'users' table ");
        }
    }

    @Override
    public Set<String> getTableNames() {

        return new LinkedHashSet<String>(Arrays.asList(TABLE_NAME));
    }

    @Override
    public void connect(String database, String userName, String password) {

    }

    @Override
    public void clear(String tableName) {
        validateTable(tableName);
            data.clear();
    }

    @Override
    public void create(String tableName, DataSet input) {
       data.add(input);

    }

    @Override
    public void update(String tableName, int id, DataSetImpl newValue) {
     validateTable(tableName);
        for (DataSet dataSet : data) {
            if (dataSet.get("id").equals(id)) {
                dataSet.updateFrom(newValue);

            }
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) {
        return new LinkedHashSet<String>(Arrays.asList("name","password","id"));
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
