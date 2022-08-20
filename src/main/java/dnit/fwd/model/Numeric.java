package dnit.fwd.model;

public class Numeric {

    private Numeric() {}


    public static float getFloat(String num) {
        return Float.parseFloat(fixDecimal(num));
    }


    public static Double getDouble(String num) {
        return Double.parseDouble(fixDecimal(num));
    }


    public static Integer getInteger(String num) {
        return Integer.parseInt(fixDecimal(num));
    }


    private static String fixDecimal(String num) {
        return num.replaceAll(",", ".").replaceAll("\\s+", "");
    }
    
}
