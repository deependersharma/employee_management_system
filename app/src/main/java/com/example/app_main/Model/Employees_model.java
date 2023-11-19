package com.example.app_main.Model;

public class Employees_model {
    private String name;
    private String role;
    private String joining_date;
    private String hourly_rate;
    private String id;

    private byte[] image;
    public Employees_model(
                       String name,String id,byte[] image,String joining_date,
                       String role,
                       String hourly_rate)
    {
        this.id = id;
        this.name = name;
        this.image=image;
        this.hourly_rate = hourly_rate;
        this.role = role;
        this.joining_date=joining_date;
    }

    // creating getter and setter methods


    public byte[] getImage() {
        return image;
    }

    public String getEmployeeName() { return name; }

    public void setEmployeeName(String name)
    {
        this.name = name;
    }

    public String getEmployeeId()
    {
        return id;
    }

    public void setEmployeeId(String courseDuration)
    {
        this.id = id;
    }

    public String getEmployeeRole() { return role; }

    public void setEmployeeRole(String role)
    {
        this.role = role;
    }

    public String getHourly_rate()
    {
        return hourly_rate;
    }

    public void
    setHourly_rate(String hourly_rate)
    {
        this.hourly_rate =hourly_rate;
    }

    public String getJoining_date(){ return joining_date; }


    // constructor

}
