package kz.greetgo.sandbox.controller.model;

public class Filter {
    public String name;
    public String surname;
    public String patronymic;
    public String sort;
    public String order;
    public Filter(){
        this.name="";
        this.surname="";
        this.patronymic="";
        this.sort = "name";
        this.order = "";
    }
}
