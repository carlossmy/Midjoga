package com.example.carlossmy.midjogaapp.entities;


public class Comment {
    int id;
    String content;
   CustomDate date;
    User user;
    Cagnotte cagnotte;

    public Comment(int id, String content,
                   CustomDate date, User user) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CustomDate getDate() {
        return date;
    }

    public void setDate(CustomDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cagnotte getCagnotte() {
        return cagnotte;
    }

    public void setCagnotte(Cagnotte cagnotte) {
        this.cagnotte = cagnotte;
    }
}
