package com.example.birthdaybuddy;

public class DataClass {
    String name;
    String birthday;
    String imagepath;

    public DataClass(String name,String birthday,String imagepath)
    {
        this.name=name;
        this.birthday=birthday;
        this.imagepath=imagepath;
    }

    public String getName()
    {
        return name;
    }

    public String  getBirthday()
    {
        return birthday;
    }

    public String getImagepath()
    {
        return imagepath;
    }
}
