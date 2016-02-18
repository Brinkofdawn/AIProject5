package andonti_ajdooner_project5;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Incorrect Input");
            System.exit(1);
        }

        String inFile = args[0];
        CSP ourCSP = new CSP();
        ourCSP.readInput(inFile);
    }


}
