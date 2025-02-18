package net.project.ems.dto;


public class CashbookDto {


    private String name;

    // Default constructor
    public CashbookDto() {
    }

    // Parameterized constructor
    public CashbookDto(String name) {
        this.name = name;
    }

    // Getter for 'name'
    public String getName() {
        return name;
    }

    // Setter for 'name'
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CashbookDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
