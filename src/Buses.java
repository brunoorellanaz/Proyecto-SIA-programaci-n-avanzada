
public class Buses {
    public int id_bus;  
    public Viaje id_viaje;
    public int capacity;
    public boolean disponibility;

    public Buses(int id_bus,Viaje id_viaje, int capacity){
        this.id_bus = id_bus;
        this.id_viaje = id_viaje;
        this.capacity = capacity;
        this.disponibility = true;
    }
}
