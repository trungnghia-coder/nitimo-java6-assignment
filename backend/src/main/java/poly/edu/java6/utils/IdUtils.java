package poly.edu.java6.utils;

import java.util.Random;
import java.util.UUID;

public class IdUtils {
    public static String generateNewId() {
        return UUID.randomUUID().toString();
    }

    public static String generateCustomRandomId(String prefix, int numericLength) {
        Random random = new Random();
        int maxBound = (int) Math.pow(10, numericLength);
        int randomNumber = random.nextInt(maxBound);
        String formatString = "%0" + numericLength + "d";
        String randomPart = String.format(formatString, randomNumber);
        return prefix + randomPart;
    }
}
