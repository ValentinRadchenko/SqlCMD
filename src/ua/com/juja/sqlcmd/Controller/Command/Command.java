package ua.com.juja.sqlcmd.Controller.Command;

/**
 * Created by Valentin_R on 29.12.2017.
 */
public interface Command {
    boolean canProcess(String command);
    void process(String command);
}
