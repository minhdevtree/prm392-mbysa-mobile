package vn.edu.fpt.project.util;

public class SortUtils {

    // Method to get the sort option based on the selected position
    public static String getSortOptionFromPosition(int position) {
        switch (position) {
            case 1:
                return "oldest";
            case 2:
                return "price-asc";
            case 3:
                return "price-desc";
            default:
                return "latest";
        }
    }
}
