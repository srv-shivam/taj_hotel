package com.example.tajhotel.CustomClasses;
public class usr_data {
    String fname, lname, email, password,country_code,  ph_num,  gander;

    public usr_data(String fname, String lname, String email, String password, String country_code, String ph_num, String gander) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.country_code = country_code;
        this.ph_num = ph_num;
        this.gander = gander;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getPh_num() {
        return ph_num;
    }

    public void setPh_num(String ph_num) {
        this.ph_num = ph_num;
    }

    public String getGander() {
        return gander;
    }

    public void setGander(String gander) {
        this.gander = gander;
    }
}
