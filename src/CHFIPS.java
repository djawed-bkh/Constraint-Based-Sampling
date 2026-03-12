import ConstraintNewList.Constraint;

import java.math.BigDecimal;
import java.util.*;

public class CHFIPS {



    protected final Database dataset;
    ArrayList<BigDecimal> CumulativeIHweights = new ArrayList<>();
    BigDecimal ObjectstotalIH = BigDecimal.ZERO;
    Random randGenerator;
    int seed;

    ArrayList<Constraint> ConstraintList;


    public CHFIPS(int seed, Database dataset, ArrayList<Constraint> ConstraintList) {
        this.seed = seed;
        this.randGenerator = new Random(this.seed);
        this.dataset = dataset;
        this.ConstraintList = ConstraintList;
        ProcessObjectsWeights(ConstraintList);
    }






    public IP drawIP() {
        //todo: A tester

        /* Method that draws an interval pattern proportional to volume
         *
         * Return: sampled IP
         */
        ArrayList<Double> resultingIP = new ArrayList<>();

        //     STEP 1: draw an object proportionally to w(g)
        int drawnObject =DrawObject();// draw object proportionally to w(g)


        //     STEP 2: draw intervals bounds proportionally to w(a) and w(b)
        for (int m = 0; m < dataset.getColumnsNumberNumerical(); m++) {

            ArrayList<Double> Lgm = processLgm(m, dataset.getObject(drawnObject).get(m));
            ArrayList<Double> Jgm = processJgm(m, dataset.getObject(drawnObject).get(m));


            /*           draw a LOWER BOUND   */
            ArrayList<BigDecimal> cumulativeLBWeights = cumulativeLowerBoundWeights(Lgm, Jgm); // process the weight of each distinct value

            //todo: voir si on peut inverser la liste au lieu de faire un mapping (pour gagner en temps cpu)
            List<Map.Entry<Integer, BigDecimal>> indexedList = new ArrayList<>();
            for (int i = 0; i < cumulativeLBWeights.size(); i++) {
                indexedList.add(new AbstractMap.SimpleEntry<>(i, cumulativeLBWeights.get(i)));
            }
            indexedList.sort(Comparator.comparing(Map.Entry::getValue));

            int indexInSortedList = DrawlbIndex(Lgm, indexedList);          // use a binary search to get the desired value

          /*  System.out.println("Lgm: "+ Lgm);
            System.out.println("indexedList:  "+ indexedList);*/
            double valueLB = Lgm.get(indexedList.get(indexInSortedList).getKey());   // get the value corresponding to the key


            /*    UPPER BOUND   */

            ArrayList<BigDecimal> cumulativeUBWeights = cumulativeUpperBoundWeights(Jgm, valueLB); // process the weight of each distinct value

            int indexInJgm = drawUbIndex(Jgm, cumulativeUBWeights);    // get the desired value with a binary search
            double valueUB = Jgm.get(indexInJgm);   // this is the upper bound

            resultingIP.add(valueLB);           //Add the sampled interval
            resultingIP.add(valueUB);
        }
        return new IP(resultingIP, dataset, false);
    }


    public int DrawlbIndex(ArrayList<Double> lbList, List<Map.Entry<Integer, BigDecimal>> SortedCumulativelbWeights) {
        /* * Method that draws a lower bound proportionally to it weight
         * Recherche dichotomique
         * Return: drawn lower bound
         **/

        if (lbList.size() > 1) {
            double randomValue = randGenerator.nextDouble();
            BigDecimal drawnlowerBound = BigDecimal.valueOf(randomValue)
                    .multiply(SortedCumulativelbWeights.get(SortedCumulativelbWeights.size() - 1).getValue());
            int result = FindLBBoundIndex(0, lbList.size() - 1, drawnlowerBound, SortedCumulativelbWeights);
            return result;

        } else if (lbList.size() == 1) { //si il existe qu'une seule valeur dans Igm
            return 0;

        } else {
            System.out.println("The problem is in the lb Index");
            System.out.println("ERROR WE SHOULD NOT BE HERE");
            return -1;
        }
    }

