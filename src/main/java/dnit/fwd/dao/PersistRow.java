package dnit.fwd.dao;

import dnit.fwd.jdbc.JDBC;
import dnit.fwd.model.Date;
import dnit.fwd.model.Deflections;
import dnit.fwd.model.GPS;
import dnit.fwd.model.State;
import dnit.fwd.model.Temperature;
import dnit.fwd.model.Hora;

public class PersistRow {

    private JDBC jdbc;
    
    public PersistRow(JDBC jdbc) {
        this.jdbc = jdbc;
    }



    public void state(int idUF) throws Exception {
        jdbc.useTable("state");
        if (null == jdbc.queryValue("id_state", "id_state = " + idUF))
            jdbc.insert(idUF + ", '" + State.getUF(idUF) + "'");
    }



    public void snv(int idUF, int br, String snv, String periodo) throws Exception {
        jdbc.useTable("snv");
        if (null == jdbc.queryValue("snv", "snv = '" + snv + "'"))
            jdbc.insertUsingParameters("id_state, br, snv, periodo", 
                                       idUF + ", " + br + ", '" + snv + "'', '" + periodo + "'");
    }



    public int result(String snv, float km) throws Exception {
        jdbc.useTable("snv");
        String id_snv = jdbc.queryValue("id_snv", "snv = '" + snv + "'");
        jdbc.useTable("result");
        int id_result = jdbc.insertUsingParameters("id_snv, km", id_snv + ", " + km);
        return id_result;
    }



    public void temperature(int id_result, Temperature temp) throws Exception {
        jdbc.useTable("temperature");
        jdbc.insert(id_result + ", " + temp.getAr() + ", " + temp.getPav());
    }



    public void gps(int id_result, GPS gps) throws Exception {
        jdbc.useTable("gps");
        jdbc.insert(id_result + ", " + gps.getLatitude() + ", " + gps.getLongitude() + ", " + gps.getAltitude());
    }



    public void forca(int id_result, Float forca, Float pression, Float raio) throws Exception {
        jdbc.useTable("forca");
        jdbc.insert(id_result + ", " + forca + ", " + pression + ", " + raio);
    }



    public void data(int id_result, Date data, Hora hora) throws Exception {
        jdbc.useTable("data");
        jdbc.insert(id_result + ", '" + data.getData() + "'', '" + hora.getHora() + "'");
    }



    public void deflections(int id_result, Deflections deflections) throws Exception {
        jdbc.useTable("deflection");
        jdbc.insert(id_result + ", " + deflections.toString());

    }



    public void observation(int id_result, String obs) throws Exception {
        if (obs != null && !obs.isEmpty()) {
            jdbc.useTable("observation");
            jdbc.insertUsingParameters("obs", "'" + obs + "'");
            String id_obs = jdbc.queryValue("id_obs", "obs = '" + obs + "'");
            jdbc.useTable("result_observation");
            jdbc.insertUsingParameters("id_obs, id_result", id_obs + ", " + id_result);
        }
    }
    
}
