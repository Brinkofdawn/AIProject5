package andonti_ajdooner_project5;
import com.sun.org.apache.xpath.internal.operations.Variable;

import java.io.*;
import java.util.*;

/**
 * Created by Arun on 2/17/2016.
 */
public class CSP {
    List<Variables> Items = new LinkedList<Variables>();
    List<Domain> Bags = new LinkedList<Domain>();
    List<Variables> tempVariables = new LinkedList<Variables>();
    List<Domain> tempBags = new LinkedList<Domain>();


    int bagMin = -1;
    int bagMax = -1;
    public void readInput(String inFile) {
        BufferedReader br = null;
        String line;


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


        /*
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
            if(Items.get(p).isUnaryInclusiveCheck()) {
                System.out.println("");
                System.out.print(Items.get(p).getName());
                for(int i = 0; i<Items.get(p).getUnaryInclusive().size(); i++)
                    System.out.print(" "+Items.get(p).getUnaryInclusive().get(i));
            }

        }
        System.out.println(" ");
        System.out.println(" UNARY EXCLUSIVE ");

        for (int p = 0; p < Items.size(); p++){
            if(Items.get(p).isUnaryExclusiveCheck()) {
                System.out.println("");
                System.out.print(Items.get(p).getName());
                for(int i = 0; i<Items.get(p).getUnaryExclusive().size(); i++)
                    System.out.print(" "+Items.get(p).getUnaryExclusive().get(i));
            }

        }
        System.out.println(" ");
        System.out.println(" Binary Equals ");

        for (int p = 0; p < Items.size(); p++){
            if(Items.get(p).isBinaryEqualsCheck()) {
                System.out.println("");
                System.out.print(Items.get(p).getName());
                for(int i = 0; i<Items.get(p).getBinaryEquals().size(); i++)
                    System.out.print(" "+Items.get(p).getBinaryEquals().get(i));
            }

        }

        System.out.println(" ");
        System.out.println(" Binary Not Equals ");

        for (int p = 0; p < Items.size(); p++){
            if(Items.get(p).isBinaryNotEqualsCheck()) {
                System.out.println("");
                System.out.print(Items.get(p).getName());
                for(int i = 0; i<Items.get(p).getBinaryNotEquals().size(); i++)
                    System.out.print(" "+Items.get(p).getBinaryNotEquals().get(i));
            }

        }

        System.out.println(" ");
        System.out.println(" Mutual Inclusive ");

        for (int p = 0; p < Items.size(); p++){
            if(Items.get(p).isMutualInclusiveCheck()) {
                System.out.println("");
                for (int i = 0; i < Items.get(p).getMutualInclusive().size(); i++) {
                    System.out.print(Items.get(p).getName());
                    System.out.print(" " + Items.get(p).getMutualInclusive().get(i).thenThatItem + " " + Items.get(p).getMutualInclusive().get(i).ifThisBag + " " + Items.get(p).getMutualInclusive().get(i).inThatBag);
                    System.out.println("");
                }
            }

        }
        */

        backtrackForward();

    }

// maybe this needs to be slightly edited for different algorithms
    public boolean canAdd(int weight, int itemAmount, Domain bag, Variables item, boolean check){
        // if we can add to the bag before checking any binary constraints
        //Is it already assigned?
        if(item.isAssigned()){
            return false;
        }

        if(itemAmount >=  bagMax){
            return false;
        }

        if(weight + item.getWeight() >= bag.getCapacity()){
            return false;
        }

        if(item.isUnaryInclusiveCheck()){
            if(item.getUnaryInclusive().contains(bag.getName()) == false){
                return false;
            }
        }

        if(item.isUnaryExclusiveCheck()){
            if(item.getUnaryExclusive().contains(bag.getName())){
                return false;
            }
        }

        // if it gets past these first checks now have to check with binary

        if(item.isBinaryEqualsCheck()){
            for (int i = 0; i < item.getBinaryEquals().size(); i++){
                for (int j = 0; j < Items.size(); j++){
                    if(Items.get(j).getName().equals(item.getBinaryEquals().get(i))){
                        if(bag.getStoredItems().contains(j)){

                        }
                        else if(canAdd(weight + item.getWeight(), itemAmount +1, bag,Items.get(j), check) == false){
                            return false;
                        }
                    }
                }
            }
        }

        if(item.isBinaryNotEqualsCheck()){
            for (int i = 0; i < item.getBinaryNotEquals().size(); i++){
                for (int j = 0; j < Items.size(); j++){
                    if(Items.get(j).getName().equals(item.getBinaryNotEquals().get(i))){
                        if(bag.getStoredItems().contains(j)){
                            return false;
                        }
                    }
                }
            }
        }

        for(int i = 0; i < item.getMutualInclusive().size(); i++){
            if(item.getMutualInclusive().get(i).ifThisBag.equals(bag.getName())){
                int it = 0;
                int ba = 0;
                for (int j = 0; j < Items.size(); j++){
                    if(Items.get(j).getName().equals(item.getMutualInclusive().get(i).thenThatItem)){
                        it = j;
                    }
                }
                for (int j = 0; j < Bags.size(); j++){
                    if(Bags.get(j).getName().equals(item.getMutualInclusive().get(i).inThatBag)){
                        ba = j;
                    }
                }
                for (int j = 0; j < Items.size(); j++){
                    if(Items.get(j).getName().equals(item.getMutualInclusive().get(i).thenThatItem)){
                        it = j;
                    }
                }
                if(Bags.get(ba).getStoredItems().contains(Items.get(it))){

                }
                else if(canAdd(weight + item.getWeight(), itemAmount + 1, Bags.get(ba),Items.get(it), check) == false){
                    return false;
                }
            }
        }

        if(check) {
            tempVariables.add(item); //Add item to list of temporarily added things
        }
        bag.addItemToBag(item); //Add items to list of bags

        if(check) {
            tempBags.add(bag);
        }
        item.Assign();
        System.out.println("True");
        return true;
    }

