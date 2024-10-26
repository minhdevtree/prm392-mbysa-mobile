package vn.edu.fpt.project.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    public static String formatDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(dateStr);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return dateStr;
        }
    }
}
