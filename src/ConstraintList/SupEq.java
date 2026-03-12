package ConstraintList;

import java.util.Iterator;
import java.util.Set;

public class SupEq extends Constraint {

    public SupEq( int mprim, double vprim ) {
        super(mprim,vprim);
    }


    @Override
    public Set <Double> filterU(int m,int mprim, double vprim, double vgm, Set<Double>RemainingDistinctValues){
        Iterator<Double> iterator = RemainingDistinctValues.iterator();
        while (iterator.hasNext()) {
            Double v = iterator.next();
            if(!(vprim <= v)){
                iterator.remove();
            }
        }
        return RemainingDistinctValues;
    }

    @Override
    public Set <Double> filterA(int m,int mprim, double vprim, double vgm, Set<Double>RemainingDistinctValues){

        Iterator<Double> iterator = RemainingDistinctValues.iterator();
        while (iterator.hasNext()) {
            Double v = iterator.next();
            if(!(vprim <= vgm)){
                iterator.remove();
            }
        }
        return RemainingDistinctValues;
    }

    @Override
    public Set <Double> filterS(int m,int mprim, double vprim, double vgm, Set<Double>RemainingDistinctValues){

        Iterator<Double> iterator = RemainingDistinctValues.iterator();
        while (iterator.hasNext()) {
            Double v = iterator.next();
            if(!(vprim <= v)){
                iterator.remove();
            }
        }
        return RemainingDistinctValues;
    }

    @Override
    public Set <Double> filterH(int m,int mprim, double vprim, double vgm, Set<Double>RemainingDistinctValues){
        //Not removing anything
        return RemainingDistinctValues;
    }

    @Override
    public Set <Double> filterT(int m,int mprim, double vprim, double vgm, Set<Double>RemainingDistinctValues){
        Iterator<Double> iterator = RemainingDistinctValues.iterator();
        while (iterator.hasNext()) {
            Double v = iterator.next();
            if(vprim > v){
                iterator.remove();
            }
        }
        return RemainingDistinctValues;
    }


    @Override
    public String toString() {
        return "SupEq(m':  "+this.getMprim()+", v': "+ this.getVprim()+")";
    }

}
