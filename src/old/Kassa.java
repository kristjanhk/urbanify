package old;

import java.util.ArrayList;
import java.util.Scanner;

public class Kassa {
    private static boolean running = true;
    private static ArrayList<Yritus> üritused;

    public static void main(String[] args) {
        Summa summa = new Summa(19915.16);
        System.out.println(summa);
        Scanner sc = new Scanner(System.in);
        while (running) {
            //todo cmds
            //help
            //loo üritus - kysida kohe infi selle kohta
            //loo pilet - kysida nime ja hinda v2hemalt
            //loo aruanne ??
            //lisa pileti kogus vms
            //jne

            double nextSumma = sc.nextDouble();
            System.out.println(nextSumma);
            summa = new Summa(nextSumma);
            System.out.println(summa);
            /*Aruanne aruanne = new Aruanne("Aruande sisu!");
            aruanne.looAruanne();*/

        }

    }

    private static void looÜritus(String nimi) {
        üritused.add(new Yritus(nimi));
    }
}
