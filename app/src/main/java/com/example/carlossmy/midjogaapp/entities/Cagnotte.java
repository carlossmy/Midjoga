package com.example.carlossmy.midjogaapp.entities;
import java.sql.Date;

public class Cagnotte {
  private int id;
  private String title,desc,localisation,due_date;
  private  double goal,amount;
  private  int userId,typeCagnotte,nbDonnors;
  private  boolean visibility;
  private int visible;
  private  String imgUrl;

  public int getId() {
    return id;
  }

  public Cagnotte(int id, String title, String desc, double amount, double goal, String due_date, String localisation, String imgUrl, int typeCagnotte, int nbDonnors) {
    this.id=id;
    this.title = title;
    this.desc = desc;
    this.amount = amount;
    this.goal=goal;
    this.due_date = due_date;
    this.typeCagnotte = typeCagnotte;
    this.nbDonnors= nbDonnors;
    this.localisation=localisation;
    this.imgUrl=imgUrl;

  }


  public Cagnotte() {

  }
  public String getLocalisation() {
    return localisation;
  }

  public void setLocalisation(String localisation) {
    this.localisation = localisation;
  }

  public void setGoal(double goal) {
    this.goal = goal;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }



  public int getNbDonnors() {
    return nbDonnors;
  }

  public void setNbDonnors(int nbDonnors) {
    this.nbDonnors = nbDonnors;
  }

  public String getTitle() {

    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public double getGoal() {
    return goal;
  }



  public double getAmount() {
    return amount;
  }

  public String getDue_date() {
    return due_date;
  }

  public void setDue_date(String due_date) {
    this.due_date = due_date;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getTypeCagnotte() {
    return typeCagnotte;
  }

  public void setTypeCagnotte(int typeCagnotte) {
    this.typeCagnotte = typeCagnotte;
  }

  public boolean isVisibility() {
    return visibility;
  }

  public void setVisibility(boolean visibility) {
    this.visibility = visibility;
  }
  public boolean getVisibility(){ return visibility;};

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
}
