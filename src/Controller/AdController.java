package Controller;

import model.Ad;
import model.Product;

import java.util.ArrayList;
import model.Calendar;

public class AdController {

    public static void createAd(Integer id, Product product, ArrayList<String> images, Calendar calendar) {
        Ad newAd = new Ad(id, product, calendar);

    }
}
