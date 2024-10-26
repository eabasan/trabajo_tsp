/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tsp;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author elena
 */
public class TSP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Leer las ciudades desde el archivo TSP
        String fileName = "C:/Users/elena/Documents/UPO/ALGORITMICA I/EPDS/EPD4_EVALUABLE/tsp/a280.tsp";
        List<Ciudad> ciudades = Ciudad.readTSPFile(fileName);

        // Parámetro: número de iteraciones para BA1
        int numIteraciones = 1000;
        int[] n = {100, 500, 1000, 5000};
        
        for(int iteraciones : n){
            long inicio = System.nanoTime();
            List<Ciudad> mejorRuta = Ciudad.randomSearchBA1(ciudades, numIteraciones);
            long fin = System.nanoTime();
            long tiempoTotal = fin - inicio;
            System.out.println("Iteraciones: " + iteraciones + " Tiempo: " + tiempoTotal + " ns");
            //System.out.println("Solucion obtenida: " + Arrays.toString(mejorRuta));
            System.out.println("Solucion obtenida: " + mejorRuta);
        }

        // Ejecutar Búsqueda Aleatoria (BA1)
        //List<Ciudad> mejorRuta = Ciudad.randomSearchBA1(ciudades, numIteraciones);

        // Mostrar la mejor ruta encontrada
        /*System.out.println("Mejor ruta encontrada:");
        for (Ciudad ciudad : mejorRuta) {
            System.out.println(ciudad);
        }*/
    }
}
