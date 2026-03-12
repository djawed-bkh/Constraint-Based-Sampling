package ConstraintNewList;

import ConstraintNewList.Constraint;

import java.util.Iterator;
import java.util.Set;

public class Exclusion extends Constraint {


    public Exclusion( int mprim, double vprim ) {
        super(mprim,vprim);
    }

    @Override
    public Set<Double> filterJ(int m, int mprim, double vprim, double vgm, Set<Double> remainingDistinctValues) {
        if(vprim >= vgm){
            Iterator<Double> iterator = remainingDistinctValues.iterator();
            while (iterator.hasNext()) {
                Double v = iterator.next();
                if(!((vprim != vgm) && (vprim > v))){
                    iterator.remove();
                }
            }
        }
        return remainingDistinctValues;
    }

    @Override
    public Set<Double> filterL(int m, int mprim, double vprim, double vgm, Set<Double> remainingDistinctValues) {
        if(vprim <= vgm){
            Iterator<Double> iterator = remainingDistinctValues.iterator();
            while (iterator.hasNext()) {
                Double v = iterator.next();
                if(!((vprim != vgm) && (vprim < v))){
                    iterator.remove();
                }
            }
        }
        return remainingDistinctValues;
    }


    @Override
    public String toString() {
        return "Exc(m':  "+this.getMprim()+", v': "+ this.getVprim()+")";
    }
}
