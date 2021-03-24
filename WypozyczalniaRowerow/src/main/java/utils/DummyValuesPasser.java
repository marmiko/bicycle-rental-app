package utils;

/**
 * class used for transferring values between classes which cannot communicate in other way
 */
public class DummyValuesPasser {

    private static String stringValue;
    private static long longValue = -1;

    public static String getStringValue() {
        return stringValue;
    }

    public static void setStringValue(String stringValue) {
        DummyValuesPasser.stringValue = stringValue;
    }

    public static long getLongValue() {
        return longValue;
    }

    public static void setLongValue(long longValue) {
        DummyValuesPasser.longValue = longValue;
    }
}
