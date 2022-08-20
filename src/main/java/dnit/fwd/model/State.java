package dnit.fwd.model;

import java.util.HashMap;
import java.util.Map;

public enum State {
    
    AC(1),  AL(2),  AP(3),  AM(4),  BA(5),  CE(6),  DF(7),  ES(8),  GO(9),  MA(10), 
    MT(11), MS(12), MG(13), PA(14), PB(15), PR(16), PE(17), PI(18), RJ(19), 
    RN(20), RS(21), RO(22), RR(23), SC(24), SP(25), SE(26), TO(27);

    public final int id;
    private final static Map<Integer, String> UFs = new HashMap<>(27);
    
    static {
        for (State s : State.values()) 
            UFs.put(s.id, s.name());
    }


    private State (int id) {
        this.id = id;
    }


    public static String getUF(int id) {
        return UFs.get(id);
    }


    public static int validateUFofID(String UF) throws Exception {
        for (State s : State.values()) {
            if (s.name().toLowerCase().equals(UF.toLowerCase()))
                return s.id;
        }
        throw new Exception("Not a valid UF: " + UF);
    }

    
}