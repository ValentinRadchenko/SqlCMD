package ua.com.juja.sqlcmd.Model.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.Controller.Command.Clear;
import ua.com.juja.sqlcmd.Controller.Command.Command;
import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.Model.DataSet;
import ua.com.juja.sqlcmd.View.View;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

/**
 * Created by Valentin_R on 10.01.2018.
 */
public class ClearTest {

    private DataBaseManager manager;
    private View view;
    private Command command;
    @Before
    public void setup(){

        manager=mock(DataBaseManager.class);
        view=mock(View.class);
        command=new Clear(manager,view);
    }

    @Test
    public void testClearTable(){
        //given



        //when

        command.process("clear|users");

        //then
        verify(manager).clear("users");
    verify(view).write("Таблица users была успешно очищена");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor=ArgumentCaptor.forClass(String.class);
        verify(view,atLeastOnce()).write(captor.capture());
        assertEquals(expected,captor.getAllValues().toString());
    }


    @Test
    public void testCanProcessClearWithParameters(){

         boolean canProcess= command.canProcess("clear|users");
        assertTrue(canProcess);

    }


    @Test
    public void testCanProcessClearWithoutParameters(){



        boolean canProcess= command.canProcess("clear");

        assertFalse(canProcess);

    }

    @Test
    public void testCanProcessClearCountParametersLessThen2() {

        try {
            command.process("clear");
            fail();

        }catch (IllegalArgumentException e) {
            assertEquals("  Формат команды clear|tableName , а ты ввел clear", e.getMessage());
        }
    }
    @Test
    public void testCanProcessClearCountParametersMoreThen3() {

        try {
            command.process("clear|tablename|qwe");
            fail();

        }catch (IllegalArgumentException e) {
            assertEquals("  Формат команды clear|tableName , а ты ввел clear|tablename|qwe", e.getMessage());
        }
    }
}
