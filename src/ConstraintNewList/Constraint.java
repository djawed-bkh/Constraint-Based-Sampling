package ConstraintNewList;

import java.util.Set;


public abstract class Constraint {

    private int mprim;
    private double vprim;


    public int getMprim() {
        return mprim;
    }

    public void setMprim(int mprim) {
        this.mprim = mprim;
    }

    public double getVprim() {
        return vprim;
    }

    public void setVprim(double vprim) {
        this.vprim = vprim;
    }

    public Constraint(int mprim, double v) {
        this.mprim = mprim;
        this.vprim = v;
    }

    public abstract Set<Double> filterJ(int m, int mprim, double vprim, double vgm, Set<Double> remainingDistinctValues);
    public abstract Set<Double> filterL(int m, int mprim, double vprim, double vgm, Set<Double> remainingDistinctValues);


}
