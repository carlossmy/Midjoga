package com.example.carlossmy.midjogaapp.entities;

public class Category {

  int id;
  String libelle,imgUrl;

  public Category(int id, String libelle, String imgUrl) {
    this.id = id;
    this.libelle = libelle;
    this.imgUrl = imgUrl;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLibelle() {
    return libelle;
  }

  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
}
