import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalTime;

public class Viajes {

    private int id_viaje;
    private String origen;
    private String destino;
    private HashMap<Integer, Buses> buses;
    private double costoViaje;
    private double costoPasaje;
    private LocalTime horaInicio;

    public Viajes(int id_viaje, String origen, String destino,
                  double costoViaje, double costoPasaje,
                  LocalTime horaInicio) {

        this.id_viaje = id_viaje;
        this.origen = origen;
        this.destino = destino;
        this.costoViaje = costoViaje;
        this.costoPasaje = costoPasaje;
        this.horaInicio = horaInicio;
        this.buses = new HashMap<>();
    }

    // Setters
    public void setIdViaje(int id_viaje) {
        this.id_viaje = id_viaje;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setCostoViaje(double costoViaje) {
        this.costoViaje = costoViaje;
    }

    public void setCostoPasaje(double costoPasaje) {
        this.costoPasaje = costoPasaje;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }


    // Getters
    public int getIdViaje() {
        return id_viaje;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public double getCostoViaje() {
        return costoViaje;
    }

    public double getCostoPasaje() {
        return costoPasaje;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }



    // Agregar bus al viaje
    public void agregarBus(Buses bus) {

        if (estaDisponible()) {
            buses.put(bus.getIdBus(), bus); // ESTE METODO NO EXISTE Hay que arreglarlo
        }
    }

    // Verificar si el viaje todavía está disponible
    public boolean estaDisponible() {
        return LocalTime.now().isBefore(horaInicio);
    }

    // Buscar un bus con espacio
    public Buses buscarBusDisponible() {

        for (Buses bus : buses.values()) {

            if (bus.getDisponibility()
                    && bus.getCantidadPasajeros() < bus.getCapacity()) {

                return bus;
            }
        }
        // AQUI SE PUEDE HACER UN TRY CATCH CON LOS BUSES DISPONIBLES
        return null;
    }

    // Comprobar rentabilidad
    public boolean esRentable(Buses bus) {

        double ingresos =
                bus.getCantidadPasajeros() * costoPasaje; 

        return ingresos > costoViaje;
    }

    // Reasignar pasajeros
    public void reasignarBus(Buses bus) {

      
        for (Buses otroBus : buses.values()) {

            if (otroBus.getIdBus() == bus.getIdBus()) {
                continue;
            }

            while (
                otroBus.getCantidadPasajeros() < otroBus.getCapacity()
                && bus.getCantidadPasajeros() > 0){

                Pasajeros pasajero = otroBus.obtenerPasajero(0);

                bus.eliminarPasajero(pasajero);
                otroBus.agregarPasajero(pasajero);
            }

            if ( bus.getCantidadPasajeros() <= 0) {
                break;
            }
        }
    }

    // Mostrar información del viaje
    public void mostrarViaje() {

        System.out.println("\n--- VIAJE ---");
        System.out.println("ID viaje: " + id_viaje);
        System.out.println("Origen: " + origen);
        System.out.println("Destino: " + destino);
        System.out.println("Hora: " + horaInicio);
        System.out.println("Costo viaje: $" + costoViaje);
        System.out.println("Costo pasaje: $" + costoPasaje);

        for (Buses bus : buses.values()) {

            System.out.println(
                "Bus " + bus.getIdBus()
                + " | Pasajeros: "
                + bus.getCantidadPasajeros()
                + "/" + bus.getCapacity()
            );
        }
    }
}