package com.mega.print.enity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name_user;
    private String name;
    private String last_name;
    private String telephon_number;
    private String address_mail;
    private String number_mail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTelephon_number() {
        return telephon_number;
    }

    public void setTelephon_number(String telephon_number) {
        this.telephon_number = telephon_number;
    }

    public String getAddress_mail() {
        return address_mail;
    }

    public void setAddress_mail(String address_mail) {
        this.address_mail = address_mail;
    }

    public String getNumber_mail() {
        return number_mail;
    }

    public void setNumber_mail(String number_mail) {
        this.number_mail = number_mail;
    }
}
