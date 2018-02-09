package ua.com.juja.sqlcmd.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by indigo on 21.08.2015.
 */
public abstract  class DataBaseManagerTest {

    private DataBaseManager manager;


    public abstract DataBaseManager getDatabaseManager();

    @Before
    public void setup() {


        manager = new JDBCDataBaseManager();
        manager.connect("MySqlCmd", "postgres", "java");
    }

    @Test
    public void testGetAllTableNames() {
        Set<String> tableNames = manager.getTableNames();
        assertEquals("[users, test]", tableNames.toString());
    }

    @Test
    public void testGetTableData() {
        // given
        manager.clear("users");

        // when
        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 13);
        manager.create("users",input);

        // then
        DataSet[] users = manager.getTableData("users");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven, pass, 13]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateTableData() {
        // given
        manager.clear("users");

        DataSet input = new DataSet();
        input.put("name", "Stiven");
        input.put("password", "pass");
        input.put("id", 13);
        manager.create("users", input);

        // when
        DataSet newValue = new DataSet();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        manager.update("users", 13, newValue);

        // then
        DataSet[] users = manager.getTableData("users");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
        assertEquals("[Pup, pass2, 13]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testGetColumnNames(){
        manager.clear("users");

        Set<String>columnNames=manager.getTableColumns("users");


        assertEquals("[name, password, id]",columnNames.toString());
    }

    @Test
    public void testisConnected(){
        assertTrue(manager.isConnected());
    }
}
