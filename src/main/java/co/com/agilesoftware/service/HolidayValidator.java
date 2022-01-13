package co.com.agilesoftware.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.Month.*;

public class HolidayValidator {

    private HolidayValidator() {}

    public static boolean isHoliday(LocalDate date) {
        List<LocalDate> holidayList = getHoliDays(date.getYear());
        return holidayList.contains(date);
    }

    public static boolean isBusinessDay(LocalDate date) {
        return date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY && !isHoliday(date);
    }

    public static List<LocalDate> getHoliDays(int year) {
        List<LocalDate> dates = new ArrayList<>();

        //Festivos fijos
        addHoliday(dates, LocalDate.of(year, JANUARY, 1)); //Anio nuevo
        addHoliday(dates, LocalDate.of(year, MAY, 1)); //Día del Trabajo
        addHoliday(dates, LocalDate.of(year, JULY, 20)); //Grito de la Independencia
        addHoliday(dates, LocalDate.of(year, AUGUST, 7)); //Batalla de Boyacá
        addHoliday(dates, LocalDate.of(year, DECEMBER, 8)); //Inmaculada Concepción
        addHoliday(dates, LocalDate.of(year, DECEMBER, 25)); //Navidad

        //Primer lunes
        addHoliday(dates, nextMonday(LocalDate.of(year, JANUARY, 6))); //Reyes magos
        addHoliday(dates, nextMonday(LocalDate.of(year, MARCH, 19))); //San José
        addHoliday(dates, nextMonday(LocalDate.of(year, JUNE, 29))); //San Pedro y San Pablo
        addHoliday(dates, nextMonday(LocalDate.of(year, AUGUST, 15))); //Asunción de la Virgen
        addHoliday(dates, nextMonday(LocalDate.of(year, NOVEMBER, 1))); //Todos los Santos
        addHoliday(dates, nextMonday(LocalDate.of(year, OCTOBER, 12))); //Día de la Raza
        addHoliday(dates, nextMonday(LocalDate.of(year, NOVEMBER, 11))); //Independencia de Cartagena

        //Dependen de la pascua
        LocalDate easter = getEasterDay(year);
        addHoliday(dates, easter.minusDays(3)); //Jueves Santo
        addHoliday(dates, easter.minusDays(2)); //Viernes Santo
        addHoliday(dates, easter.plusDays(43)); //Ascensión de Jesús
        addHoliday(dates, easter.plusDays(64)); //Corpus Christi
        addHoliday(dates, easter.plusDays(71)); //Sagrado Corazón

        return dates.stream().sorted().collect(Collectors.toList());
    }

    private static LocalDate nextMonday(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();

        if (DayOfWeek.MONDAY.ordinal() != day.ordinal()) {
            return date.plusDays(7L - day.ordinal());
        }

        return date;
    }


    private static void addHoliday(List<LocalDate> holidayList, LocalDate date) {
        if (holidayList.contains(date) == false)
            holidayList.add(date);
    }


    public static LocalDate getEasterDay(int year) {
        int a;
        int b;
        int c;
        int d;
        int e;
        int m = 24;
        int n = 5;
        int day;


        if (year >= 1583 && year <= 1699) {
            m = 22;
            n = 2;
        } else if (year >= 1700 && year <= 1799) {
            m = 23;
            n = 3;
        } else if (year >= 1800 && year <= 1899) {
            m = 23;
            n = 4;
        } else if (year >= 1900 && year <= 2099) {
            m = 24;
            n = 5;
        } else if (year >= 2100 && year <= 2199) {
            m = 24;
            n = 6;
        } else if (year >= 2200 && year <= 2299) {
            m = 25;
            n = 0;
        }

        a = year % 19;
        b = year % 4;
        c = year % 7;
        d = ((a * 19) + m) % 30;
        e = ((2 * b) + (4 * c) + (6 * d) + n) % 7;

        // Decidir entre los 2 casos:
        if (d + e < 10) {
            return LocalDate.of(year, MARCH, d + e + 22);
        } else {
            if (d + e == 26) {
                day = 17;
            } else if (d + e == 34 && d == 28 && e == 6 && a > 10) {
                day = 18;
            } else if (d + e == 25 && d == 28 && e == 6 && a > 10) {
                day = 18;
            } else {
                day = d + e - 9;
            }

            return LocalDate.of(year, APRIL, day);
        }
    }


}
