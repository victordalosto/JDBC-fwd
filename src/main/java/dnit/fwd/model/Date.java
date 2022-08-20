package dnit.fwd.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private final LocalDate data;


    public Date(String date) throws Exception {
        this.data = LocalDate.parse(date, formatter);
        if (data.getYear() > 2022 || data.getYear() < 2020)
            throw new Exception("Invalid value for year");
    }


    
    public LocalDate getData() {
        return this.data;
    }
    
}
