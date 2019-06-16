package com.example.carlossmy.midjogaapp.entities;

public class Paiement {
    int id,status;
    String libelle;
    double montant;
    CustomDate date;
    Cagnotte cagnotte;
    User user;

    public Paiement(int id, double montant, User user) {

        this.id = id;
        this.montant = montant;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public CustomDate getDate() {
        return date;
    }

    public void setDate(CustomDate date) {
        this.date = date;
    }

    public Cagnotte getCagnotte() {
        return cagnotte;
    }

    public void setCagnotte(Cagnotte cagnotte) {
        this.cagnotte = cagnotte;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
