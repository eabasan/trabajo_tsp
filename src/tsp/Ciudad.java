package tsp;

public class Ciudad {
    private int id;
    private double x;
    private double y;

    public Ciudad(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Calcular la distancia euclídea entre dos ciudades
    public double calculaDistancia(Ciudad otra) {
        return Math.sqrt(Math.pow(this.x - otra.getX(), 2) + Math.pow(this.y - otra.getY(), 2));
    }

    @Override
    public String toString() {
        return "Ciudad " + id + ": (" + x + ", " + y + ")";
    }
}
