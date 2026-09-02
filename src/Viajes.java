import java.util.*;
import java.time.*;

public class Viajes {
    private int id_viaje;
    private String origen;
    private String destino;
    private HashMap<Integer, Buses> buses;
    private double costoViaje;
    private double costoPasaje;
    private LocalTime horaInicio;

    
    public Viajes(int id_viaje, String origen, String destino,
                  double costoViaje, double costoPasaje, LocalTime horaInicio){
        this.id_viaje = id_viaje;
        this.origen = origen;
        this.destino = destino;
        this.costoViaje = costoViaje;
        this.costoPasaje = costoPasaje;
        buses = new HashMap<>();
        this.horaInicio = horaInicio;

        
    }
     //setters
    public void setIdViaje(int id_viaje){this.id_viaje = id_viaje;}
    public void setOrigen(String origen){this.origen = origen;}
    public void setDestino(String destino){this.destino = destino;}
    public void setCostoViaje(double costoViaje){this.costoViaje = costoViaje;}
    public void setCostoPasaje(double CostoPasaje){this.costoPasaje = costoPasaje;}
    public void setHoraInicio(LocalTime horaInicio){this.horaInicio = horaInicio;}
    
    //getters
    public int getIdViaje(){return id_viaje;}
    public String getOrigen(){return origen;}
    public String getDestino(){return destino;}
    public double getCostoViaje(){return costoViaje;}
    public double getCostoPasaje(){return costoPasaje;}
    public LocalTime getHoraInicio() {return horaInicio;}

    public void agregarBus(Buses bus){
        if( estaDisponible() ){
            buses.put(bus.getIdBus(), bus);
        }
        return;
    }

    public boolean estaDisponible(){
        return (LocalTime.now()).isBefore(horaInicio);
    }
    
    public boolean esRentable(Buses bus){
       
        if( (( bus.getPasajeros() ).size() * costoPasaje) <= costoViaje){
            reasignarBus(bus);
            return false;
        }
        return true;
       
    }

    public void reasignarBus(Buses bus){
        
        for( Buses i : buses.values()  ){
            ArrayList<Pasajeros> listPasajeros = bus.getPasajeros();
            if( bus.getIdBus() == i.getIdBus() ) {continue;}
            
            while (i.getCapacity() - i.getPasajeros().size() > 0
                    && listPasajeros.size() > 0) {

                i.agregarPasajero( listPasajeros.get(0) );
                listPasajeros.remove(0);
            }
        }
    }   

    
        
}

