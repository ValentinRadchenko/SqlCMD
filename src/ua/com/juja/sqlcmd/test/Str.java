package ua.com.juja.sqlcmd.test;

import com.sun.media.jfxmediaimpl.NativeMediaPlayer;

/**
 * Created by Valentin_R on 09.12.2017.
 */
public class Str {



  public   String result = "";
    public  String mathr = "";
   public int [] res;

    public int [] Merrr(String[] arg) {
        for (int i = 0; i < arg.length; i++) {
            result += arg[i];
        }
        char[] a = result.toCharArray();
        for (int j = 0; j < a.length; j++) {
            if (Character.isDigit(result.charAt(j))&&result.charAt(j)%2!= 0) {

                mathr+=a[j];

            } else {
                continue;
            }
            //res =new int[] {Integer.valueOf(mathr)};
        }

        return new int[]{Integer.valueOf(mathr)};
    }
}