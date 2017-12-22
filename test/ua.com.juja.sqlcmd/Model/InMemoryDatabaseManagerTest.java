package ua.com.juja.sqlcmd.Model;

/**
 * Created by Valentin_R on 03.12.2017.
 */
public class InMemoryDatabaseManagerTest extends DataBaseManagerTest {
    @Override
    public DataBaseManager getDatabaseManager() {
        return new InMemoryDatabaseManager();
    }

}
