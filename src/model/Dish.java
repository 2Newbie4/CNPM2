package model;

public class Dish {
    private int id;
    private String name;
    private String type;
    private double price;
    private String image;
    private String description;
    private String status;

    public Dish() {
    }

    public Dish(String name, String type, double price, String image, String description, String status) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.image = image;
        this.description = description;
        this.status = status;
    }

    public Dish(int id, String name, String type, double price, String image, String description, String status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.image = image;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
