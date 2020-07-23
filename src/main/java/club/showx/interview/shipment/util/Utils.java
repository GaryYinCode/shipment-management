package club.showx.interview.shipment.util;

import java.util.Collection;

public class Utils {

    public static int sum(Collection<Integer> amounts) {
        int returnValue = 0;

        for (int amount : amounts) {
            returnValue += amount;
        }

        return returnValue;
    }
}
