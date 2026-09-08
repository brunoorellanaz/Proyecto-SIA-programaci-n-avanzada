/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bruno
 */
import java.io.*;
import java.util.*;
import java.time.*;

public class CargarDatos {

    private HashMap<Integer, Buses> mapaBuses = new HashMap<>();
    private ArrayList<Pasajeros> listaPasajeros = new ArrayList<>();
   
    
    
    public ArrayList<Viajes> leerCsv(String rutaArchivo){
        ArrayList<Viajes> listaViajes = new ArrayList<>();
        
        try(BufferedReader lector = new BufferedReader( new FileReader(rutaArchivo))){
        
            String linea = "";
            //lee encabezados
            lector.readLine();
        
            while((linea = lector.readLine()) != null){
                String[] campo = linea.split(",");

                if(campo[0].equals("VIAJE")){
                    int id_viaje = Integer.parseInt(campo[1]);
                    String origen = campo[4];
                    String destino = campo[5];
                    double costoViaje = Double.parseDouble(campo[6]); 
                    double costoPasaje = Double.parseDouble(campo[7]);
                    int cantidadBuses = Integer.parseInt(campo[9]);
                    LocalTime horaInicio = LocalTime.parse(campo[11]);

                    Viajes viaje = new Viajes(
                        id_viaje,
                        origen, 
                        destino,
                        costoViaje,
                        costoPasaje,
                        horaInicio
                    );
                    int contador = 0;

                    for(Buses busBase : mapaBuses.values()){
                        if(contador >= cantidadBuses){
                            break;
                        }

                        Buses bus = new Buses(
                            busBase.getIdBus(),
                            busBase.getCapacity()
                        );

                        viaje.agregarBus(bus);

                        contador++;
                    }   
                    listaViajes.add(viaje);
                }
            
                if(campo[0].equals("BUS")){
                    int id_bus = Integer.parseInt(campo[1]);
                    int capacidad = Integer.parseInt(campo[8]);
                    Buses bus = new Buses(
                    id_bus, 
                    capacidad
                    );

                    mapaBuses.put(bus.getIdBus(), bus);
                }
                if(campo[0].equals("PASAJERO")){
                    int id_pasajero = Integer.parseInt(campo[1]);
                    String nombre = campo[2];
                    int edad = Integer.parseInt(campo[3]);

                    Pasajeros pasajero = new Pasajeros(
                    id_pasajero,
                    edad,
                    nombre
                    );
                    listaPasajeros.add(pasajero);
                }

                
                
            }    
        } catch (IOException e){
            System.out.println("Error al leer esta cosa");
        }
        return listaViajes;
    }
}
