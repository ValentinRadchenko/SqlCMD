package ua.com.juja.sqlcmd.Model;

/**
 * Created by Valentin_R on 03.12.2017.
 */
public interface DataBaseManager {
    DataSet[] getTableData(String tableName);

    String[] getTableNames();

    void connect(String database, String userName, String password);

    void clear(String tableName);

    void create(String tablaName, DataSet input);

    void update(String tableName, int id, DataSet newValue);
}
