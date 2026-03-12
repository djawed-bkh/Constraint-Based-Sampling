package ConstraintNewList;

import java.util.Iterator;
import java.util.Set;

public class Inf extends Constraint{

    public Inf( int mprim, double vprim ) {
        super(mprim,vprim);
    }
    @Override
    public Set<Double> filterJ(int m, int mprim, double vprim, double vgm, Set<Double> remainingDistinctValues) {
        Iterator<Double> iterator = remainingDistinctValues.iterator();
        while (iterator.hasNext()) {
            Double v = iterator.next();
            //System.out.println("v: "+v);
            //System.out.println("vprim: "+vprim);
            if(!( vprim > v)){
               // System.out.println("v: "+v);
                iterator.remove();
            }
        }
        return remainingDistinctValues;
    }

    @Override
    public Set<Double> filterL(int m, int mprim, double vprim, double vgm, Set<Double> remainingDistinctValues) {
        //not removing anything
        return remainingDistinctValues;
    }


    @Override
    public String toString() {
        return "Inf(m':  "+this.getMprim()+", v': "+ this.getVprim()+")";
    }
}
