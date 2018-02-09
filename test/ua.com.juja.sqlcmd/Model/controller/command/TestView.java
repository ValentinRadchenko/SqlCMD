package ua.com.juja.sqlcmd.Model.controller.command;

import ua.com.juja.sqlcmd.View.View;
import ua.com.juja.sqlcmd.test.Str;

/**
 * Created by Valentin_R on 10.01.2018.
 */
public class TestView implements View {

  private String messages="";
  private String input=null;

    @Override
    public void write(String message) {

        messages+=message+"\n";
    }

    @Override
    public String read() {
        if (this.input == null){
            throw new IllegalStateException();
        }
        String result = this.input;
        this.input = null;
        return result;
    }

        public  void addRead(String input){

        this.input=input;
    }

    public String getContent() {
        return messages;
    }
}
