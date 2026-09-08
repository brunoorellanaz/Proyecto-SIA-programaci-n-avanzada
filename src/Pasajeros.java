public class Pasajeros {

    private int id_pasajero;
    private Buses bus;
    private int edad;
    private String nombre;

    public Pasajeros(int id_pasajero, int edad, String nombre) {
        this.id_pasajero = id_pasajero;
        this.edad = edad;
        this.nombre = nombre;
        this.bus = null;
    }

    // Setters
    public void setBus(Buses bus) {
        this.bus = bus;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setIdPasajero(int id_pasajero){
        this.id_pasajero = id_pasajero;
    }

    // Getters
    public int getIdPasajero() {
        return id_pasajero;
    }

    public Buses getBus() {
        return bus;
    }

    public int getEdad() {
        return edad;
    }

    public String getNombre() {
        return nombre;
    }
}