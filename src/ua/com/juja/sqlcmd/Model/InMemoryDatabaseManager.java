package ua.com.juja.sqlcmd.Model;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by Valentin_R on 03.12.2017.
 */
public class InMemoryDatabaseManager implements DataBaseManager{



    public static final String TABLE_NAME = "users";

   private Map<String,List<DataSet>> tables=new LinkedHashMap<>();
 //   private List<DataSet> data =new LinkedList<DataSet>();



    @Override
    public List<DataSet> getTableData(String tableName) {
      validateTable(tableName);
        return get(tableName);
    }

    @Override
    public int getSize(String tableName)  {
        return get(tableName).size();
    }

    private void validateTable(String tableName) {
        if(!"users".equals(tableName)){
            throw new UnsupportedOperationException("Only for 'users' table ");
        }
    }

    @Override
    public Set<String> getTableNames() {

        return tables.keySet();
    }

    @Override
    public void connect(String database, String userName, String password) {

    }

    @Override
    public void clear(String tableName) {
       get(tableName).clear();



    }

    private List<DataSet> get(String tableName) {

        if(!tables.containsKey(tableName)){
            tables.put(tableName,new LinkedList<DataSet>());
        }
     return tables.get(tableName);

    }

    @Override
    public void create(String tableName, DataSet input) {
   get(tableName).add(input);

    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {

        for (DataSet dataSet : get(tableName)) {
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
