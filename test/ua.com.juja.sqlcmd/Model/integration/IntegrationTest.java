package ua.com.juja.sqlcmd.Model.integration;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.juja.sqlcmd.Controller.Main;
import ua.com.juja.sqlcmd.Model.DataBaseManager;
import ua.com.juja.sqlcmd.Model.DataSet;
import ua.com.juja.sqlcmd.Model.JDBCDataBaseManager;

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
    private DataBaseManager dataBaseManager;

    @Before
    public void setup() {

      dataBaseManager=new JDBCDataBaseManager();
        

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
                "\tclear|tableName\r\n" +
                "\t\t: Для очистки всей таблицы\r\n" +
                "\tcreate|tableName|column1|value1|column2|value2|...|columnN|valueN\r\n" +
                "\t\t: Для создания записи в таблице\r\n" +
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
                "Вы не можете пользоваться командой list пока не подключитесь с помощью комманды connect|database|userName|Password\r\n" +
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
    @Test
    public void testConnectWithError(){
        in.add("connect|MySqlCmd");
               in.add("exit");
        Main.main(new String[0]);
        assertEquals("Привет мой господин\r\n" +
                "Введите имя базы пользователя и пароль в формате: connect|database|userName|Password\r\n" +
                "Неудача по причине - Неверно количество параметров разделенных знаком | ожидается 4 а введено 2\r\n" +
                "Повторите попытку\r\n" +
                "Введи команду или help для помощи \r\n" +
                "До скорой встречи!\r\n",getData());
    }
    @Test
    public void testFindAfterConnect_WithData(){


        in.add("connect|MySqlCmd|postgres|java");
        in.add("clear|users");
        in.add("create|users|id|13|name|Stiven|Password|+++++");
        in.add("create|users|id|15|name|Silva|Password|*****");
        in.add("find|users");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Привет мой господин\r\n" +
                "Введите имя базы пользователя и пароль в формате: connect|database|userName|Password\r\n" +
                "Успешно подключились\r\n" +
                "Введи команду или help для помощи \r\n" +
                "Таблица users была успешно очищена\r\n" +
                "Введи команду или help для помощи \r\n" +
                "Запись {names:[id, name, Password] ,values:[13, Stiven, +++++]} была успешно создана в таблице users\r\n" +
                "Введи команду или help для помощи \r\n" +
                "Запись {names:[id, name, Password] ,values:[15, Silva, *****]} была успешно создана в таблице users\r\n" +
                "Введи команду или help для помощи \r\n" +
                "===================\r\n" +
                "|name|password|id|\r\n" +
                "===================\r\n" +
                "|Stiven|+++++|13|\r\n" +
                "|Silva|*****|15|\r\n" +
                "Введи команду или help для помощи \r\n" +
                "До скорой встречи!\r\n",getData());

    }

    @Test
    public void testCreateWithErrors(){


        in.add("connect|MySqlCmd|postgres|java");

        in.add("create|users|error");

        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Привет мой господин\r\n" +
                "Введите имя базы пользователя и пароль в формате: connect|database|userName|Password\r\n" +
                "Успешно подключились\r\n" +
                "Введи команду или help для помощи \r\n" +
                "Неудача по причинеДолжно быть четное количество параметров, а вы ввели create|users|error\r\n" +
                "Повторите попытку\r\n" +
                "Введи команду или help для помощи \r\n" +
                "До скорой встречи!\r\n",getData());

    }

    @Test
    public void testClearWithErrors(){


        in.add("connect|MySqlCmd|postgres|java");
        in.add("clear|");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Привет мой господин\r\n" +
                "Введите имя базы пользователя и пароль в формате: connect|database|userName|Password\r\n" +
                "Успешно подключились\r\n" +
                "Введи команду или help для помощи \r\n" +
                "Неудача по причине  Формат команды clear|tableName , а ты ввел clear|\r\n" +
                "Повторите попытку\r\n" +
                "Введи команду или help для помощи \r\n" +
                "До скорой встречи!\r\n",getData());

    }

}