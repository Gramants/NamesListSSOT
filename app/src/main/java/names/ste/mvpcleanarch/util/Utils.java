package names.ste.mvpcleanarch.util;

import java.util.Comparator;

import names.ste.mvpcleanarch.model.entities.ItemDbEntity;


public class Utils {

    public final static int WIFI = 1;
    public final static int NETWORK = 2;
    public final static int NO_CONNECTION = 0;

    public static class CustomComparator implements Comparator<Object> {

        @Override
        public int compare(Object o1, Object o2) {
            if ((o1 instanceof ItemDbEntity) && (o2 instanceof ItemDbEntity)) {
                return ((ItemDbEntity) o1).getName().compareTo(((ItemDbEntity) o2).getName());
            }
            return 0;
        }
    }

}