    public int drawUbIndex(ArrayList<Double> UBList, ArrayList<BigDecimal> CumulativelbWeights) {

        if (UBList.size() > 1) {  // here we do a binary search
            BigDecimal drawnUpperBound = BigDecimal.valueOf(randGenerator.nextDouble())
                    .multiply(CumulativelbWeights.get(CumulativelbWeights.size() - 1));
            return FindUBBoundIndex(0, UBList.size() - 1, drawnUpperBound, CumulativelbWeights);

        } else if (UBList.size() == 1) { // here we just take the only candidate
            return 0;
        } else {    // aucun element dans la liste
            System.out.println("ERROR WE SHOULD NOT BE HERE");
            return -1;
        }
    }

    public ArrayList<BigDecimal> cumulativeUpperBoundWeights(ArrayList<Double> Jgm, double selectedLowerBound) {
        /*
         * process the weight of each value in Jgm that is candidate for being an upper bound
         * //checked seems correct
         * */
        ArrayList<BigDecimal> cumulativeUBWeights = new ArrayList<>();
        for (double b : Jgm) {
            BigDecimal weighta = BigDecimal.valueOf(b - selectedLowerBound);
            if (cumulativeUBWeights.isEmpty()) {
                cumulativeUBWeights.add(weighta);
            } else {
                BigDecimal previousweights = cumulativeUBWeights.get(cumulativeUBWeights.size() - 1);
                cumulativeUBWeights.add(previousweights.add(weighta));
            }
        }
        return cumulativeUBWeights;
    }

    public ArrayList<BigDecimal> cumulativeLowerBoundWeights(ArrayList<Double> Igm, ArrayList<Double> Jgm) {

        // todo: voir l'impact des changements
        /*
         * Calcule le poid de chaque borne inférieure candidate (dans Igm)
         *
         * \\checked and seems correct
         * */
        ArrayList<BigDecimal> cumulativelbWeights = new ArrayList<>();
        for (double a : Igm) {
            BigDecimal weighta = BigDecimal.ZERO;
            BigDecimal multiplication = BigDecimal.valueOf(Jgm.size() * a);
            for (double x : Jgm) {
                weighta = weighta.add(BigDecimal.valueOf(x).subtract(multiplication));
            }
            if (cumulativelbWeights.isEmpty()) {
                cumulativelbWeights.add(weighta);
            } else {
                BigDecimal previousweights = cumulativelbWeights.get(cumulativelbWeights.size() - 1);
                cumulativelbWeights.add(previousweights.add(weighta));
            }

        }
        return cumulativelbWeights;
    }


    private int FindLBBoundIndex(int left, int right, BigDecimal drawnLB, List<Map.Entry<Integer, BigDecimal>> sortedCumulativelbWeights) {
        /**  Method that search a cell in cumulative object weights that has a cumulative weight smaller than  drawnobject
         *
         * Return: The cell corresponding to the object respecting the condition
         * checked: seems correct
         **/
        if (left >= right) {
            return left;
        }

        int middle = (left + right) / 2;


        if (sortedCumulativelbWeights.get(middle).getValue().compareTo(drawnLB) >= 0) {    // si value(middle) > value drawnLB
            if (middle == 0 || (sortedCumulativelbWeights.get(middle - 1).getValue().compareTo(drawnLB) < 0)) {
                return middle;
            } else {
                return FindLBBoundIndex(left, middle - 1, drawnLB, sortedCumulativelbWeights);
            }
        } else {

            return FindLBBoundIndex(middle + 1, right, drawnLB, sortedCumulativelbWeights);
        }
    }


