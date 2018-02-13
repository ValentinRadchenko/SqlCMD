package ua.com.juja.sqlcmd.Model;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Valentin_R on 03.12.2017.
 */
public interface DataBaseManager {
    List<DataSet> getTableData(String tableName);

    int getSize(String tableName) throws SQLException;

    Set<String> getTableNames();

    void connect(String database, String userName, String password);

    void clear(String tableName);

    void create(String tablaName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    Set<String> getTableColumns(String tableName);

    boolean isConnected();
}
