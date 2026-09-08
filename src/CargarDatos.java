/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bruno
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class CargarDatos {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static void cargar(gestionbuses gestion, String ruta) throws IOException {
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            gestion.limpiarDatos();
            String linea;
            HashMap<Integer, Viajes> viajes = new HashMap<>();
            HashMap<String, Buses> busesViaje = new HashMap<>();

            lector.readLine();
            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] c = linea.split(";", -1);
                switch (c[0]) {
                    case "BUS":
                        gestion.agregarBusInicial(Integer.parseInt(c[1]), Integer.parseInt(c[2]));
                        break;
                    case "VIAJE":
                        Viajes viaje = new Viajes(Integer.parseInt(c[1]), c[2], c[3],
                                Double.parseDouble(c[4]), Double.parseDouble(c[5]),
                                LocalDateTime.parse(c[6], FORMATO));
                        viaje.setDuracionMinutos(Integer.parseInt(c[7]));
                        gestion.agregarViajeInicial(viaje);
                        viajes.put(viaje.getIdViaje(), viaje);
                        for (Buses base : gestion.getListaBuses()) {
                            Buses copia = new Buses(base.getIdBus(), base.getCapacity());
                            viaje.agregarBus(copia);
                            busesViaje.put(viaje.getIdViaje() + ":" + copia.getIdBus(), copia);
                        }
                        break;
                    case "PASAJERO":
                        Pasajeros pasajero = new Pasajeros(Integer.parseInt(c[1]), Integer.parseInt(c[3]), c[2]);
                        Viajes v = viajes.get(Integer.parseInt(c[4]));
                        if (v == null) {
                            throw new IOException(
                                    "El pasajero " + pasajero.getIdPasajero()
                                    + " referencia un viaje inexistente: " + c[4]
                                );
                            }

                            Buses b = busesViaje.get(c[4] + ":" + c[5]);

                            if (b == null) {
                                throw new IOException(
                                    "El pasajero " + pasajero.getIdPasajero()
                                    + " referencia el bus " + c[5]
                                    + " en el viaje " + c[4]
                                    + ", pero ese bus no existe en ese viaje."
                                );
                            }

                            try {
                                b.agregarPasajero(pasajero);
                                gestion.agregarPasajeroInicial(pasajero);
                            } catch (CapacidadExcedidaException e) {
                                throw new IOException(
                                    "No se pudo cargar el pasajero "
                                    + pasajero.getIdPasajero() + ": " + e.getMessage()
                                );
                            }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}