    private int FindUBBoundIndex(int left, int right, BigDecimal drawnUB, ArrayList<BigDecimal> sortedCumulativelbWeights) {

        /**  Method that search a cell in cumulative object weights that has a cumulative weight smaller than  drawnobject
         *
         * Return: The cell corresponding to the object respecting the condition
         **/
        if (left >= right) {
            return left;
        }

        int middle = (left + right) / 2;

        // Using BigDecimal comparison methods
        if (sortedCumulativelbWeights.get(middle).compareTo(drawnUB) >= 0) {    // si sortedCumulativelbWeights.get(middle) >= drawnUB
            if (middle == 0 || (sortedCumulativelbWeights.get(middle - 1).compareTo(drawnUB) < 0)) {
                return middle;
            } else {
                return FindUBBoundIndex(left, middle - 1, drawnUB, sortedCumulativelbWeights);
            }
        } else {
            return FindUBBoundIndex(middle + 1, right, drawnUB, sortedCumulativelbWeights);
        }
    }


    public HashMap<Double, BigDecimal> upperBoundWeights(int m, double vgm, double selected_a) {
        // todo: voir l'impact des changements

        HashMap<Double, BigDecimal> cumulativeUbWeights = new HashMap<>();
        //1- process Igm AND Jgm
        ArrayList<Double> Jgm = processJgm(m, vgm);
        for (double b : Jgm) {
            BigDecimal weightb = BigDecimal.valueOf(b - selected_a);
            if (cumulativeUbWeights.isEmpty()) {
                cumulativeUbWeights.put(b, weightb);
            } else {
                BigDecimal previousweights = cumulativeUbWeights.get(cumulativeUbWeights.size() - 1);
                cumulativeUbWeights.put(b, previousweights.add(weightb));
            }
        }
        return cumulativeUbWeights;
    }


    public ArrayList<Double> processLgm(int m, double vgm) {
        ArrayList<Double> Lgm = new ArrayList<>();

        for (int v = 0; v < dataset.getDistinctValues().get(m).size(); v++) {
            if (dataset.getDistinctValues().get(m).get(v) <= vgm) {
                Lgm.add(dataset.getDistinctValues().get(m).get(v));
            }
        }

        Set<Double> newL = new HashSet<>(Lgm);

        if (ConstraintList != null) {

            for (Constraint c : ConstraintList) {
                if (m == c.getMprim()) {
                    newL = c.filterL(m, c.getMprim(), c.getVprim(), vgm, newL);
                }
            }
        }

        return new ArrayList<>(newL);
    }


    public ArrayList<Double> processJgm(int m, double vgm) {
        //System.out.println("dataset.getDistinctValues().get(m): "+dataset.getDistinctValues().get(m));
        //System.out.println("vgm: "+ vgm);

        ArrayList<Double> Jgm = new ArrayList<>();
        for (int v = 0; v < dataset.getDistinctValues().get(m).size(); v++) {
            if (dataset.getDistinctValues().get(m).get(v) >= vgm) {
                Jgm.add(dataset.getDistinctValues().get(m).get(v));
            }
        }
        //System.out.println("Jgm: "+ Jgm);
        Set<Double> newJ = new HashSet<>(Jgm);

        if (ConstraintList != null) {

            for (Constraint c : ConstraintList) {
                if (m == c.getMprim()) {
                    newJ = c.filterJ(m, c.getMprim(), c.getVprim(), vgm, newJ);
                }
            }
        }
        return new ArrayList<>(newJ);
    }

    ;

    public int DrawObject() {
        /*
         * Method that draws an object index proportionally to it weight
         * Return: drawn object
         * */
       /* System.out.println("test 1");
        System.out.println("ObjectstotalIH: "+ObjectstotalIH);
        System.out.println("randGenerator.nextDouble()"+randGenerator.nextDouble());*/
        BigDecimal drawnobject = BigDecimal.valueOf(randGenerator.nextDouble()).multiply(ObjectstotalIH);
        //System.out.println("test 2");
        return FindObjectIndex(0, dataset.getObjectNumber() - 1, drawnobject);
    }


