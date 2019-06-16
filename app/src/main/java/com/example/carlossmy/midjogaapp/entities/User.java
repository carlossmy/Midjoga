package com.example.carlossmy.midjogaapp.entities;

import java.sql.Date;

public class User {
    int id;
    String nomComplet,email;
    Date naissance;

    public User(int id, String nomComplet, String email, Date naissance) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.email = email;
        this.naissance = naissance;
    }

    public User(int id, String nomComplet) {
        this.id = id;
        this.nomComplet = nomComplet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNaissance() {
        return naissance;
    }

    public void setNaissance(Date naissance) {
        this.naissance = naissance;
    }
}
