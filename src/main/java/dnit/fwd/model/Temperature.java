package dnit.fwd.model;

public class Temperature {

    private final float temp_ar;
    private final float temp_pav;

    
    public Temperature(String temp_ar, String temp_pav) throws Exception {
        this.temp_ar  = Numeric.getFloat(temp_ar);
        this.temp_pav = Numeric.getFloat(temp_pav);
    }


    public float getAr() {
        return temp_ar;
    }


    public float getPav() {
        return temp_pav;
    }
    
    
}
