package Controller;

import model.Organiser;

import java.util.Comparator;

public class NameComparatorOrganiser implements Comparator<Organiser> {

    @Override
    public int compare(Organiser organiser1, Organiser organiser2){
        return organiser1.getUsername().compareTo(organiser2.getUsername());
    }
}
