import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class OptimizedFips {
    protected final Database dataset;
    ArrayList<BigDecimal> CumulativeObjectsweights = new ArrayList<>();
    BigDecimal ObjectstotalWeight = BigDecimal.ZERO;
    Random randGenerator;
    int seed;
    public OptimizedFips(Database dataset, int seed) {
        this.dataset = dataset;
        this.seed = seed;
        this.randGenerator = new Random(this.seed);
        ProcessObjectsWeights();
    }


    public void ProcessObjectsWeights() {
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


                double AttributeWeight = Jgm.size() * Lgm.size();
                objectWeight = objectWeight.multiply(BigDecimal.valueOf(AttributeWeight));
            }

            if (CumulativeObjectsweights.isEmpty()) {
                CumulativeObjectsweights.add(objectWeight);
            } else {
                BigDecimal last = CumulativeObjectsweights.get(CumulativeObjectsweights.size() - 1);
                CumulativeObjectsweights.add(last.add(objectWeight));
            }
            ObjectstotalWeight.add(objectWeight);

        }
        if(Objects.equals(CumulativeObjectsweights.get(0), CumulativeObjectsweights.get(CumulativeObjectsweights.size() - 1))){
            System.out.println("WARINING: The CumulativeObjectsweights contains the same weights");
        }
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

            int indexlb = randGenerator.nextInt(Lgm.size());  // tire un entier entre 0 et size-1
            int indexub = randGenerator.nextInt(Jgm.size());  // tire un entier entre 0 et size-1

            resultingIP.add(Lgm.get(indexlb));
            resultingIP.add(Jgm.get(indexub));
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
