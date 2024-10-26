/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author elena
 */
public class Ciudad {
    private double x;
    private double y;
    private int id;
    
    public Ciudad(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public double calculaDistancia(Ciudad otra){
        return Math.sqrt(Math.pow((this.x - otra.getX()), 2) + Math.pow((this.y - otra.getY()), 2));
    }

    @Override
    public String toString() {
        return "Ciudad " + id +": (" + x + ", " + y + " )";
    }
    public static List<Ciudad> readTSPFile(String fileName) {
        List<Ciudad> ciudades = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isCoordSection = false;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                
                // Buscar la sección de coordenadas
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
                    ciudades.add(new Ciudad(id, x, y));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ciudades;
    }
    
    // Método que calcula el coste de una ruta (suma de las distancias entre ciudades consecutivas)
    public static double calcularCostoRuta(List<Ciudad> ruta) {
        double costoTotal = 0.0;
        for (int i = 0; i < ruta.size() - 1; i++) {
            costoTotal += ruta.get(i).calculaDistancia(ruta.get(i + 1));
        }
        // Añadir la distancia desde la última ciudad de vuelta a la primera (ciclo)
        costoTotal += ruta.get(ruta.size() - 1).calculaDistancia(ruta.get(0));
        return costoTotal;
    }
    
    // Método que genera una ruta aleatoria (una permutación de las ciudades)
    public static List<Ciudad> generarRutaAleatoria(List<Ciudad> ciudades) {
        List<Ciudad> ruta = new ArrayList<>(ciudades);  // Crea una copia de la lista de ciudades
        Collections.shuffle(ruta);  // Mezcla aleatoriamente las ciudades para generar una ruta
        return ruta;
    }
    
    public static List<Ciudad> randomSearchBA1(List<Ciudad> ciudades, int numIteraciones) {
        List<Ciudad> mejorRuta = generarRutaAleatoria(ciudades);
        double mejorCosto = calcularCostoRuta(mejorRuta);

        for (int i = 0; i < numIteraciones; i++) {
            // Generar una nueva ruta aleatoria
            List<Ciudad> nuevaRuta = generarRutaAleatoria(ciudades);
            double nuevoCosto = calcularCostoRuta(nuevaRuta);

            // Si encontramos una ruta mejor, la actualizamos
            if (nuevoCosto < mejorCosto) {
                mejorRuta = nuevaRuta;
                mejorCosto = nuevoCosto;
            }
        }

        System.out.println("Mejor costo encontrado: " + mejorCosto);
        return mejorRuta;
    }
}
