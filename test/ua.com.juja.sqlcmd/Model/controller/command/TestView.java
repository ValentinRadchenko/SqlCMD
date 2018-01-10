package ua.com.juja.sqlcmd.Model.controller.command;

import ua.com.juja.sqlcmd.View.View;

/**
 * Created by Valentin_R on 10.01.2018.
 */
public class TestView implements View {

  private String messages="";

    @Override
    public void write(String message) {
     messages+=message+"\n";
    }

    @Override
    public String read() {
        return null;
    }

    public String getContent() {
        return messages;
    }
}
