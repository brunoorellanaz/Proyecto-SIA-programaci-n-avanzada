import java.util.*;

public class Buses {
    private int id_bus;  
    private int capacity;
    private boolean disponibility;
    private ArrayList<Pasajeros> pasajeros;

    public Buses(int id_bus,int capacity){
        this.id_bus = id_bus;
        this.capacity = capacity;
        this.disponibility = true;
        pasajeros = new ArrayList<>();
    }

    //setters
    public void setIdBus(int id_bus){this.id_bus = id_bus;}
    public void setCapacity(int capacity){this.capacity = capacity;}
    //public void setDisponibility(boolean disponibility){this.disponibility = disponibility;}

    // getters
    public int getIdBus(){ return id_bus;}
    public int getCapacity(){ return capacity;}
    public boolean getDisponibility(){ return disponibility;}
    public ArrayList<Pasajeros> getPasajeros(){return pasajeros;}
    

    public void mostrarPasajeros(){
        for(int i = 0 ; i < pasajeros.size(); i++){
            System.out.println("Nombre: " + pasajeros.get(i).getNombre() + "| Edad: " + pasajeros.get(i).getEdad());
        }
        return;
    }

    public void agregarPasajero(Pasajeros nuevo_pasajero){
        if (disponibility && pasajeros.size()< capacity){
            pasajeros.add(nuevo_pasajero);
            nuevo_pasajero.setBus(this);
        }
       
        if(pasajeros.size() == capacity){disponibility = false;}
        return;
    }   
    
}  
