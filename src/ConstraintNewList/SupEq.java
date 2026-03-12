package ConstraintNewList;

import java.util.Iterator;
import java.util.Set;

public class SupEq extends Constraint{

    public SupEq( int mprim, double vprim ) {
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
            if(!(vprim <= v)){
                iterator.remove();
            }
        }
        return remainingDistinctValues;
    }

    @Override
    public String toString() {
        return "SupEq(m':  "+this.getMprim()+", v': "+ this.getVprim()+")";
    }
}
