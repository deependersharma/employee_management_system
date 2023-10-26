package com.example.app_main;

public class Employees_Model {
    private String name;
    private String role;
    private String joining_date;
    private String hourly_rate;
    private String id;

    private byte[] image;
    public Employees_Model(String id,
                       String name,byte[] image,
                       String role,
                       String hourly_rate, String joining_date)
    {
        this.name = name;
        this.id = id;
        this.image=image;
        this.hourly_rate = hourly_rate;
        this.role = role;
    }

    // creating getter and setter methods
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


    // constructor

}
