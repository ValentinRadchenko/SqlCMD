package ua.com.juja.sqlcmd.Model.integration;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.juja.sqlcmd.Controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;


/**
 * Created by Valentin_R on 03.01.2018.
 */
public class IntegrationTest {

    private  ConfigurableInputStream in;
    private  ByteArrayOutputStream out;

    @Before
    public void setup() {

        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testHelp() {

        in.add("help");


        Main.main(new String[0]);

        assertEquals("Привет мой господин\r\n" +
                "Введите имя базы пользователя и пароль в формате: connect|database|userName|Password\r\n" +
                "Существующие комманды\r\n" +
                "\tconnect|databaseName|userName|password\r\n" +
                "\t\t: Для подключения к базе данных, с которой будем работать\r\n" +
                "\tfind|tableNames\r\n" +
                "\t\t: Для получения содержимого таблицы tableNames\r\n" +
                "\tlist\r\n" +
                "\t\t: Для вывода списка всех таблиц\r\n" +
                "\tHelp\r\n" +
                "\t\t: Для вывода сриска всех комманд\r\n" +
                "\texit\r\n" +
                "\t\tДля выхода из программы\r\n" +
                "Введи команду или help для помощи \r\n" +
                "До скорой встречи!\r\n", getData());
    }



    public String getData() {
        try {
            return new String(out.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return  e.getMessage();
        }
    }

    @Test
    public void testExit(){
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Привет мой господин\r\n" +
                "Введите имя базы пользователя и пароль в формате: connect|database|userName|Password\r\n" +
                "До скорой встречи!\r\n",getData());

    }

    @Test
    public void testListWithoutConnect(){
        in.add("list");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Привет мой господин\r\n" +
                "Введите имя базы пользователя и пароль в формате: connect|database|userName|Password\r\n" +
                "Вы не можете пользоваться командой $s пока не подключитесь с помощью комманды connect|database|userName|Password\r\n" +
                "Введи команду или help для помощи \r\n" +
                "До скорой встречи!\r\n",getData());

    }
    @Test
    public void testUnsopportedAfterConnect(){
        in.add("connect|MySqlCmd|postgres|java");

        in.add("unsopported");

        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Привет мой господин\r\n" +
                "Введите имя базы пользователя и пароль в формате: connect|database|userName|Password\r\n" +
                "Успешно подключились\r\n" +
                "Введи команду или help для помощи \r\n" +
                "Несуществующая команда: unsopported\r\n" +
                "Введи команду или help для помощи \r\n" +
                "До скорой встречи!\r\n",getData());

    }


    @Test
    public void testListAfterConnect(){
        in.add("connect|MySqlCmd|postgres|java");

        in.add("list");

        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Привет мой господин\r\n" +
                "Введите имя базы пользователя и пароль в формате: connect|database|userName|Password\r\n" +
                "Успешно подключились\r\n" +
                "Введи команду или help для помощи \r\n" +
                "[users, test]\r\n" +
                "Введи команду или help для помощи \r\n" +
                "До скорой встречи!\r\n",getData());

    }

    @Test
    public void testFindUsersAfterConnect(){
        in.add("connect|MySqlCmd|postgres|java");

        in.add("find|users");

        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Привет мой господин\r\n" +
                "Введите имя базы пользователя и пароль в формате: connect|database|userName|Password\r\n" +
                "Успешно подключились\r\n" +
                "Введи команду или help для помощи \r\n" +
                "===================\r\n" +
                "|name|password|id|\r\n" +
                "===================\r\n" +
                "Введи команду или help для помощи \r\n" +
                "До скорой встречи!\r\n",getData());

    }


}