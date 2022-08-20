package dnit.fwd.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Hora {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
    private final LocalTime hora;


    public Hora(String hora) {
        this.hora = LocalTime.parse(hora, formatter);
    }

    
    public LocalTime getHora() {
        return this.hora;
    }
    
}
