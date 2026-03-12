import ConstraintNewList.Constraint;

import java.math.BigDecimal;
import java.util.*;

public class OptimizedCFips {
    protected final Database dataset;
    ArrayList<BigDecimal> CumulativeObjectsweights = new ArrayList<>();
    BigDecimal ObjectstotalWeight = BigDecimal.ZERO;
    Random randGenerator;
    int seed;
    ArrayList<Constraint> ConstraintList;
    public OptimizedCFips(Database dataset, int seed, ArrayList<Constraint> ConstraintList) {

        this.dataset = dataset;
        this.seed = seed;
        this.randGenerator = new Random(this.seed);
        this.ConstraintList = ConstraintList;
        ProcessObjectsWeights(ConstraintList);
    }


    public void ProcessObjectsWeights(ArrayList<Constraint>ConstraintList) {
        /*
         * Method that process the weight of each object of the database through to the NIP formula
         *
         * */

        for (int g = 0; g < this.dataset.getObjectNumber(); g++) {
            BigDecimal objectWeight = BigDecimal.ONE;

            for (int m = 0; m < this.dataset.getColumnsNumberNumerical(); m++) {

                double vgm = this.dataset.getObject(g).get(m); //ici on récupére la valeur vg,m

                if (vgm == -1) {

                    System.out.println("WARNING: voir les conséquences");
                }

                // calculer les valeurs Lgm et Jgm
                ArrayList<Double> Lgm = Lgm(m, vgm);
                ArrayList<Double> Jgm = Jgm(m, vgm);


                // filtrer les valeurs de Lgm et Jgm ne respectant pas les contraintes
                Set<Double> newL = ConstrainedLgm(m, vgm,Lgm,ConstraintList);
                Set<Double> newJ = ConstrainedJgm(m, vgm,Jgm,ConstraintList);


                //System.out.println("newJ.size "+newJ.size() );
                //System.out.println("newL.size "+newL.size() );

                double AttributeWeight = newJ.size() * newL.size();
                //System.out.println("objectWeight au début: "+ objectWeight);
                //System.out.println("AttributeWeight: "+AttributeWeight);

                objectWeight = objectWeight.multiply(BigDecimal.valueOf(AttributeWeight));
                //System.out.println("hadi hiya: "+objectWeight);
            }
            ObjectstotalWeight= ObjectstotalWeight.add(objectWeight);
                if (CumulativeObjectsweights.isEmpty()) {
                    CumulativeObjectsweights.add(objectWeight);
                    //System.out.println("hadi hiya2 : "+objectWeight);
                } else {
                    BigDecimal last = CumulativeObjectsweights.get(CumulativeObjectsweights.size() - 1);
                    CumulativeObjectsweights.add(last.add(objectWeight));
                    //System.out.println("hadi hiya3 : "+objectWeight);
                }



        }
        if(Objects.equals(CumulativeObjectsweights.get(0), CumulativeObjectsweights.get(CumulativeObjectsweights.size() - 1))){
            System.out.println("WARINING: The CumulativeObjectsweights contains the same weights");
        }

        for(int i =0; i< dataset.getObjectNumber(); i++){
           // System.out.println("g "+i+" : "+CumulativeObjectsweights.get(i));
        }
        //System.out.println("lewla ObjectstotalWeight: "+ ObjectstotalWeight);


    }


      public ArrayList<Double> Lgm( int m, double vgm){
            ArrayList<Double> FinalLgm = new ArrayList<>();

            for(int i =0; i < dataset.getDistinctValues().get(m).size(); i++){

                if(dataset.getDistinctValues().get(m).get(i) <= vgm){
                    FinalLgm.add(dataset.getDistinctValues().get(m).get(i));
                }
            }
            return FinalLgm;
        }


    public ArrayList<Double> Jgm( int m, double vgm){
        ArrayList<Double> FinalJgm = new ArrayList<>();

        for(int i =0; i < dataset.getDistinctValues().get(m).size(); i++){

            if(dataset.getDistinctValues().get(m).get(i) >= vgm){
                FinalJgm.add(dataset.getDistinctValues().get(m).get(i));
            }
        }
        return FinalJgm;
    }


    public Set<Double> ConstrainedLgm( int m, double vgm, ArrayList<Double>Lgm ,ArrayList<Constraint> ConstraintList){
        Set<Double> newL = new HashSet<>(Lgm);
        //System.out.println("LGM: "+ Lgm);
        //System.out.println("vgm: "+ vgm);
        if (ConstraintList != null) {
            for (Constraint c : ConstraintList) {
                if (m == c.getMprim()) {
                    newL = c.filterL(m, c.getMprim(), c.getVprim(), vgm, newL);
                }
            }
        }
       // System.out.println("newL: "+ newL);
        return newL;
    }


