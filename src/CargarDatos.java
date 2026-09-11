import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class CargarDatos {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static void cargar(gestionbuses gestion, String ruta) throws IOException {

        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {

            gestion.limpiarDatos();

            String linea;

            HashMap<Integer, Viajes> viajes = new HashMap<>();
            HashMap<String, Buses> busesViaje = new HashMap<>();

            // Permite detectar si estamos cargando el formato antiguo.
            boolean hayAsignacionesDeBuses = false;

            // Ignorar cabecera.
            lector.readLine();

            while ((linea = lector.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] c = linea.split(";", -1);

                switch (c[0]) {

                    // ==================== BUS ====================
                    case "BUS":

                        gestion.agregarBusInicial(
                                Integer.parseInt(c[1]),
                                Integer.parseInt(c[2])
                        );

                        break;

                    // ==================== VIAJE ====================
                    case "VIAJE":

                        Viajes viaje = new Viajes(
                                Integer.parseInt(c[1]),
                                c[2],
                                c[3],
                                Double.parseDouble(c[4]),
                                Double.parseDouble(c[5]),
                                LocalDateTime.parse(c[6], FORMATO)
                        );

                        viaje.setDuracionMinutos(
                                Integer.parseInt(c[7])
                        );

                        gestion.agregarViajeInicial(viaje);

                        viajes.put(
                                viaje.getIdViaje(),
                                viaje
                        );

                        break;

                    // ==================== BUS DEL VIAJE ====================
                    case "BUS_VIAJE":

                        hayAsignacionesDeBuses = true;

                        int idViaje = Integer.parseInt(c[1]);
                        int idBus = Integer.parseInt(c[2]);
                        int capacidad = Integer.parseInt(c[3]);

                        Viajes viajeBus = viajes.get(idViaje);

                        if (viajeBus == null) {
                            throw new IOException(
                                    "El bus " + idBus
                                    + " referencia un viaje inexistente: "
                                    + idViaje
                            );
                        }

                        Buses busViaje =
                                new Buses(idBus, capacidad);

                        viajeBus.agregarBus(busViaje);

                        busesViaje.put(
                                idViaje + ":" + idBus,
                                busViaje
                        );

                        break;

                    // ==================== PASAJERO ====================
                    case "PASAJERO":

                        Pasajeros pasajero = new Pasajeros(
                                Integer.parseInt(c[1]),
                                Integer.parseInt(c[3]),
                                c[2]
                        );

                        int pasajeroViajeId =
                                Integer.parseInt(c[4]);

                        int pasajeroBusId =
                                Integer.parseInt(c[5]);

                        Viajes viajePasajero =
                                viajes.get(pasajeroViajeId);

                        if (viajePasajero == null) {
                            throw new IOException(
                                    "El pasajero "
                                    + pasajero.getIdPasajero()
                                    + " referencia un viaje inexistente: "
                                    + pasajeroViajeId
                            );
                        }

                        Buses busPasajero =
                                busesViaje.get(
                                        pasajeroViajeId
                                        + ":"
                                        + pasajeroBusId
                                );

                        if (busPasajero == null) {
                            throw new IOException(
                                    "El pasajero "
                                    + pasajero.getIdPasajero()
                                    + " referencia el bus "
                                    + pasajeroBusId
                                    + " en el viaje "
                                    + pasajeroViajeId
                                    + ", pero ese bus no existe en ese viaje."
                            );
                        }

                        try {

                            busPasajero.agregarPasajero(
                                    pasajero
                            );

                            gestion.agregarPasajeroInicial(
                                    pasajero
                            );

                        } catch (CapacidadExcedidaException e) {

                            throw new IOException(
                                    "No se pudo cargar el pasajero "
                                    + pasajero.getIdPasajero()
                                    + ": "
                                    + e.getMessage()
                            );
                        }

                        break;

                    default:
                        // Ignorar registros desconocidos.
                        break;
                }
            }

            // ==================== COMPATIBILIDAD ====================
            // El CSV antiguo no tenía registros BUS_VIAJE.
            if (!hayAsignacionesDeBuses) {

                for (Viajes viaje : viajes.values()) {

                    for (Buses base : gestion.getListaBuses()) {

                        Buses copia =
                                new Buses(
                                        base.getIdBus(),
                                        base.getCapacity()
                                );

                        viaje.agregarBus(copia);

                        busesViaje.put(
                                viaje.getIdViaje()
                                + ":"
                                + copia.getIdBus(),
                                copia
                        );
                    }
                }
            }
        }
    }
}