package dnit.fwd.push;

import java.io.File;
import java.util.Scanner;

import dnit.fwd.dao.PersistRow;
import dnit.fwd.jdbc.JDBC;
import dnit.fwd.model.Date;
import dnit.fwd.model.Deflections;
import dnit.fwd.model.GPS;
import dnit.fwd.model.Numeric;
import dnit.fwd.model.Row;
import dnit.fwd.model.SNV;
import dnit.fwd.model.State;
import dnit.fwd.model.Temperature;
import dnit.fwd.model.Hora;

public class ReadFromCSV {

    private static Scanner scanner;
    private static PersistRow persistRow;
    private static Row currentRow = new Row();


    private ReadFromCSV() {}


    /* Read a csv file and persists its info into the database */
    public static void readAndPersist(String path, JDBC jdbc) throws Exception {
        path = fixPath(path);
        persistRow = new PersistRow(jdbc);
        scanner = new Scanner(new File(path), "UTF-8");
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            try {
                String line = scanner.nextLine();
                validateRow(line.split(";"), jdbc);
                persistRow(line.split(";"), jdbc);
                jdbc.commit();
            } catch (Exception e) {
                jdbc.rollback();
                e.printStackTrace();
            }
        }
    }


    /* Fixes the initial path to contains src\\asserts\\fileName.csv */
    private static String fixPath(String path) {
        if (!path.startsWith("assets"))
            path = "assets\\" + path;
        if (!path.endsWith(".csv")) 
            path = path + ".csv";
        return path;
    }



    /* Persists the row in the database */
    private static void validateRow(String row[], JDBC jdbc) throws Exception {
        if (row.length != 23)
            throw new Exception("Wrong format for row");
        currentRow.setId_state(State.validateUFofID(row[0]));
        currentRow.setBR(Numeric.getInteger(row[1]));
        currentRow.setKM(Numeric.getFloat(row[2]));
        currentRow.setSNV(SNV.validateSNV(row[3]));
        currentRow.setDeflections(new Deflections(row[4], row[5], row[6], row[7], row[8], row[9], row[10]));
        currentRow.setRaio(Numeric.getFloat(row[11]));
        currentRow.setForca(Numeric.getFloat(row[12]));
        currentRow.setPressao(Numeric.getFloat(row[13]));
        currentRow.setTemp(new Temperature(row[14], row[15]));
        currentRow.setGps(new GPS(row[16], row[17], row[18]));
        currentRow.setData(new Date(row[19]));
        currentRow.setHora(new Hora(row[20]));
        currentRow.setObs(row[21]);
        currentRow.setPeriodo(row[22]);

    }



    private static void persistRow(String[] split, JDBC jdbc) throws Exception {
        persistRow.state(currentRow.getId_state());
        persistRow.snv(currentRow.getId_state(), currentRow.getBR(), 
                        currentRow.getSNV(), currentRow.getPeriodo());
        int id_result = persistRow.result(currentRow.getSNV(), currentRow.getKM());
        persistRow.temperature(id_result, currentRow.getTemp());
        persistRow.gps(id_result, currentRow.getGps());
        persistRow.forca(id_result, currentRow.getForca(), currentRow.getPressao(), currentRow.getRaio());
        persistRow.data(id_result, currentRow.getData(), currentRow.getHora());
        persistRow.deflections(id_result, currentRow.getDeflections());
        persistRow.observation(id_result, currentRow.getObs());
    }
}
