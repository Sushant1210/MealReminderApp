package com.example.mealreminder;
/*
 * Created by Sushant Kale 22-05-2021.
 */
public class Utilities {

    // Method to return integer value as per the weekday
    public static int getWeekDaysInNo(String weekday) {
        if (weekday.equals("Sunday"))
            return 0;
        if (weekday.equals("Monday"))
            return 1;
        if (weekday.equals("Tuesday"))
            return 2;
        if (weekday.equals("Wednesday"))
            return 3;
        if (weekday.equals("Thursday"))
            return 4;
        if (weekday.equals("Friday"))
            return 5;
        if (weekday.equals("Saturday"))
            return 6;
        else
            return 0;
    }
}
