package com.example.tajhotel.CustomClasses;
public class usr_data {
    String Fname, Lname, Email, Password,Country_code,  Phnum,  Gander;

    public usr_data(String Fname, String Lname, String Email, String Password, String Country_code, String Phnum, String Gander) {
        this.Fname = Fname;
        this.Lname = Lname;
        this.Email = Email;
        this.Password = Password;
        this.Country_code = Country_code;
        this.Phnum = Phnum;
        this.Gander = Gander;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCountry_code() {
        return Country_code;
    }

    public void setCountry_code(String country_code) {
        Country_code = country_code;
    }

    public String getPhnum() {
        return Phnum;
    }

    public void setPhnum(String phnum) {
        Phnum = phnum;
    }

    public String getGander() {
        return Gander;
    }

    public void setGander(String gander) {
        Gander = gander;
    }
}
