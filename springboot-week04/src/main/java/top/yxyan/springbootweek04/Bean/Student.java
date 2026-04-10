package top.yxyan.springbootweek04.Bean;

public class Student {
    private String name;
    private int id;

    public void study(){
        System.out.println("Student " + name + " is studying");
    }

    public Student() {
    }

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }


}
