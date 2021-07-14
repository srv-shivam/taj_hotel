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

    public void setFname(String Fname) {
        this.Fname = Fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String Lname) {
        this.Lname = Lname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getCountry_code() {
        return Country_code;
    }

    public void setCountry_code(String Country_code) {
        this.Country_code = Country_code;
    }

    public String getPhnum() {
        return Phnum;
    }

    public void setPhnum(String Phnum) {
        this.Phnum = Phnum;
    }

    public String getGander() {
        return Gander;
    }

    public void setGander(String Gander) {
        this.Gander = Gander;
    }
}
