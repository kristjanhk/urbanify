import java.util.ArrayList;
import java.util.Scanner;

public class Kassa {
    private static boolean running = true;
    private static ArrayList<Üritus> üritused;

    public static void main(String[] args) {
        Summa summa = new Summa(19915.16);
        System.out.println(summa);
        Scanner sc = new Scanner(System.in);
        while (running) {
            //todo clear console
            //todo print current info etc
            //todo scan for cmd

            //todo cmds
            //help
            //loo üritus - kysida kohe infi selle kohta
            //loo pilet - kysida nime ja hinda v2hemalt
            //loo aruanne ??
            //lisa pileti kogus vms
            //jne


            //käsurea jaoks tuleb mingi loogiline graafiline süsteem teha
            //tavalisele graafilisele liidesele sobib praegu paremini

            //http://commons.apache.org/proper/commons-cli/
            //http://commons.apache.org/proper/commons-cli/usage.html
            //http://alvinalexander.com/java/edu/pj/pj010005

            //näidis vb https://www.wowza.com/forums/content.php?151-How-to-set-up-a-command-line-interface-to-Wowza-API
            //String next = sc.nextLine();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            double nextSumma = sc.nextDouble();
            System.out.println(nextSumma);
            summa = new Summa(nextSumma);
            System.out.println(summa);

        }

    }

    private static void looÜritus(String nimi) {
        üritused.add(new Üritus(nimi));
    }
}
