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


    boolean DEBUG = true;
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

    public LinkedList<Variables> validityCheck(Domain bag, Variables item, int itemAmount, List<Variables> allVariables, List<Domain> allBags, List<Variables> addedAlready) {
        LinkedList<Variables> tempLinkedList = new LinkedList<Variables>();
        tempLinkedList.add(item);

        if (item.isAssigned() && !bag.getStoredItems().contains(item)) { //In case it's already assigned and not in the bag we're looking to put it in
            return null;
        }

        if ((bag.getCapacity() - bag.getTotalWeight()) < item.getWeight()) { //If the item's weight is larger than what's left in the bag we want to put it in
            return null;
        }
        if (itemAmount >= bagMax) {
            if (DEBUG) {
                System.out.println("Total item amount exceeds bagMax of " + bagMax + ". Cannot include " + item.getName() + ".");
            }
            return null;
        }

        if (item.isUnaryInclusiveCheck()) { //There are only certain bags this item can be in
            if (!item.getUnaryInclusive().contains(bag.getName())) { //If this bag is not one of them
                if (DEBUG){
                    System.out.println("Unary inclusive requirement for " + item.getName()+". Bag " + bag.getName() + " is not included in this requirement.");
                }
                return null;
            }
        }

        if (item.isUnaryExclusiveCheck()) { //There are certain bags it can't be in
            if (item.getUnaryExclusive().contains(bag.getName())){ //This is one of them
                if (DEBUG) {
                    System.out.println("Unary exclusive requirement for " + item.getName()+". Bag " + bag.getName() + " is included in this requirement.");
                }
                return null;
            }
        }
        bag.addItemToBag(item); //Add items to list of bags
        item.Assign();
        //Checks are now binary
        if(item.isBinaryEqualsCheck()){ //Check to see if we have binary equals constraints (SAME BAG REQUIREMENT)
            for (int i = 0; i < item.getBinaryEquals().size(); i++){ //Iterate over each of the binary equals constraints
                for (int j = 0; j < allVariables.size(); j++){ //Iterate over the variables present
                    if(allVariables.get(j).getName().equals(item.getBinaryEquals().get(i))){ //Get the binary equals requirement
                        boolean temp = true;
                        if (bag.getStoredItems().contains(allVariables.get(j))){ //If it actually satisfies the requirement
                            temp = false;
                        }
                        if (temp) {
                            boolean result = tempLinkedList.addAll(validityCheck(bag, allVariables.get(j), itemAmount + 1, allVariables, allBags, addedAlready)); //Add all elements it creates
                            if(!result) { //It actually failed
                                System.out.println("A binary equals condition is present between " + item.getName() + " and " + allVariables.get(j) + ". However, there is not enough space to accommodate both.");
                                bag.removeFromBag(item);
                                item.deAssign();
                                return null;
                            }
                        }
                    }
                }
            }
        }

        //Binary not equals
        if(item.isBinaryNotEqualsCheck()){ //Check to see if we have any binary not equals constrainst (NOT SAME BAG REQUIREMENT)
            for (int i = 0; i < item.getBinaryNotEquals().size(); i++){ //Iterate over each of the binary not equals constraints
                for (int j = 0; j < allVariables.size(); j++){ //Iterate over the variables present
                    if(allVariables.get(j).getName().equals(item.getBinaryNotEquals().get(i))){ //Get the binary not equal
                        if(bag.getStoredItems().contains(allVariables.get(j))){
                            System.out.println("A binary not equals condition is present between " + item.getName() + " and " + allVariables.get(j) + ". However, the latter is already in the bag.");
                            bag.removeFromBag(item);
                            item.deAssign();
                            return null;
                        }
                    }
                }
            }
        }
        //Mutual inclusive, seems to loop back indefinitely
        for(int i = 0; i < item.getMutualInclusive().size(); i++){ //Check to see if any mutually inclusive dependencies
            if(item.getMutualInclusive().get(i).ifThisBag.equals(bag.getName())){
                int it = 0;
                int ba = 0;
                for (int j = 0; j < allVariables.size(); j++){
                    if(allVariables.get(j).getName().equals(item.getMutualInclusive().get(i).thenThatItem)){
                        it = j;
                    }
                }
                for (int j = 0; j < allBags.size(); j++){
                    if(allBags.get(j).getName().equals(item.getMutualInclusive().get(i).inThatBag)){
                        ba = j;
                    }
                }
                for (int j = 0; j < allVariables.size(); j++){
                    if(allVariables.get(j).getName().equals(item.getMutualInclusive().get(i).thenThatItem)){
                        it = j;
                    }
                }
                boolean newTemp = true;
                if(allBags.get(ba).getStoredItems().contains(allVariables.get(it))){
                    newTemp = false;
                }

                if (newTemp){
                    boolean result = tempLinkedList.addAll(validityCheck(allBags.get(ba), allVariables.get(it), itemAmount + 1, allVariables, allBags, addedAlready));
                    if (!result) {
                        System.out.println("A mutual inclusive condition is present between " + item.getName() + " at bag " + bag.getName() + " and " + allVariables.get(it).getName() + " at bag " + allBags.get(ba).getName() + ".");
                        bag.removeFromBag(item);
                        item.deAssign();
                        return null;
                    }
                }
            }
        }

        return tempLinkedList;
    }

    public List<Variables> duplicateListOfVariables(List<Variables> initialVars) {
        List<Variables> tempVars = new LinkedList<Variables>();
        for (Variables x : initialVars){
            tempVars.add(x.duplicateItem());
        }
        return tempVars;
    }

    public List<Domain> duplicateListOfBags(List<Domain> initialBags) {
        List<Domain> tempBags =  new LinkedList<Domain>();
        for (Domain y : initialBags) {
            tempBags.add(y.duplicateBag());
        }
        return tempBags;
    }

    public void clearCheckingBags(){
        for (int i = 0; i < tempVariables.size(); i++){
            for (int j = 0; j < tempBags.size(); j++){
                if (tempBags.get(j).getStoredItems().contains(tempVariables.get(i))){
                    tempVariables.get(i).deAssign();
                    tempBags.get(j).removeFromBag(tempVariables.get(i));
                }
            }
        }

        tempVariables.clear();
        tempBags.clear();
    }
    //This needs to be adapted
    public LinkedList<Domain> getPossibleBags(Variables item){
        LinkedList<Domain> tempList = new LinkedList<Domain>();
        LinkedList<Variables> dupItems;
        LinkedList<Domain> dupBags;
        for (int i = 0; i < Bags.size(); i++){
            Domain tempBag = Bags.get(i);
            List<Variables> allVariables = duplicateListOfVariables(Items);
            List<Domain> allBags = duplicateListOfBags(Bags);
            List<Variables> tempVars = new LinkedList<Variables>();
            if (validityCheck(tempBag, item, 1, allVariables, allBags, tempVars) != null){ //Is this right?
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

    // sorting the bags by capacity constraint
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
        //return true;
        return minLimit && noUnassignedItems;
    }

// print the correct output if we find an answer
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

    // we might need another validity checking function in order for this to work, this could also be written wrong
    //Problem is that if something doesn't work, it only removes one thing
    public LinkedList<Domain> backtrackForward() {
        //using this for now need to change to better heuristic
        Variables i = minimumRemainingValues();

        if(i == null) {
            return new LinkedList<Domain>();
        }
        // Prune bags that are not valid
        LinkedList<Domain> possibleBags = getPossibleBags(i);

        // Order bags so that the least constraining bags are first
        LinkedList<Domain> orderedBags = minBag(possibleBags);

        // iterating over list of possible bags
        for(int j = 0; j < orderedBags.size(); j++) {
            if(validityCheck(orderedBags.get(j), orderedBags.get(j).getTotalWeight(),orderedBags.get(j).getTotalItems(),orderedBags.get(j),i,false)) {//Fix this
                //clearCheckingBags();
                //orderedBags.get(j).addItemToBag(i);
                //i.Assign();
                // Recursive call
                LinkedList<Domain> results = backtrackForward();

                if(results == null) { //Improper results
                    clearCheckingBags();
                }
            }
            else { //Improper results
                clearCheckingBags();
            }
        }
            //System.out.print("wtf");
        if(isComplete()) {
            printOutput();
            return (LinkedList<Domain>) Bags;
        }

        return null;
    }
}
