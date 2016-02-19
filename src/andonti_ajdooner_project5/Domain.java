package andonti_ajdooner_project5;

import java.util.*;
import java.util.LinkedList;

/**
 * Created by Arun on 2/17/2016.
 */
public class Domain {
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
        }
        return temp;
    }
}