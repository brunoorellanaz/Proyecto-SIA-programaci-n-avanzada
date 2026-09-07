import java.util.ArrayList;

public class Buses {

    private int id_bus;
    private int capacity;
    private boolean disponibility;
    private ArrayList<Pasajeros> pasajeros;

    public Buses(int id_bus, int capacity) {
        this.id_bus = id_bus;
        this.capacity = capacity;
        this.disponibility = true;
        this.pasajeros = new ArrayList<>();
    }

    // Setters
    public void setIdBus(int id_bus) {
        this.id_bus = id_bus;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDisponibility(boolean disponibility) {
        this.disponibility = disponibility;
    }

    public void setPasajeros(ArrayList<Pasajeros> pasajeros) { this.pasajeros = pasajeros; }

    // Getters
    public int getIdBus() {
        return id_bus;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean getDisponibility() {
        return disponibility;
    }

    public ArrayList<Pasajeros> getPasajeros() {
        return pasajeros;
    }

    // Agregar pasajero versión 1: recibe un objeto pasajeros ya hecho
    public void agregarPasajero(Pasajeros nuevo_pasajero) throws CapacidadExcedidaException {
        if (pasajeros.size() >= capacity) {
            disponibility = false;
            throw new CapacidadExcedidaException(
                "El bus " + id_bus + " no tiene asientos disponibles");
        }

        pasajeros.add(nuevo_pasajero);
        nuevo_pasajero.setBus(this);

        if (pasajeros.size() >= capacity) {
            disponibility = false;
        }
    }

    //Versión 2 de agregar pasajero: recibe datos sueltos y crea el pasajero
    //sobrecarga del método agregar pasajero:
    public Pasajeros agregarPasajero(int id, int edad, String nombre) 
                throws CapacidadExcedidaException {
        Pasajeros p = new Pasajeros(id, edad, nombre);
        agregarPasajero(p);
        return p;
    }

    // Eliminar pasajero
    public boolean eliminarPasajero(Pasajeros pasajero) {

        if (pasajeros.remove(pasajero)) {
            pasajero.setBus(null);

            // Si había estado lleno, vuelve a estar disponible
            if (pasajeros.size() < capacity) {
                disponibility = true;
            }

            return true;
        }

        return false;
    }

    // Mostrar pasajeros
    public void mostrarPasajeros() {

        if (pasajeros.isEmpty()) {
            System.out.println("El bus no tiene pasajeros.");
            return;
        }

        for (Pasajeros pasajero : pasajeros) {
            System.out.println(
                "ID: " + pasajero.getIdPasajero()
                + " | Nombre: " + pasajero.getNombre()
                + " | Edad: " + pasajero.getEdad()
            );
        }
    }
}