package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class PlayerInfo {
    @SerializedName("id")
    private int id;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("firstname")
    private String firstName;
    
    @SerializedName("lastname")
    private String lastName;
    
    @SerializedName("age")
    private int age;
    
    @SerializedName("birth")
    private Birth birth;
    
    @SerializedName("nationality")
    private String nationality;
    
    @SerializedName("height")
    private String height;
    
    @SerializedName("weight")
    private String weight;
    
    @SerializedName("injured")
    private boolean injured;
    
    @SerializedName("photo")
    private String photo;

    public PlayerInfo() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public Birth getBirth() { return birth; }
    public void setBirth(Birth birth) { this.birth = birth; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getHeight() { return height; }
    public void setHeight(String height) { this.height = height; }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public boolean isInjured() { return injured; }
    public void setInjured(boolean injured) { this.injured = injured; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
}

