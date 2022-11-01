package model;

public class Hall extends Product{
    private String location;
    private int capacity;

    public Hall(String name, int rating, String description, String location, int capacity) {
        super(name, rating, description);
        this.location=location;
        this.capacity=capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
}

}
