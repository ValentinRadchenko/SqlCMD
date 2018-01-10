package ua.com.juja.sqlcmd.Model.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;
import ua.com.juja.sqlcmd.Controller.Command.Command;
import ua.com.juja.sqlcmd.Controller.Command.Exit;
import ua.com.juja.sqlcmd.Controller.Command.Find;
import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.Model.DataSet;
import ua.com.juja.sqlcmd.View.View;

import javax.lang.model.util.Types;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Valentin_R on 10.01.2018.
 */
public class FindTest {

    private DataBaseManager manager;
    private View view;

    @Before
    public void setup(){

        manager=mock(DataBaseManager.class);
        view=mock(View.class);

    }

    @Test
    public void testPrintTableData(){
        //given

        Command command=new Find(manager,view);
        when(manager.getTableColumns("users")).
                thenReturn(new String[]{"id","name","password"});


        DataSet user1=new DataSet();
        user1.put("id",12);
        user1.put("name","Stiven");
        user1.put("passwors","*****");

        DataSet user2=new DataSet();
        user2.put("id",13);
        user2.put("name","Eva");
        user2.put("password","+++++");

        DataSet[] data=new DataSet[]{user1,user2};
        when(manager.getTableData("users")).thenReturn(data);
        //when

        command.process("find|users");

        //then
        ArgumentCaptor<String> captor=ArgumentCaptor.forClass(String.class);
        verify(view,atLeastOnce()).write(captor.capture());
        assertEquals("[===================, |id|name|password|," +
                " ===================, |12|Stiven|*****|," +
                " |13|Eva|+++++|]",captor.getAllValues().toString());
    }



    @Test
    public void testCanProcessFindWithParameters(){

        Command command =new Find(manager,view);

        boolean canProcess= command.canProcess("find|users");

        assertTrue(canProcess);

    }


    @Test
    public void testCanProcessFindWithoutParameters(){

        Command command =new Find(manager,view);

        boolean canProcess= command.canProcess("find");

        assertFalse(canProcess);

    }

    @Test
    public void testCanProcessFindQweParameters(){

        Command command =new Find(manager,view);

        boolean canProcess= command.canProcess("Qwe|users");

        assertFalse(canProcess);

    }

    @Test
    public void testPrintEmptyTableData(){
        //given

        Command command=new Find(manager,view);
        when(manager.getTableColumns("users")).
                thenReturn(new String[]{"id","name","password"});



        DataSet[] data=new DataSet[0];
        when(manager.getTableData("users")).thenReturn(data);
        //when

        command.process("find|users");

        //then
        ArgumentCaptor<String> captor=ArgumentCaptor.forClass(String.class);
        verify(view,atLeastOnce()).write(captor.capture());
        assertEquals(
                "[===================," +
                        " |id|name|password|," +
                         " ===================]"

                ,captor.getAllValues().toString());
    }
}
