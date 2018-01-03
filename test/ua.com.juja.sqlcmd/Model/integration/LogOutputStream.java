package ua.com.juja.sqlcmd.Model.integration;

import ua.com.juja.sqlcmd.test.Str;

import java.io.IOException;
import java.io.OutputStream;


/**
 * Created by Valentin_R on 03.01.2018.
 */
public class LogOutputStream extends OutputStream{


    private String log;
    @Override
    public void write(int b) throws IOException {

        byte[]bytes=new byte[]{(byte)(b&0xff00>>8),(byte)(b&0x00ff)};

        log+=new String(bytes,"UTF-8");

         }

         public String getData(){
        return log;
         }
}
