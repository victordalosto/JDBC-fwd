package dnit.fwd.model;

public class SNV {

    private SNV() {}

    public static String validateSNV(String snv) throws Exception {
        if (snv.length() != 10)
            throw new Exception("Invalid SNV name");
        Integer.valueOf(snv.substring(0, 3));
        Integer.valueOf(snv.substring(6));
        State.validateUFofID(snv.substring(3, 5));
        return snv;
    }
    
}