    private int FindObjectIndex(int left, int right, BigDecimal drawnobject) {
        /*
         * Method that searching a cell in CumulativeObjectsweights that has a cumulative weight smaller than  drawnobject
         *
         * Return: The cell corresponding to the object respecting the condition
         * */

        if(left>= right){
            return left;
        }
        int middle = (left + right) / 2;

        if (CumulativeIHweights.get(middle).compareTo(drawnobject) >= 0) {  //si middle est superieur a drawn object
            if (middle == 0 || CumulativeIHweights.get(middle - 1).compareTo(drawnobject) < 0) { // si on est au premier elt ou si l'elt en dessous est plus petit
                return middle;
            } else {
                return FindObjectIndex(left, middle, drawnobject);
            }
        } else {
            return FindObjectIndex(middle + 1, right, drawnobject);
        }
    }



    public void ProcessObjectsWeights(ArrayList<Constraint>ConstraintList) {
        /*
         * Method that process the weight of each object of the database through to the IPH formula
         *
         * */


        for (int g = 0; g < this.dataset.getObjectNumber(); g++) {
            //System.out.println("g: "+ g);
            BigDecimal IH = BigDecimal.ONE;
            ArrayList<BigDecimal> TIA = new ArrayList<>();

            for (int m = 0; m < this.dataset.getColumnsNumberNumerical(); m++) {

                double vgm = this.dataset.getObject(g).get(m); //ici on récupére la valeur vg,m

                if (vgm == -1) {
                    System.out.println("Warning: voir les conséquences");
                }
                BigDecimal vgmBigDecimal = BigDecimal.valueOf(vgm);
                Set<Double> Lgm = new HashSet<>();
                Set<Double> Jgm = new HashSet<>();
                double sumLGM = 0;
                double sumJGM = 0;
                // get the values that are in the basic L and J

                for (int v = 0; v < dataset.getDistinctValues().get(m).size(); v++) {
                    if (dataset.getDistinctValues().get(m).get(v) <= vgm) {
                        Lgm.add(dataset.getDistinctValues().get(m).get(v));
                    }
                    if (dataset.getDistinctValues().get(m).get(v) >= vgm) {
                        Jgm.add(dataset.getDistinctValues().get(m).get(v));
                    }
                }

                // filtrer values from L and J with regard to constraints

                Set<Double> newL = new HashSet<>(Lgm);
                Set<Double> newJ = new HashSet<>(Jgm);

                if (ConstraintList != null) {

                    for (Constraint c : ConstraintList) {
                        if (m == c.getMprim()) {
                            newL = c.filterL(m, c.getMprim(), c.getVprim(), vgm, newL);
                            newJ = c.filterJ(m, c.getMprim(), c.getVprim(), vgm, newJ);
                        }
                    }
                }
                //System.out.println("newL: "+newL.size());
                //System.out.println("newJ: "+newL.size());

                for (Double val : newL) {
                    sumLGM += val;

                }


                for (Double val : newJ) {
                    sumJGM += val;

                }

                BigDecimal processTIA = BigDecimal.valueOf(newL.size() * sumJGM - newJ.size() * sumLGM);


                /*if (m == 1 &&  vgm != 0.0){
                    System.out.print("newL.size() * sumJGM - newJ.size() * sumLGM: ");
                    System.out.println(newL.size() * sumJGM - newJ.size() * sumLGM);
                    System.out.println("m: "+m);
                    System.out.println("vgm: "+vgm);
                    System.out.println("newL: "+ newL);
                    System.out.println("newL: "+ newJ);
                    System.out.println("processTIA: "+ processTIA);
                }*/

                TIA.add(processTIA);

            }

            for (BigDecimal v : TIA) {
                IH = IH.multiply(v);
            }

            if (CumulativeIHweights.isEmpty()) {
                CumulativeIHweights.add(IH);
            } else {
                BigDecimal lastCumulativeWeight = CumulativeIHweights.get(CumulativeIHweights.size() - 1);
                CumulativeIHweights.add(lastCumulativeWeight.add(IH));
            }

            ObjectstotalIH = ObjectstotalIH.add(IH);

        }
        if (Objects.equals(CumulativeIHweights.get(0), CumulativeIHweights.get(CumulativeIHweights.size() - 1))) {
            System.out.println("WARINING: The CumulativeObjectsweights contains the same weights: "+ CumulativeIHweights.get(CumulativeIHweights.size()-1));
        }
    }













}
