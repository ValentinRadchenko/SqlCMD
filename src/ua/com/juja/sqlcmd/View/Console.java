package ua.com.juja.sqlcmd.View;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Valentin_R on 04.12.2017.
 */
public class Console implements View {
    @Override
    public void write(String message) {
        System.out.println(message);

    }

    @Override
    public String read() {
       try {
           Scanner scanner = new Scanner(System.in);
           return scanner.nextLine();
       }catch (NoSuchElementException e){
           return  null;
       }
    }
}
