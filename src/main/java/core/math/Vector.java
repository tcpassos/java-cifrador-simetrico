package core.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe que representa um vetor e suas operações.
 */
public class Vector {
    
    private final int PRECISION = 5;
    
    public double x;
    public double y;
    public double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector rotate(double radX, double radY, double radZ) {
        return rotateX(radX).rotateY(radY).rotateZ(radZ);
    }
    
    public Vector rotateX(double radians) {
        double newY = (( y * Math.cos(radians)) - (z * Math.sin(radians)));
        double newZ = (( y * Math.sin(radians)) + (z * Math.cos(radians)));
        return new Vector(x, _round(newY, PRECISION), _round(newZ, PRECISION));
    }

    public Vector rotateY(double radians) {
        double newX = (( x * Math.cos(radians)) + (z * Math.sin(radians)));
        double newZ = (( x * -Math.sin(radians)) + (z * Math.cos(radians)));
        return new Vector(_round(newX, PRECISION), y, _round(newZ, PRECISION));
    }

    public Vector rotateZ(double radians) {
        double newX = (( x * Math.cos(radians)) - (y * Math.sin(radians)));
        double newY = (( x * Math.sin(radians)) + (y * Math.cos(radians)));
        return new Vector(_round(newX, PRECISION), _round(newY, PRECISION), z);
    }
    
    private double _round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector other = (Vector) obj;
        return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(other.x) &&
               Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y) &&
               Double.doubleToLongBits(this.z) == Double.doubleToLongBits(other.z);
    }

    @Override
    public String toString() {
        return String.format("[%f, %f, %f]", x, y, z);
    }

}
