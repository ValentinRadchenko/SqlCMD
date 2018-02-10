package ua.com.juja.sqlcmd.Model;

import java.util.List;
import java.util.Set;

/**
 * Created by Valentin_R on 10.02.2018.
 */
public interface DataSet {
    void put(String name, Object value);

    List<Object> getValues();

   Set<String> getNames();

    Object get(String name);

    void updateFrom(DataSet newValue);
}
