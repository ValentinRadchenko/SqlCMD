package ua.com.juja.sqlcmd.Model;

/**
 * Created by Valentin_R on 10.02.2018.
 */
public interface DataSet {
    void put(String name, Object value);

    Object[] getValues();

    String[] getNames();

    Object get(String name);

    void updateFrom(DataSet newValue);
}
