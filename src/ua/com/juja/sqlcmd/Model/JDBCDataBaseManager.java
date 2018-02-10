package ua.com.juja.sqlcmd.Model;

import java.sql.*;
import java.util.*;

/**
 * Created by indigo on 21.08.2015.
 */
public class JDBCDataBaseManager implements DataBaseManager {

    private Connection connection;


    @Override
    public List<DataSet> getTableData(String tableName) {
        LinkedList<DataSet> result = new LinkedList<DataSet>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);){


            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                DataSet dataSet = new DataSetImpl();
                result.add(dataSet);
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    private int getSize(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
    }

    @Override
    public Set<String> getTableNames() {
        Set<String> tables = new LinkedHashSet<String>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");){

            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        }
    }


    @Override
    public void connect(String database, String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project.", e);
        }
          try {
            if(connection!=null){
                connection.close();
            }
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, userName,
                    password);
        } catch (SQLException e) {
              connection = null;
              throw new RuntimeException(String.format("Cant get connection for database:%s user:%s", database, userName), e);

        }
    }

     @Override
     public void clear(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public." + tableName);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(String tablaName, DataSet input) {
        try( Statement stmt = connection.createStatement();) {
             String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            stmt.executeUpdate("INSERT INTO public."+tablaName+"(" + tableNames + ")" +
                    "VALUES (" + values + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value: input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        try {
            String tableNames = getNameFormated(newValue, "%s = ?,");

            String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";
            System.out.println(sql);
            PreparedStatement ps = connection.prepareStatement(sql);

            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setObject(index, id);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String>getTableColumns(String tableName) {
        Set<String>tables = new LinkedHashSet<String>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("select column_name from information_schema.columns where table_name='users'");){


            while (rs.next()) {
                tables.add(rs.getString("column_name"));
            }


            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        }

    }

    @Override
    public boolean isConnected() {
        return connection!=null;
    }

    private String getNameFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }
}
