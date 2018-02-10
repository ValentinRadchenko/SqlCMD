package ua.com.juja.sqlcmd.Model.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;
import ua.com.juja.sqlcmd.Controller.Command.Command;
import ua.com.juja.sqlcmd.Controller.Command.Find;
import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.Model.DataSet;
import ua.com.juja.sqlcmd.Model.DataSetImpl;
import ua.com.juja.sqlcmd.View.View;

import java.util.*;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Valentin_R on 10.01.2018.
 */
public class FindTest {

    private DataBaseManager manager;
    private View view;
    private Command command;
    @Before
    public void setup(){

        manager=mock(DataBaseManager.class);
        view=mock(View.class);
        command=new Find(manager,view);
    }

    @Test
    public void testPrintTableData(){
        //given

 setupTableColumns("users","id","name","password");


        DataSet user1=new DataSetImpl();
        user1.put("id",12);
        user1.put("name","Stiven");
        user1.put("passwors","*****");

        DataSet user2=new DataSetImpl();
        user2.put("id",13);
        user2.put("name","Eva");
        user2.put("password","+++++");

        //List<DataSet> data=new LinkedList<DataSet>();
        when(manager.getTableData("users")).thenReturn(Arrays.asList(user1,user2));
        //when

        command.process("find|users");
        String expected = "[===================, " +
                "|id|name|password|," +
                " ===================, " +
                "|12|Stiven|*****|, " +
                "|13|Eva|+++++|]";

        //then
        shouldPrint(expected);
    }

    private void setupTableColumns(String users, String ...args) {

        when(manager.getTableColumns("users")).
                thenReturn(new LinkedHashSet<String>(Arrays.asList("id","name","password")));

    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor=ArgumentCaptor.forClass(String.class);
        verify(view,atLeastOnce()).write(captor.capture());
        assertEquals(expected,captor.getAllValues().toString());
    }


    @Test
    public void testCanProcessFindWithParameters(){

         boolean canProcess= command.canProcess("find|users");
        assertTrue(canProcess);

    }


    @Test
    public void testCanProcessFindWithoutParameters(){



        boolean canProcess= command.canProcess("find");

        assertFalse(canProcess);

    }

    @Test
    public void testCanProcessFindQweParameters(){


        boolean canProcess= command.canProcess("Qwe|users");

        assertFalse(canProcess);

    }

    @Test
    public void testPrintEmptyTableData(){
        //given
        setupTableColumns("users","id","name","password");

       // List<DataSet> data=new LinkedList<>();
        when(manager.getTableData("users"))
                .thenReturn(new ArrayList<DataSet>());
        //when

        command.process("find|users");

        //then
        shouldPrint("[===================," +
                " |id|name|password|," +
                 " ===================]");
    }
    @Test
    public void testPrintTableDataWithOneColumn(){

         setupTableColumns("test","id");
         DataSet user1=new DataSetImpl();
         user1.put("id",12);
         DataSet user2=new DataSetImpl();
         user2.put("id",13);
         when(manager.getTableData("test")).thenReturn(Arrays.asList(user1,user2));

         command.process("find|test");

        //then
        shouldPrint("[===================," +
                " |id|," +
                " ===================, "+
                " |12|," +
                " |13|," +
                " ===================]");
    }


}
