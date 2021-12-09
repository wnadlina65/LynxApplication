package com.example.lynxapplication;

public class Resume {
    public String name;
    public String phone;
    public String email;
    public String address;
    public String position;
    public String[] social;
    public String summary;
    public String[] skill;
    public String[] experience;
    public String[] education;
    public String interest;

    public Resume(){}

    public Resume(String name, String phone, String email, String address, String position, String[] social,
                  String summary, String[] skill, String[] experience, String[] education, String interest)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.position = position;
        this.social = new String[2];
        this.social[0] = social[0];
        this.social[1] = social[1];
        this.summary = summary;
        this.skill = new String[4];
        this.skill[0] = skill[0];
        this.skill[1] = skill[1];
        this.skill[2] = skill[2];
        this.skill[3] = skill[3];
        this.experience = new String[4];
        this.experience[0] = experience[0];
        this.experience[1] = experience[1];
        this.experience[2] = experience[2];
        this.experience[3] = experience[3];
        this.education = new String[4];
        this.education[0] = education[0];
        this.education[1] = education[1];
        this.education[2] = education[2];
        this.education[3] = education[3];
        this.interest = interest;
    }

}




