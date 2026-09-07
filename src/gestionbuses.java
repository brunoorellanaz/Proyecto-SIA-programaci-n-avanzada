import java.util.ArrayList;
import java.time.LocalTime;

public class gestionbuses {

    private ArrayList<Buses> listaBuses; //flota base
    private ArrayList<Pasajeros> listaPasajeros;
    private ArrayList<Viajes> listaViajes;
    private int contadorViajes = 1;

    public gestionbuses() {

        listaBuses = new ArrayList<>();
        listaPasajeros = new ArrayList<>();
        listaViajes = new ArrayList<>();

        // flota base: la empresa tiene estos buses actualmente y se guardan en (id + capacidad)
        listaBuses.add(new Buses(1, 40));
        listaBuses.add(new Buses(2, 40));
        listaBuses.add(new Buses(3, 50));
    }

    public ArrayList<Buses> getListaBuses() { return listaBuses; }
    public ArrayList<Pasajeros> getListaPasajeros() { return listaPasajeros; }
    public ArrayList<Viajes> getListaViajes() { return listaViajes; }

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
            //cada viaje recibe sus propios buses que son copias de la flota actual disponible
            //y no el objeto bus compartido, asi la ocupación no se filtra entre viajes
            for (Buses busBase : listaBuses) {
                viajeEncontrado.agregarBus(new Buses(busBase.getIdBus(), busBase.getCapacity()));
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

        //Agregar pasajero
        try {
            Pasajeros pasajero = busDisponible.agregarPasajero(idPasajero, edad, nombre);
            listaPasajeros.add(pasajero);
            System.out.println("\nViaje reservado con exito");
            System.out.println("Pasajero: " + nombre);
            System.out.println("Bus asignado : " + busDisponible.getIdBus());
            System.out.println("Viaje ID: " + viajeEncontrado.getIdViaje());
        } catch(CapacidadExcedidaException error) {
            System.out.println("No se pudo reservar: " + error.getMessage());
        }
    }

    // =====================================================
    // CANCELAR VIAJE
    // =====================================================

    public void cancelarViaje(int idReserva) {
        try {
            Pasajeros pasajero = buscarPasajero(idReserva);
            Buses bus = pasajero.getBus();
            if (bus != null) {
                bus.eliminarPasajero(pasajero);
            }
            listaPasajeros.remove(pasajero);
            System.out.println("Reserva " + idReserva + " cancelada correctamente");
        } catch (ElementoNoEncontradoException error) {
            System.out.println(error.getMessage());
        }
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

        // Sacar del bus anterior y agregar al nuevo
        try {
            Buses busAnterior = pasajeroEncontrado.getBus();
            nuevoBus.agregarPasajero(pasajeroEncontrado);
            if (busAnterior != null) {
                busAnterior.eliminarPasajero(pasajeroEncontrado);
            }
            System.out.println("Reserva " + idReserva + " reagendada correctamente");
            System.out.println("Nueva hora: " + nuevaHora);
            System.out.println("Nuevo bus: " + nuevoBus.getIdBus());
        } catch (CapacidadExcedidaException error) {
            System.out.println("No se pudo reagendar: " + error.getMessage());
        }
    }

    // =====================================================
    // BUSCAR PASAJEROS | SOBRECARGA 1
    // =====================================================
    // Búsqueda por ID del pasajero
    public Pasajeros buscarPasajero(int id) throws ElementoNoEncontradoException {
        for (Pasajeros p : listaPasajeros) {
            if (p.getIdPasajero() == id) {
                return p;
            }
        }
        throw new ElementoNoEncontradoException("No existe el pasajero con id " + id);
    }

    // =====================================================
    // BUSCAR PASAJEROS | SOBRECARGA 2
    // =====================================================
    // Búsqueda por nombre del pasajero
    public Pasajeros buscarPasajero(String nombre) throws ElementoNoEncontradoException {
        for (Pasajeros p : listaPasajeros) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        throw new ElementoNoEncontradoException("No existe un pasajero con nombre " + nombre);
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