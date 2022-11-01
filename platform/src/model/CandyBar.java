package model;

import java.util.List;

public class CandyBar extends Product{
    List<String> sweets;

    public CandyBar(String name, int rating, String description, List<String> sweets) {
        super(name, rating, description);
        this.sweets = sweets;
    }

    public List<String> getSweets() {
        return sweets;
    }

    public void setSweets(List<String> sweets) {
        this.sweets =sweets;
    }

}
