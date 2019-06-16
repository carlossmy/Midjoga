package com.example.carlossmy.midjogaapp.entities;

public class CustomDate {
    int day,month,year;

    public CustomDate( int day, int month,int year){
        this.day=day;
        this.month=month;
        this.year=year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String toStringSQL() {
        if(month<10){
            if(day<10)
                return year+"-0"+month+"-0"+day;
            else
                return year+"-0"+month+"-"+day;
        }
        else
        if (day<10)
            return year+month+"-0"+day;
        else
            return year+"-"+month+"-"+day;
    }
    public String toString() {
        if(month<10){
            if(day<10)
                return"0"+ day+"-0"+month+"-"+year;
            else
                return day+"-0"+month+"-"+year;

        }
        else{
            if (day<10)
                return"0"+ day+"-"+month+"-"+year;
            else
                return day+"-"+month+"-"+year;

        }

    }
}
