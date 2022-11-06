package model;

public abstract class Product {
    private String name;
    private Integer rating;
    private String description;

    public Product(String name, String description) {
        this.name = name;
        this.rating = null;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}