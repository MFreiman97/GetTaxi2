package com.mfreiman.driverapp.model.entities;

public class Customer {
    @Override
    public String toString() {
        return getName()+'\n'+getMailAdd();
    }


    String name;
    String Phone;
    String MailAdd;
    String id;

    public Customer() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMailAdd() {
        return MailAdd;
    }

    public void setMailAdd(String mailAdd) {
        MailAdd = mailAdd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
