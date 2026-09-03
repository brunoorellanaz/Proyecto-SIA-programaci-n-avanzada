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

    // Agregar pasajero
    public boolean agregarPasajero(Pasajeros nuevo_pasajero) {

        if (!disponibility) {
            return false;
        }

        if (pasajeros.size() >= capacity) {
            disponibility = false;
            return false;
        }

        pasajeros.add(nuevo_pasajero);
        nuevo_pasajero.setBus(this);

        if (pasajeros.size() >= capacity) {
            disponibility = false;
        }

        return true;
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