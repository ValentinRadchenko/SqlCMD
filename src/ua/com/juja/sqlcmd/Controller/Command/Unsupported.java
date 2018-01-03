package ua.com.juja.sqlcmd.Controller.Command;

import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 02.01.2018.
 */
public class Unsupported implements Command {
    private View view;

    public Unsupported(View view) {

        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write("Несуществующая команда: "+command);
    }
}
