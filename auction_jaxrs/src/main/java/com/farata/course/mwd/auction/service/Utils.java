package com.farata.course.mwd.auction.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by ds on 31/01/15.
 */
public class Utils {

    public  static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y");
        LocalDate maxCloseDate = LocalDate.parse("02/06/2015", formatter);
        System.out.println(maxCloseDate);
    }
}
