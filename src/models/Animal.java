package models;

public class Animal {
    private String type;

    public Animal(String type) {
        this.type = type;
    }

    // getter와 setter 메서드
    public String getType() { return type; }

    public void setType(String type) {
        this.type = type;
    }
}
