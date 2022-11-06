package model;


import java.time.LocalDate;
import java.util.ArrayList;

public class Calendar {
    private ArrayList<LocalDate> occupiedDates;
    private ArrayList<LocalDate> freeDates;

    public Calendar() {
        this.occupiedDates = new ArrayList<>();
        this.freeDates = new ArrayList<>();
    }

    public ArrayList<LocalDate> getOccupiedDates() {
        return occupiedDates;
    }

    public void setOccupiedDates(ArrayList<LocalDate> occupiedDates) {
        this.occupiedDates = occupiedDates;
    }

    public ArrayList<LocalDate> getFreeDates() {
        return freeDates;
    }

    public void setFreeDates(ArrayList<LocalDate> freeDates) {
        this.freeDates = freeDates;
    }
}
