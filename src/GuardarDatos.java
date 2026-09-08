import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class GuardarDatos {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static void guardar(gestionbuses gestion, String ruta) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta))) {
            escritor.write("TIPO;ID;DATO1;DATO2;DATO3;DATO4;DATO5;DATO6");
            escritor.newLine();

            for (Buses bus : gestion.getListaBuses()) {
                escritor.write("BUS;" + bus.getIdBus() + ";" + bus.getCapacity());
                escritor.newLine();
            }

            for (Viajes viaje : gestion.getListaViajes()) {
                escritor.write("VIAJE;" + viaje.getIdViaje() + ";"
                        + limpiar(viaje.getOrigen()) + ";" + limpiar(viaje.getDestino()) + ";"
                        + viaje.getCostoViaje() + ";" + viaje.getCostoPasaje() + ";"
                        + viaje.getFechaHoraInicio().format(FORMATO) + ";" + viaje.getDuracionMinutos());
                escritor.newLine();
            }

            for (Viajes viaje : gestion.getListaViajes()) {
                for (Buses bus : viaje.getBuses().values()) {
                    for (Pasajeros pasajero : bus.getPasajeros()) {
                        escritor.write("PASAJERO;" + pasajero.getIdPasajero() + ";"
                                + limpiar(pasajero.getNombre()) + ";" + pasajero.getEdad() + ";"
                                + viaje.getIdViaje() + ";" + bus.getIdBus());
                        escritor.newLine();
                    }
                }
            }
        }
    }

    private static String limpiar(String texto) {
        return texto == null ? "" : texto.replace(";", " ");
    }
}