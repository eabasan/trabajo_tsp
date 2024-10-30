/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author elena
 */
public class TSP {

    public static double[][] inicializarMatrizDistanciaDesdeTSP(String file) {
        List<double[]> ciudades = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isCoordSection = false;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                
                //Iniciar lectura en la sección de coordenadas
                if (line.equals("NODE_COORD_SECTION")) {
                    isCoordSection = true;
                    continue;
                }
                
                // Leer las coordenadas de las ciudades
                if (isCoordSection) {
                    if (line.equals("EOF")) break; // Termina si llega al final del archivo
                    String[] parts = line.split("\\s+");
                    int id = Integer.parseInt(parts[0]);
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    ciudades.add(new double[]{id, x, y});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numCiudades = ciudades.size();
        double[][] distancias = new double[numCiudades][numCiudades];
        
         // Calcula la distancia entre cada par de ciudades
        for (int i = 0; i < numCiudades; i++) {
            for (int j = 0; j < numCiudades; j++) {
                distancias[i][j] = calculaDistancia(ciudades.get(i), ciudades.get(j));
            }
        }
        return distancias;
    }
    public static double calculaDistancia(double[] ciudad1, double[] ciudad2) {
        return Math.sqrt(Math.pow(ciudad1[1] - ciudad2[1], 2) + Math.pow(ciudad1[2] - ciudad2[2], 2));
    }

    // Calcula el coste total de un camino completo
    public static double getDistanciaTotal(double[][] distancias, int[] camino) {
        double coste = 0;
        for (int i = 0; i < camino.length - 1; i++) {
            coste += distancias[camino[i]][camino[i + 1]];
        }
        coste += distancias[camino[camino.length - 1]][camino[0]];// Volver al inicio
        return coste;
    }

    // Genera un tour aleatorio inicial
    public static int[] getTour(int NMaxCiudades) {
        int[] tour = new int[NMaxCiudades];
        for (int i = 0; i < NMaxCiudades; i++) {
            tour[i] = i;
        }
        Random random = new Random();
        // Barajar el tour de manera aleatoria
        for (int i = NMaxCiudades - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = tour[i];
            tour[i] = tour[j];
            tour[j] = temp;
        }
        return tour;
    }
    // El algoritmo BA1 ejecuta iteraciones con caminos aleatorios y guarda la mejor solución
    public static int[] algoritmoAleatorioBA1(double[][] distancias, int iteraciones) {
        int NMaxCiudades = distancias.length;
        int[] mejorCamino = getTour(NMaxCiudades); // Camino inicial aleatorio
        double mejorCoste = getDistanciaTotal(distancias, mejorCamino);

        for (int i = 0; i < iteraciones; i++) {
            int[] caminoActual = getTour(NMaxCiudades);
            double costeActual = getDistanciaTotal(distancias, caminoActual);
            // Si el camino actual es mejor, lo guardamos
            if (costeActual < mejorCoste) {
                mejorCamino = caminoActual;
                mejorCoste = costeActual;
            }
        }
        System.out.println("Mejor camino: " + Arrays.toString(mejorCamino) + "\nCon coste " + mejorCoste);
        return mejorCamino;
    }
    // El algoritmo BA2 ejecuta iteraciones hasta que alcanza un máximo sin mejora
    public static int[] algoritmoAleatorioBA2(double[][] distancias, int maxIteraciones, int maxIterSinMejora) {
        int NMaxCiudades = distancias.length;
        int[] mejorCamino = getTour(NMaxCiudades);// Camino inicial aleatorio
        double mejorCoste = getDistanciaTotal(distancias, mejorCamino);
        int iterSinMejora = 0; //Contador de iteraciones sin mejora

        for (int i = 0; i < maxIteraciones; i++) {
            int[] caminoActual = getTour(NMaxCiudades);
            double costeActual = getDistanciaTotal(distancias, caminoActual);
            // Si el camino actual mejora la solución, actualizamos la mejor
            if (costeActual < mejorCoste) {
                mejorCamino = caminoActual;
                mejorCoste = costeActual;
                iterSinMejora = 0; // Reiniciar el contador si hay mejora
            } else {
                iterSinMejora++;
            }
             // Parar si se alcanzó el máximo de iteraciones sin mejora
            if (iterSinMejora >= maxIterSinMejora) {
                System.out.println("Parando por falta de mejoras tras " + maxIterSinMejora + " iteraciones.");
                break;
            }
        }

        System.out.println("Mejor camino encontrado: " + Arrays.toString(mejorCamino) + "\nCon coste " + mejorCoste);
        return mejorCamino;
    }
    
    
    public static void main(String[] args) {
         // Especifica la ruta relativa del archivo .tsp
        //String file = "C:\\Users\\elena\\Documents\\tsp\\a280.tsp";
        //String file = "C:\\Users\\elena\\Documents\\tsp\\berlin52.tsp";
        //String file = "C:\\Users\\elena\\Documents\\tsp\\kroA100.tsp";
        //String file = "C:\\Users\\elena\\Documents\\tsp\\kroA150.tsp";
        //String file = "C:\\Users\\elena\\Documents\\tsp\\kroA200.tsp";
        //String file = "C:\\Users\\elena\\Documents\\tsp\\vm1084.tsp";
        String file = "C:\\Users\\elena\\Documents\\tsp\\vm1748.tsp";
        double[][] distancias = inicializarMatrizDistanciaDesdeTSP(file);

        int[] n = {100, 500, 1000, 5000}; // Valores de iteraciones para BA1
        int[] p = {50, 100, 250, 500};    // Valores de iteraciones sin mejora para BA2

        // Ejecutar BA1 con diferentes valores de iteraciones y medir el tiempo
        for (int maxIteraciones : n) {
            System.out.println("\nBA1 - Iteraciones: " + maxIteraciones);
            long inicio = System.nanoTime();
            algoritmoAleatorioBA1(distancias, maxIteraciones);
            long fin = System.nanoTime();
            long tiempoTotal = (fin - inicio) / 1000000; // Convertir a milisegundos
            System.out.println("Tiempo de ejecución: " + tiempoTotal + " ms");
            System.out.println("------------------------");
        }

        // Ejecutar BA2 con diferentes valores de iteraciones sin mejora y medir el tiempo
        for (int maxIterSinMejora : p) {
            System.out.println("\nBA2 - p (Iteraciones sin mejora): " + maxIterSinMejora);
            long inicio = System.nanoTime();
            algoritmoAleatorioBA2(distancias, 5000, maxIterSinMejora);  //maxIteraciones limita la ejecución a 5000 iteraciones si no se cumple antes el criterio de p.
            long fin = System.nanoTime();
            long tiempoTotal = (fin - inicio) / 1000000; // Convertir a milisegundos
            System.out.println("Tiempo de ejecución: " + tiempoTotal + " ms");
            System.out.println("------------------------");
        }
    }
}
