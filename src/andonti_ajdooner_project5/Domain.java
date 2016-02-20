package andonti_ajdooner_project5;

import java.util.*;
import java.util.LinkedList;

/**
 * Created by Arun on 2/17/2016.
 */
public class Domain implements Comparable<Domain>{
    private String name;
    private int capacity;
    private int totalWeight = 0;
    private int totalItems = 0;
    private List<Variables> storedItems = new LinkedList<Variables>();

    public Domain(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addItemToBag(Variables item) {

            storedItems.add(item);
            totalWeight += item.getWeight();
            totalItems++;

    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public int getTotalItems() {
        return totalItems;
    }


    public List<Variables> getStoredItems() {
        return storedItems;
    }

    public boolean removeFromBag(Variables item) {

        boolean temp = storedItems.remove(item);
        if (temp){
            totalItems--;
            totalWeight = totalWeight - item.getWeight();
        }
        return temp;
    }
    public int compareTo(Domain b2) {
        int b1A = this.getCapacity() - this.getTotalWeight();
        int b2A = b2.getCapacity() - b2.getTotalWeight();
        if(b1A > b2A) {
            return -1;
        }
        else if(b1A < b2A) {
            return 1;
        }
        else
            return 0;
    }
}