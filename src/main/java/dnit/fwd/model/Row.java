package dnit.fwd.model;

public class Row {

    private int id_state;
    private int BR;
    private float km;
    private String snv;
    private Deflections deflections;
    private float raio;
    private float forca;
    private float pressao;
    private Temperature temp;
    private GPS gps;
    private Date data;
    private Hora hora;
    private String obs;
    private String periodo;

    public Row() {}

    public int getId_state() {
        return id_state;
    }

    public void setId_state(int id_state) {
        this.id_state = id_state;
    }

    public int getBR() {
        return BR;
    }

    public void setBR(int BR) {
        this.BR = BR;
    }

    public float getKM() {
        return km;
    }

    public void setKM(float km) {
        this.km = km;
    }

    public String getSNV() {
        return snv;
    }

    public void setSNV(String snv) {
        this.snv = snv;
    }

    public Deflections getDeflections() {
        return deflections;
    }

    public void setDeflections(Deflections deflections) {
        this.deflections = deflections;
    }

    public float getRaio() {
        return raio;
    }

    public void setRaio(float raio) {
        this.raio = raio;
    }

    public float getForca() {
        return forca;
    }

    public void setForca(float forca) {
        this.forca = forca;
    }

    public float getPressao() {
        return pressao;
    }

    public void setPressao(float pressao) {
        this.pressao = pressao;
    }

    public Temperature getTemp() {
        return temp;
    }

    public void setTemp(Temperature temp) {
        this.temp = temp;
    }

    public GPS getGps() {
        return gps;
    }

    public void setGps(GPS gps) {
        this.gps = gps;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Hora getHora() {
        return hora;
    }

    public void setHora(Hora hora) {
        this.hora = hora;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
}
