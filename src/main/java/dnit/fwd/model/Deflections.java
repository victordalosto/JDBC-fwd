package dnit.fwd.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deflections {

    private final List<Float> deflections = new ArrayList<>(8);


    public Deflections(String ... d) throws Exception {
        if (d.length != 7)
            throw new Exception("Wrong number of parameters for deflection");
        for (int i=0; i<d.length; i++)
            deflections.add(Numeric.getFloat(d[i]));
        validateDeflections();
    }



    public List<Float> getDeflections() {
        return Collections.unmodifiableList(deflections);
    }



    private void validateDeflections() throws Exception {
        for (int i=0; i<deflections.size(); i++) {
            if (deflections.get(i) < 0)
                throw new Exception("Negative deflection value");
            if (i < deflections.size()-2) {
                if (deflections.get(i) < deflections.get(i+1))
                    throw new Exception("Wrong value for the deflection");                    
            } 
        }
    }



    @Override
    public String toString() {
        return deflections.get(0) + ", " + deflections.get(1) + ", " + deflections.get(2) + ", " + 
               deflections.get(3) + ", " + deflections.get(4) + ", " + deflections.get(5) + ", " + 
               deflections.get(6);
    }


}