    public void clearCheckingBags(){
        for(int i = 0; i < tempVariables.size(); i++){
            for(int j = 0; j < tempBags.size(); j++){
                if(tempBags.get(j).getStoredItems().contains(tempVariables.get(i))){
                    tempVariables.get(i).deAssign();
                    tempBags.get(j).removeFromBag(tempVariables.get(i));

                }
            }
        }

        tempVariables.clear();
        tempBags.clear();
    }

    public LinkedList<Domain> getPossibleBags(Variables item){
        LinkedList<Domain> tempList = new LinkedList<Domain>();
        for(int i = 0; i < Bags.size(); i++){
            Domain tempBag= Bags.get(i);
            if(canAdd(tempBag.getTotalWeight(),tempBag.getTotalItems(),tempBag,item,true)){
                clearCheckingBags();
                tempList.add(tempBag);
            }
            else{
                clearCheckingBags();
            }
        }
        return tempList;
    }

    // need to change this later to not just return first unassigned
    public Variables minimumRemainingValues(){

        for(int i = 0; i < Items.size(); i++ ){
            if(Items.get(i).isAssigned() == false){
                return Items.get(i);
            }
        }

        return null;
    }

    public LinkedList<Domain> minBag(LinkedList<Domain> bagList) {
        LinkedList<Domain> orderedBags = bagList;
        Collections.sort(orderedBags);
        return orderedBags;
    }

    // checking to see if the assignment fits end constraints
    public boolean overMinItems(){
        for(int i = 0; i < Bags.size(); i++){
            // checking to see if it meets the min items and min weight requirement
            if(Bags.get(i).getTotalItems() < bagMin || Bags.get(i).getTotalWeight() < 0.9 * Bags.get(i).getCapacity()){
                return false;
            }
        }
        return true;
    }

    public boolean noUnassignedItems(){
        for (int i = 0; i < Items.size(); i++){
            if(Items.get(i).isAssigned() == false){
                return false;
            }
        }

        return true;
    }

    // returns true if all requirements are met, returns false otherwise
    public boolean isComplete(){
        //boolean isValid
        boolean minLimit = overMinItems();
        boolean noUnassignedItems = noUnassignedItems();
        return minLimit && noUnassignedItems;
    }


    public void printOutput(){
        for (int i =0; i<Bags.size(); i++){
            System.out.print(Bags.get(i).getName());
            for (int j =0; j< Bags.get(i).getStoredItems().size(); j++){
                System.out.print(" "+Bags.get(i).getStoredItems().get(j).getName());
            }
            System.out.println();
            System.out.println("number of items: " + Bags.get(i).getTotalItems());
            System.out.println("total weight: " + Bags.get(i).getTotalWeight() + "/" + Bags.get(i).getCapacity());
            int waste = -(Bags.get(i).getTotalWeight()- Bags.get(i).getCapacity());
            System.out.println("wasted capacity: " + waste );

        }
    }

    public LinkedList<Domain> backtrackForward() {

           Variables i = minimumRemainingValues();
        //using this for now
        if(i == null) {
            return new LinkedList<Domain>();
        }
        // Prune bags that are not valid
        LinkedList<Domain> possibleBags = getPossibleBags(i);

        // Order bags so that the least constraining bags are first
        LinkedList<Domain> orderedBags = minBag(possibleBags);


        for(int j = 0; j < orderedBags.size(); j++) {

            if(canAdd(orderedBags.get(j).getTotalWeight(),orderedBags.get(j).getTotalItems(),orderedBags.get(j),i,true)) {
                clearCheckingBags();
                orderedBags.get(j).addItemToBag(i);
                // Recursive call
                LinkedList<Domain> results = backtrackForward();

                if(results == null) {
                   orderedBags.get(j).removeFromBag(i);
                }
            }
            else {
                clearCheckingBags();
            }
        }
            System.out.print("wtf");
        if(isComplete()) {
            printOutput();
            return (LinkedList<Domain>) Bags;
        }

        return null;
    }
}
