package ua.com.juja.sqlcmd.Controller.Command;

import ua.com.juja.sqlcmd.Controller.Command.ExitException;
import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 29.12.2017.
 */
public class Exit implements Command {

    private View view;

    public Exit(View view) {

        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(String command) {
        view.write("До скорой встречи!");
        throw new ExitException();

    }
}