package com.capgemini.calendar;

import com.sun.deploy.util.StringUtils;

import java.util.Scanner;

public class Main_Calendar {

    public static void main(String[] args) {
        //Data collection from io stream
        Scanner sc = new Scanner(System.in);
        //Boolean to create a looping cycle
        boolean isRunning = true;
        while(isRunning)
        {
            //print question
            System.out.println("enter a year to get the calendar for that year, enter exit to close the application: ");
            //get answer loop internally when failed
            failedReturn:
            for(;;) {
                String data = sc.next();
                //expected result is either exit or a number so remove all spaces.
                data = StringUtils.trimWhitespace(data);

                //try to parse to a number, if it fails check if it's equal to exit else return.
                try {
                    int year = Integer.parseInt(data);
                    System.out.println(CalendarFormatting.CalendarOfYear(year, 2));
                    break failedReturn;
                } catch (NumberFormatException nfe) {
                    //check for exit else return
                    if (data.equals("exit")) {
                        isRunning = false;
                        break failedReturn;
                    }
                    else {
                        System.out.println("Please enter a number or type exit: ");
                        continue failedReturn;
                    }
                }
            }
        }
    }
}
