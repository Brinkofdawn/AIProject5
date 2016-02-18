package andonti_ajdooner_project5;

/**
 * Created by Arun on 2/17/2016.
 */
public class Domain {
    private String name;
    private int capacity;

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
}