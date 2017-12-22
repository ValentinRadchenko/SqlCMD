package ua.com.juja.sqlcmd.View;

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

        Scanner scanner =new Scanner(System.in);

        return scanner.nextLine();
    }
}
