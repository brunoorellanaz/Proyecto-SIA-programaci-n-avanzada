import java.util.ArrayList;
import java.time.LocalTime;

public class gestionbuses {

    private ArrayList<Buses> listaBuses;
    private ArrayList<Pasajeros> listaPasajeros;
    private ArrayList<Viajes> listaViajes;

    private int contadorViajes = 1;

    public gestionbuses() {

        listaBuses = new ArrayList<>();
        listaPasajeros = new ArrayList<>();
        listaViajes = new ArrayList<>();

        // Buses iniciales
        listaBuses.add(new Buses(1, 40));
        listaBuses.add(new Buses(2, 40));
        listaBuses.add(new Buses(3, 50));
    }

    // =====================================================
    // RESERVAR VIAJE
    // =====================================================

    public void reservarViaje(
            int idPasajero,
            String nombre,
            int edad,
            String origen,
            String destino,
            String fechaHora) {

        // Crear pasajero
        Pasajeros pasajero =
                new Pasajeros(idPasajero, edad, nombre);

        // Convertir hora
        LocalTime hora;

        try {

            String horaString = fechaHora;

            // Si el usuario escribe "Lunes 14:30"
            if (fechaHora.contains(" ")) {
                horaString =
                        fechaHora.substring(fechaHora.lastIndexOf(" ") + 1);
            }

            hora = LocalTime.parse(horaString);

        } catch (Exception e) {

            System.out.println(
                "Formato de hora inválido."
                + " Use por ejemplo: 14:30"
            );

            return;
        }

        // Buscar un viaje existente
        Viajes viajeEncontrado = null;

        for (Viajes viaje : listaViajes) {

            if (viaje.getOrigen().equalsIgnoreCase(origen)
                    && viaje.getDestino().equalsIgnoreCase(destino)
                    && viaje.getHoraInicio().equals(hora)) {

                viajeEncontrado = viaje;
                break;
            }
        }

        // Si no existe, crear uno
        if (viajeEncontrado == null) {

            viajeEncontrado = new Viajes(
                    contadorViajes++,
                    origen,
                    destino,
                    100000,
                    5000,
                    hora
            );

            // Agregar todos los buses disponibles al viaje
            for (Buses bus : listaBuses) {
                viajeEncontrado.agregarBus(bus);
            }

            listaViajes.add(viajeEncontrado);
        }

        // Comprobar disponibilidad
        if (!viajeEncontrado.estaDisponible()) {

            System.out.println(
                "El viaje ya comenzó o no está disponible."
            );

            return;
        }

        // Buscar bus
        Buses busDisponible =
                viajeEncontrado.buscarBusDisponible();

        if (busDisponible == null) {

            System.out.println(
                "No hay buses disponibles para este viaje."
            );

            return;
        }

        // Agregar pasajero
        if (busDisponible.agregarPasajero(pasajero)) {

            listaPasajeros.add(pasajero);

            System.out.println(
                "\nViaje reservado con éxito."
            );

            System.out.println(
                "Pasajero: " + nombre
            );

            System.out.println(
                "Bus asignado: "
                + busDisponible.getIdBus()
            );

            System.out.println(
                "Viaje ID: "
                + viajeEncontrado.getIdViaje()
            );

        } else {

            System.out.println(
                "No se pudo realizar la reserva."
            );
        }
    }

    // =====================================================
    // CANCELAR VIAJE
    // =====================================================

    public void cancelarViaje(int idReserva) {

    Pasajeros pasajeroEncontrado = null;

    for (Pasajeros pasajero : listaPasajeros) {

        if (pasajero.getIdPasajero() == idReserva) {
            pasajeroEncontrado = pasajero;
            break;
        }
    }

    if (pasajeroEncontrado == null) {

        System.out.println(
            "No se encontró la reserva."
        );

        return;
    }

    Buses bus = pasajeroEncontrado.getBus();

    if (bus != null) {
        bus.eliminarPasajero(pasajeroEncontrado);
    }

    listaPasajeros.remove(pasajeroEncontrado);

    System.out.println(
        "Reserva " + idReserva
        + " cancelada correctamente."
    );
    }

    // =====================================================
    // REAGENDAR VIAJE
    // =====================================================

    public void reagendarViaje(
            int idReserva,
            String nuevaFechaHora) {

        Pasajeros pasajeroEncontrado = null;

        for (Pasajeros pasajero : listaPasajeros) {

            if (pasajero.getIdPasajero() == idReserva) {

                pasajeroEncontrado = pasajero;
                break;
            }
        }

        if (pasajeroEncontrado == null) {

            System.out.println(
                "No se encontró la reserva."
            );

            return;
        }

        LocalTime nuevaHora;

        try {

            String horaString = nuevaFechaHora;

            if (nuevaFechaHora.contains(" ")) {

                horaString =
                    nuevaFechaHora.substring(
                        nuevaFechaHora.lastIndexOf(" ") + 1
                    );
            }

            nuevaHora = LocalTime.parse(horaString);

        } catch (Exception e) {

            System.out.println(
                "Formato de hora inválido. Use HH:mm."
            );

            return;
        }

        // Buscar un viaje compatible
        Viajes nuevoViaje = null;

        for (Viajes viaje : listaViajes) {

            if (viaje.getHoraInicio().equals(nuevaHora)
                    && viaje.estaDisponible()) {

                nuevoViaje = viaje;
                break;
            }
        }

        if (nuevoViaje == null) {

            System.out.println(
                "No existe un viaje disponible para esa hora."
            );

            return;
        }

        Buses nuevoBus =
                nuevoViaje.buscarBusDisponible();

        if (nuevoBus == null) {

            System.out.println(
                "No hay espacio disponible en el nuevo viaje."
            );

            return;
        }

        // Sacar del bus anterior
        Buses busAnterior =
                pasajeroEncontrado.getBus();

        if (busAnterior != null) {
            busAnterior.eliminarPasajero(pasajeroEncontrado);
        }

        // Agregar al nuevo bus
        nuevoBus.agregarPasajero(pasajeroEncontrado);

        System.out.println(
            "Reserva " + idReserva
            + " reagendada correctamente."
        );

        System.out.println(
            "Nueva hora: " + nuevaHora
        );

        System.out.println(
            "Nuevo bus: " + nuevoBus.getIdBus()
        );
    }

    // =====================================================
    // MOSTRAR BUSES
    // =====================================================

    public void mostrarBuses() {

        System.out.println("\n=== BUSES ===");

        for (Buses bus : listaBuses) {

            System.out.println(
                "Bus " + bus.getIdBus()
                + " | Capacidad: "
                + bus.getPasajeros().size()
                + "/" + bus.getCapacity()
                + " | Disponible: "
                + bus.getDisponibility()
            );
        }
    }

    // =====================================================
    // MOSTRAR VIAJES
    // =====================================================

    public void mostrarViajes() {

        if (listaViajes.isEmpty()) {

            System.out.println(
                "No existen viajes registrados."
            );

            return;
        }

        for (Viajes viaje : listaViajes) {
            viaje.mostrarViaje();
        }
    }
}