package andonti_ajdooner_project5;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Arun on 2/17/2016.
 */
public class Variables {
    private String name;
    private int weight;
    private boolean mutualInclusiveCheck = false;
    private boolean unaryInclusiveCheck = false;
    private boolean unaryExclusiveCheck = false;
    private boolean binaryEqualsCheck = false;
    private boolean binaryNotEqualsCheck = false;
    private boolean Assigned = false;
    private List<Mutualinclusive> mutualInclusive = new LinkedList<Mutualinclusive>();
    private List<String> unaryInclusive = new LinkedList<String>();
    private List<String> unaryExclusive = new LinkedList<String>();
    private List<String> binaryEquals = new LinkedList<String>();
    private List<String> binaryNotEquals = new LinkedList<String>();

    public Variables(String name, int weight){
        this.name = name;
        this.weight = weight;
    }
    public Variables duplicateItem(){
        Variables itemCopy = new Variables(name, weight);
        if (isAssigned()) {
            itemCopy.Assign();
        }
        if (mutualInclusiveCheck) {
            for (Mutualinclusive i : mutualInclusive) {
                itemCopy.addMutualInclusive(i);
            }
        }
        if (unaryInclusiveCheck) {
            for (String j : unaryInclusive) {
                itemCopy.addUnaryInclusive(j);
            }
        }
        if (unaryExclusiveCheck) {
            for (String k : unaryExclusive) {
                itemCopy.addUnaryExclusive(k);
            }
        }
        if (binaryEqualsCheck) {
            for (String l : binaryEquals) {
                itemCopy.addBinaryEquals(l);
            }
        }
        if (binaryNotEqualsCheck) {
            for (String m : binaryNotEquals) {
                itemCopy.addBinaryNotEquals(m);
            }
        }
        return itemCopy;
    }
    public boolean isAssigned() {
        return Assigned;
    }

    public void Assign() {
        Assigned = true;
    }
    public void deAssign() {
        Assigned = false;
    }

    public List<Mutualinclusive> getMutualInclusive() {
        return mutualInclusive;
    }

    public boolean isMutualInclusiveCheck() {
        return mutualInclusiveCheck;
    }

    public boolean isBinaryEqualsCheck() {
        return binaryEqualsCheck;
    }

    public void addMutualInclusive(Mutualinclusive mut){
        mutualInclusiveCheck = true;
        mutualInclusive.add(mut);
    }

    public boolean isBinaryNotEqualsCheck() {
        return binaryNotEqualsCheck;
    }

    public void addBinaryEquals(String item){
        binaryEqualsCheck = true;
        binaryEquals.add(item);
    }

    public void addBinaryNotEquals(String item){
        binaryNotEqualsCheck = true;
        binaryNotEquals.add(item);
    }
    public List<String> getBinaryEquals() {
        return binaryEquals;
    }

    public List<String> getBinaryNotEquals() {
        return binaryNotEquals;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isUnaryInclusiveCheck() {
        return unaryInclusiveCheck;
    }

    public boolean isUnaryExclusiveCheck() {
        return unaryExclusiveCheck;
    }

    public void addUnaryInclusive(String bag){
        unaryInclusiveCheck = true;
        unaryInclusive.add(bag);
    }

    public void addUnaryExclusive(String bag){
        unaryExclusiveCheck = true;
        unaryExclusive.add(bag);
    }

    public List<String> getUnaryInclusive() {
        return unaryInclusive;
    }

    public List<String> getUnaryExclusive() {
        return unaryExclusive;
    }
}