    public Set<Double> ConstrainedJgm( int m, double vgm, ArrayList<Double>Jgm ,ArrayList<Constraint> ConstraintList) {

        Set<Double> newJ = new HashSet<>(Jgm);

        if (ConstraintList != null) {
            for (Constraint c : ConstraintList) {
                if (m == c.getMprim()) {
                    newJ = c.filterJ(m, c.getMprim(), c.getVprim(), vgm, newJ);
                }
            }

        }
        return newJ;
    }




    public IP drawIP() {

        /* Method that draws an interval pattern proportional to fréquency
         *
         * Return: sampled IP
         */
        ArrayList<Double> resultingIP = new ArrayList<>();

        int drawnObject1 = DrawObject();           // draw object proportionally to w(g)
        for (int m = 0; m < dataset.getColumnsNumberNumerical(); m++) {
            double vgm = dataset.getObject(drawnObject1).get(m);   // ON RÉCUPÉRE La valeur vg,m

            ArrayList<Double> Lgm = Lgm(m,vgm);
            ArrayList<Double> Jgm = Jgm(m,vgm);

            ArrayList<Double> ValidLgm = new ArrayList<>(ConstrainedLgm(m, vgm,Lgm,ConstraintList));
            ArrayList<Double> ValidJgm = new ArrayList<>(ConstrainedJgm(m, vgm,Jgm,ConstraintList));

            //System.out.println("ValidLgm.size(): "+ValidLgm.size());
            int indexlb = randGenerator.nextInt(ValidLgm.size());  // tire un entier entre 0 et size-1
            int indexub = randGenerator.nextInt(ValidJgm.size());  // tire un entier entre 0 et size-1

            resultingIP.add(ValidLgm.get(indexlb));
            resultingIP.add(ValidJgm.get(indexub));
        }
        return new IP(resultingIP, dataset, false);
    }

    public int getIndexValue(double value, int m){
        for(int i =0; i < dataset.getDistinctValues().get(m).size(); i++){
            if(dataset.getDistinctValues().get(m).get(i)== value){
                return i;
            }

        }
        return -1;
    }





    public IP drawClosedIP() {

        /* Method that draws a CLOSED interval pattern proportional to fréquency
         *
         * Return: sampled IP
         */
        ArrayList<Double> resultingIP = new ArrayList<>();

        int drawnObject1 = DrawObject();           // draw object proportionally to w(g)
        for (int m = 0; m < dataset.getColumnsNumberNumerical(); m++) {
            double value = dataset.getObject(drawnObject1).get(m);   // ON RÉCUPÉRE La valeur vg,m
            int indexValue= getIndexValue(value, m); //todo: voir si ça fonctionne

            int indexlb = (int) (randGenerator.nextDouble() * indexValue);            //WARNING: works only for normalized data
            int indexub = (indexValue + (int) (randGenerator.nextDouble() * ((dataset.getDistinctValues().get(m).size()) - indexValue))); //WARNING: works only for normalized data
            resultingIP.add(dataset.distinctValues.get(m).get(indexlb));
            resultingIP.add(dataset.distinctValues.get(m).get(indexub));
        }
        IP myip= new IP(resultingIP, dataset, false);




        return new IP(myip.closureOperator(), dataset, true);
    }






    public int DrawObject() {
        /*
         * Method that draws an object index proportionally to it weight
         * Return: drawn object
         * */
        BigDecimal drawnobject = BigDecimal.valueOf(randGenerator.nextDouble()).multiply(ObjectstotalWeight);
        //System.out.println("ObjectstotalWeight: "+ObjectstotalWeight);
        //System.out.println("drawnObject: "+drawnobject);
        return FindIndex(0, dataset.getObjectNumber()-1, drawnobject);
    }

    private int FindIndex(int left, int right, BigDecimal drawnobject) {
        /*
         * Method that searching a cell in CumulativeObjectsweights that has a cumulative weight smaller than  drawnobject
         *
         * Return: The cell corresponding to the object respecting the condition
         * */

        int middle = (left + right) / 2;

        // Using BigDecimal comparison methods
        if (CumulativeObjectsweights.get(middle).compareTo(drawnobject) >= 0) {
            if (middle == 0 || CumulativeObjectsweights.get(middle - 1).compareTo(drawnobject) < 0) {
                return middle;
            } else {
                return FindIndex(left, middle, drawnobject);
            }
        } else {
            return FindIndex(middle + 1, right, drawnobject);
        }
    }


}
