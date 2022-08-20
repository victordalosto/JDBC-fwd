package dnit.fwd.model;

public class GPS {

    private double latitude;
    private double longitude;
    private double altitude;

    public GPS(String latitude, String longitude, String altitude) throws Exception {
        this(Numeric.getDouble(latitude), Numeric.getDouble(longitude), Numeric.getDouble(altitude));
    }

    public GPS(double latitude, double longitude, double altitude) throws Exception {
        this.latitude  = latitude; 
        this.longitude = longitude;
        this.altitude  = altitude;  
        validateGPS();
    }

    private void validateGPS() throws Exception {
        if (latitude < -35 || latitude > 5.3) 
            throw new Exception("Wrong value for latitude");
        if (longitude < -74 || longitude > -34)
            throw new Exception("Wrong value for longitude");
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getAltitude() {
        return this.altitude;
    }
    
}
