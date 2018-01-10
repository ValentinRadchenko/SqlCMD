package ua.com.juja.sqlcmd.Model.controller.command;

import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.sqlcmd.Controller.Command.Command;
import ua.com.juja.sqlcmd.Controller.Command.Exit;
import ua.com.juja.sqlcmd.Controller.Command.ExitException;
import ua.com.juja.sqlcmd.View.View;

import static junit.framework.TestCase.*;

/**
 * Created by Valentin_R on 10.01.2018.
 */
public class ExitWithMockitoTest {

    private View view= Mockito.mock(View.class);

    @Test
    public void testCanProcessExitString(){

        Command command =new Exit(view);

        boolean canProcess= command.canProcess("exit");

        assertTrue(canProcess);

    }


    @Test
    public void testCanProcessExitgdfString(){

        Command command =new Exit(view);

        boolean canProcess= command.canProcess("gdf");

        assertFalse(canProcess);

    }
    @Test
    public void testProcessExitCommand_throwsExitException(){

        Command command =new Exit(view);
try {
    command.process("exit");
    fail("Expected ExitException");
}catch (ExitException e) {


}
       Mockito.verify(view).write("До скорой встречи!");

    }




}

