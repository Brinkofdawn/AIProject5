package andonti_ajdooner_project5;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Arthur on 2/20/2016.
 */

public class tempClass {
    public LinkedList<Variables> validityCheck(Domain bag, Variables item, int itemAmount, List<Variables> allVariables, LinkedList<Domain> allBags, LinkedList<Variables> addedAlready) {
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
}
