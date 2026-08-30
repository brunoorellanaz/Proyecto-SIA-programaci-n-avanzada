import java.util.*;

public class Viajes {
    private int id_viaje;
    private String origen;
    private String destino;
    private boolean disponibilidad;
    private HashMap<Integer, Buses> buses;
    private double costoViaje;
    private double costoPasaje;

    
    public Viajes(int id_viaje, String origen, String destino, double costoViaje, double costoPasaje){
        this.id_viaje = id_viaje;
        this.origen = origen;
        this.destino = destino;
        this.disponibilidad = true;
        this.costoViaje = costoViaje;
        this.costoPasaje = costoPasaje;
        buses = new HashMap<>();

        
    }
     //setters
    //public void setIdViaje(int id_viaje){this.id_viaje = id_viaje;}
    //public void setOrigen(String origen){this.origen = origen;}
    //public void setDestino(String destino){this.destino = destino;}
    public void setDisponibilidad(boolean disponibilidad){this.disponibilidad = disponibilidad;}
    public void setCostoViaje(double costoViaje){this.costoViaje = costoViaje;}
    public void setCostoPasaje(double CostoPasaje){this.costoPasaje = costoPasaje;}
    
    //getters
    public int getIdViaje(){return id_viaje;}
    public String getOrigen(){return origen;}
    public String getDestino(){return destino;}
    public boolean getDisponibilidad(){return disponibilidad;}
    public double getCostoViaje(){return costoViaje;}
    public double getCostoPasaje(){return costoPasaje;}

    public void agregarBus(Buses bus){
        if(disponibilidad){
            buses.put(bus.getIdBus(), bus);
        }
        return;
    }
    public boolean esRentable(){
        for (Integer i: buses.values()){
            //if (i.horaInicio < horaActual){}
            if( (i.pasajeros).size() * costoPasaje < costoViaje){
                //borramos el viaje y pasamos todo lo demas a otros buses.
            }
        }
    }
    
}
