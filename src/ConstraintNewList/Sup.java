package ConstraintNewList;

import java.util.Iterator;
import java.util.Set;

public class Sup extends Constraint{



    public Sup(int mprim, double vprim ) {
        super(mprim,vprim);
    }
    @Override
    public Set<Double> filterJ(int m, int mprim, double vprim, double vgm, Set<Double> remainingDistinctValues) {
        //Not removing anything
        return remainingDistinctValues;
    }

    @Override
    public Set<Double> filterL(int m, int mprim, double vprim, double vgm, Set<Double> remainingDistinctValues) {
        Iterator<Double> iterator = remainingDistinctValues.iterator();
        while (iterator.hasNext()) {
            Double v = iterator.next();
            if(!(vprim < v)){
                iterator.remove();
            }
        }
        return remainingDistinctValues;
    }


    @Override
    public String toString() {
        return "Sup(m':  "+this.getMprim()+", v': "+ this.getVprim()+")";
    }
}
