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

        
        int[] n = {100, 500, 1000, 5000};
        
        for (int iteraciones : n) {
            System.out.println("\nNúmero de iteraciones: " + iteraciones);
            long inicio = System.nanoTime();
            List<Ciudad> mejorRuta = Ciudad.randomSearchBA1(ciudades, iteraciones);
            long fin = System.nanoTime();
            long tiempoTotal = fin - inicio;
            System.out.println("Tiempo: " + tiempoTotal + " ns");

            // Imprimir la mejor ruta
            StringBuilder rutaStr = new StringBuilder("Mejor ruta encontrada: ");
            for (Ciudad ciudad : mejorRuta) {
                rutaStr.append(ciudad.getId()).append(" -> ");
            }
            rutaStr.append(mejorRuta.get(0).getId()); // Cerrar el ciclo volviendo a la primera ciudad
            System.out.println(rutaStr);
        }
    }
}
