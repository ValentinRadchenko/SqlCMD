package ua.com.juja.sqlcmd.Model;

/**
 * Created by Valentin_R on 03.12.2017.
 */
public class JDBCDatabaseManagerTest extends DataBaseManagerTest {
        @Override
        public DataBaseManager getDatabaseManager() {
            return new JDBCDataBaseManager();
        }

    }


