package com.example.carlossmy.midjogaapp.utils;

import com.example.carlossmy.midjogaapp.entities.CustomDate;

import org.joda.time.DateTime;
import java.util.Calendar;

import static org.joda.time.Days.daysBetween;

public class StaticFunctions {

  private static final String TIME_REMAINING= " Jour restant";
  private static final String TIMES_REMAINING= " Jours restants";
  private static final String DONNOR= " Contributions";
  private static final String DONNORS= " Contribution";
  private static final String AMOUNT= " FCFA Récoltés sur ";
  public static final String GOOGLE_PASSWORD= "google_password";
  public static final String IP_ADDRESS= "192.168.43.188";
  public static final String IMG_URL= "http://"+IP_ADDRESS+":8080/api/img/";
  public static final String FAILURE = "Problème de connexion, cliquez pour réésayer";
  public static final String AUTH_TOKEN="5145f410-14c7-4207-b1ca-ddff869cde8f";
  public static CustomDate toCustomDate(String date){
    if (date==null)
      return null ;
    String sep1 ="-";
    String sep2="T";
    String dates[]=date.split(sep1);
    String jours[]=dates[2].split(sep2);
    return new CustomDate(Integer.valueOf(jours[0]),Integer.valueOf(dates[1]),Integer.valueOf(dates[0]));


  }
  public static long daysBetweenDates( String date) {
    CustomDate endDate = toCustomDate(date);
    Calendar c =  Calendar.getInstance();
    DateTime start = new DateTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    DateTime end = new DateTime(endDate.getYear(), endDate.getMonth(), endDate.getDay(), 0, 0, 0);

    return daysBetween(start.withTimeAtStartOfDay(), end.withTimeAtStartOfDay()).getDays();

  }
  public static String timeRemaining(String dueDate){
    if (daysBetweenDates(dueDate)==1)
      return daysBetweenDates(dueDate)+TIME_REMAINING;


    return daysBetweenDates(dueDate)+TIMES_REMAINING;
  }
  public static String Amount(double amount,double total){
    return (int)amount+AMOUNT+(int)total;
  }
  public static String Donnors(int nbDonnors){
    if (nbDonnors==1)
      return nbDonnors+DONNOR;
    else
      return nbDonnors+DONNOR;
  }
  public static String DESCRIPTION(int id){
    return " PAIEMENT DE LA CAGNOTTE NUMERO "+id;
  }
  public static int barPercentage(double amount,double totalAttendu){return (int)(100*(amount/totalAttendu));}
}
