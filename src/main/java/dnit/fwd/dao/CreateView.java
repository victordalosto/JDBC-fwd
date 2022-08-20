package dnit.fwd.dao;

import dnit.fwd.jdbc.JDBC;

public class CreateView {
    
    private CreateView() {}


    /* Creates the state table in the database*/
    public static void fwd(JDBC jdbc) throws Exception {
        try {
            String sql = "CREATE view fwd AS " +
                         "SELECT state.uf, snv.br, snv.snv, result.km, d.d0, d.d20, d.d30, d.d45, " + 
                         "d.d65, d.d90, d.d120, f.forca, f.pression, f.raio, " +
                         "gps.latitude, gps.longitude, gps.altitude, " +
                         "t.ar, t.pav, data.date, data.time, o.obs " +
                         "FROM SNV " +
                         "JOIN state ON state.id_state = snv.id_state " +
                         "JOIN result ON result.id_snv = snv.id_snv " +
                         "JOIN deflection d ON d.id_result = result.id_result " +
                         "JOIN forca f ON f.id_result = result.id_result " +
                         "JOIN gps on gps.id_result = result.id_result " +
                         "JOIN temperature t on t.id_result = result.id_result " +
                         "JOIN data on data.id_result = result.id_result " + 
                         "LEFT JOIN result_observation rb ON rb.id_result = result.id_result " +
                         "LEFT JOIN observation o ON o.id_obs = rb.id_obs ";
            jdbc.runCommand(sql);
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        }
    }

}
