/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tsp;

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
}
