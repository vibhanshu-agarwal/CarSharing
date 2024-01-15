package carsharing;

public class Company {
    private int id;
    private String name;

//    public Company() {
//    }

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

//    public Company(String name) {
//        this.name = name;
//    }

    public int getId() {
        return id;
    }

//    public Company setId(int id) {
//        this.id = id;
//        return this;
//    }

    public String getName() {
        return name;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }
}
