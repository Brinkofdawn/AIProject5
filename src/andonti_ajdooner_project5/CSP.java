package andonti_ajdooner_project5;
import java.io.*;
import java.util.*;

/**
 * Created by Arun on 2/17/2016.
 */
public class CSP {

    public void readInput(String inFile) {
        BufferedReader br = null;
        String line;
        List<Variables> Items = new LinkedList<Variables>();
        List<Domain> Bags = new LinkedList<Domain>();
        int bagMin = -1;
        int bagMax = -1;

        try {
            br = new BufferedReader(new FileReader(inFile));
            int tracker = 0;
            while ((line = br.readLine()) != null) {

                if (line.contains("#####")) {
                    tracker++;
                }

                else {

                    switch (tracker) {
                        // variables
                        case 1: {
                            String[] temp = line.split("\\s+");
                            Variables var = new Variables(temp[0], Integer.parseInt(temp[1]));
                            Items.add(var);
                            break;
                        }
                        //values
                        case 2: {
                            String[] temp = line.split("\\s+");
                            Domain dom = new Domain(temp[0], Integer.parseInt(temp[1]));
                            Bags.add(dom);
                            break;
                        }

                        //fitting limits
                        case 3: {
                            String[] temp = line.split("\\s+");
                            bagMin = Integer.parseInt(temp[0]);
                            bagMax = Integer.parseInt(temp[1]);
                            break;
                        }

                        //unary inclusive
                        case 4: {
                            String[] temp = line.split("\\s+");
                            for (int i = 0; i < Items.size(); i++) {
                                if(Items.get(i).getName().equals(temp[0])){
                                    for(int j = 1; j < temp.length; j++){
                                        //System.out.println(temp[j]);
                                        Items.get(i).addUnaryInclusive(temp[j]);
                                    }
                                    break;
                                }
                            }
                            break;
                        }

                        // unary exclusive
                        case 5: {
                            String[] temp = line.split("\\s+");
                            for (int i = 0; i < Items.size(); i++) {
                                if(Items.get(i).getName().equals(temp[0])){
                                    for(int j = 1; j < temp.length; j++){
                                        //System.out.println(temp[j]);
                                        Items.get(i).addUnaryExclusive(temp[j]);
                                    }
                                    break;
                                }
                            }
                            break;
                        }

                        //binary equals
                        case 6:{
                            String[] temp = line.split("\\s+");
                            for (int i = 0; i < Items.size(); i++) {
                                if(Items.get(i).getName().equals(temp[0])){
                                    Items.get(i).addBinaryEquals(temp[1]);
                                    break;
                                }
                            }
                            for (int i = 0; i < Items.size(); i++) {
                                if(Items.get(i).getName().equals(temp[1])){
                                    Items.get(i).addBinaryEquals(temp[0]);
                                    break;
                                }
                            }
                            break;
                        }

                        // binary not equals
                        case 7:
                        {
                            String[] temp = line.split("\\s+");
                            for (int i = 0; i < Items.size(); i++) {
                                if(Items.get(i).getName().equals(temp[0])){
                                    Items.get(i).addBinaryNotEquals(temp[1]);
                                    break;
                                }
                            }
                            for (int i = 0; i < Items.size(); i++) {
                                if(Items.get(i).getName().equals(temp[1])){
                                    Items.get(i).addBinaryNotEquals(temp[0]);
                                    break;
                                }
                            }
                            break;
                        }
                            // mutual inclusive
                        case 8:
                        {
                            String[] temp = line.split("\\s+");
                            for (int i = 0; i < Items.size(); i++) {
                                if (Items.get(i).getName().equals(temp[0])) {
                                    Mutualinclusive mut1 = new Mutualinclusive(temp[2], temp[1], temp[3]);
                                    Mutualinclusive mut2 = new Mutualinclusive(temp[3], temp[1], temp[2]);
                                    Items.get(i).addMutualInclusive(mut1);
                                    Items.get(i).addMutualInclusive(mut2);
                                    break;
                                }
                            }

                            for (int i = 0; i < Items.size(); i++) {
                                if (Items.get(i).getName().equals(temp[1])) {
                                    Mutualinclusive mut1 = new Mutualinclusive(temp[3], temp[0], temp[2]);
                                    Mutualinclusive mut2 = new Mutualinclusive(temp[2], temp[0], temp[3]);
                                    Items.get(i).addMutualInclusive(mut1);
                                    Items.get(i).addMutualInclusive(mut2);
                                    break;
                                }
                            }
                            break;
                        }
                    }

                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //cehcking to make sure everything is stored correctly
        System.out.println(" ITEMS ");
        for (int p = 0; p < Items.size(); p++){
            System.out.println(Items.get(p).getName() + " "+ Items.get(p).getWeight());
        }
        System.out.println(" BAGS ");
        for (int p = 0; p < Bags.size(); p++){
            System.out.println(Bags.get(p).getName() + " "+ Bags.get(p).getCapacity());
        }
        System.out.println(" FITTING LIMITS ");
        System.out.println(bagMin);
        System.out.println(bagMax);
        System.out.println(" UNARY INCLUSIVE ");
        for (int p = 0; p < Items.size(); p++){
            if(Items.get(p).unaryInclusiveCheck) {
                System.out.println("");
                System.out.print(Items.get(p).getName());
                for(int i = 0; i<Items.get(p).getUnaryInclusive().size(); i++)
                    System.out.print(" "+Items.get(p).getUnaryInclusive().get(i));
            }

        }
        System.out.println(" ");
        System.out.println(" UNARY EXCLUSIVE ");

        for (int p = 0; p < Items.size(); p++){
            if(Items.get(p).unaryExclusiveCheck) {
                System.out.println("");
                System.out.print(Items.get(p).getName());
                for(int i = 0; i<Items.get(p).getUnaryExclusive().size(); i++)
                    System.out.print(" "+Items.get(p).getUnaryExclusive().get(i));
            }

        }
        System.out.println(" ");
        System.out.println(" Binary Equals ");

        for (int p = 0; p < Items.size(); p++){
            if(Items.get(p).binaryEqualsCheck) {
                System.out.println("");
                System.out.print(Items.get(p).getName());
                for(int i = 0; i<Items.get(p).getBinaryEquals().size(); i++)
                    System.out.print(" "+Items.get(p).getBinaryEquals().get(i));
            }

        }

        System.out.println(" ");
        System.out.println(" Binary Not Equals ");

        for (int p = 0; p < Items.size(); p++){
            if(Items.get(p).binaryNotEqualsCheck) {
                System.out.println("");
                System.out.print(Items.get(p).getName());
                for(int i = 0; i<Items.get(p).getBinaryNotEquals().size(); i++)
                    System.out.print(" "+Items.get(p).getBinaryNotEquals().get(i));
            }

        }

        System.out.println(" ");
        System.out.println(" Mutual Inclusive ");

        for (int p = 0; p < Items.size(); p++){
            if(Items.get(p).mutualInclusiveCheck) {
                System.out.println("");
                for (int i = 0; i < Items.get(p).getMutualInclusive().size(); i++) {
                    System.out.print(Items.get(p).getName());
                    System.out.print(" " + Items.get(p).getMutualInclusive().get(i).thenThatItem + " " + Items.get(p).getMutualInclusive().get(i).ifThisBag + " " + Items.get(p).getMutualInclusive().get(i).inThatBag);
                    System.out.println("");
                }
            }

        }

    }
}
