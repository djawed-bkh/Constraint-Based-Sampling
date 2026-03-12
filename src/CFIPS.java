import ConstraintList.Constraint;

import java.util.*;

public class CFIPS {

    protected final Database dataset;
    ArrayList<Double> CumulativeObjectsweights = new ArrayList<>();
    double ObjectstotalWeight = 0;

    ArrayList<Constraint> ConstraintList;


    public CFIPS(Database dataset, ArrayList<Constraint> ConstraintList) {
        this.dataset = dataset;
        this.ConstraintList = ConstraintList;
        ProcessObjectsWeights(ConstraintList);
    }


    public IP drawIP() {

        /* Method that draws an interval pattern proportional to fréquency
         *
         * Return: sampled IP
         *
         */
        ArrayList<Double> resultingIP = new ArrayList<>();

        int drawnObject1 = DrawObject();           // draw object proportionally to w(g)
        List<List<Set<Double>>> availableValues = GetAvailableValuesWRTconstraints(drawnObject1,ConstraintList);  // get all the available values wrt the constraints
        for(int m = 0; m < dataset.getColumnsNumberNumerical(); m++){

            double [] interval= tirerIntervalle(
                    availableValues.get(m).get(0),
                    availableValues.get(m).get(1),
                    availableValues.get(m).get(2),
                    availableValues.get(m).get(3),
                    availableValues.get(m).get(4),
                    dataset.getObject(drawnObject1).get(m));

            resultingIP.add(interval[0]);
            resultingIP.add(interval[1]);
        }
        return new IP(resultingIP, dataset, false);
    }


    public int DrawObject() {
        /*
         * Method that draws an object index proportionally to it weight
         * Return: drawn object
         * */

        double drawnobject = Math.random() * ObjectstotalWeight;
        return FindIndex(0, dataset.getObjectNumber(), drawnobject);
    }

    private int FindIndex(int left, int right, double drawnobject) {
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


    public void ProcessObjectsWeights(ArrayList<Constraint> ConstraintList) {
        /*
         * NIP method
         * Method that process the weight of each object of the database through to the NIP formula
         * */

        for (int g = 0; g < this.dataset.getObjectNumber(); g++) {
            //System.out.println("g"+g);
            double objectWeight = 1;
            for (int m = 0; m < this.dataset.getColumnsNumberNumerical(); m++) {

                double vgm = this.dataset.getObject(g).get(m); //v_g,m

                if (vgm == -1) {
                    System.out.println("ERROR: We should not be here");
                }

                Set<Double> U = new HashSet<>();
                Set<Double> A = new HashSet<>();
                Set<Double> S = new HashSet<>();
                Set<Double> H = new HashSet<>();
                Set<Double> T = new HashSet<>();

                //1 check the conditions for the basic NIP terms
                for (Double v : dataset.distinctValues.get(m)) {
                    if (v < vgm) {
                        U.add(v);
                    }
                    if (v > vgm) {
                        A.add(v);
                    }
                    if (v < vgm) {
                        S.add(v);
                    }

                    if (v > vgm) {
                        H.add(v);
                    }
                    if (v == vgm) {
                        T.add(vgm);
                    }
                }

                //2- Handling constraints

                Set<Double> newU = new HashSet<>(U);
                Set<Double> newA = new HashSet<>(A);
                Set<Double> newS = new HashSet<>(S);
                Set<Double> newH = new HashSet<>(H);
                Set<Double> newT = new HashSet<>(T);
                if (ConstraintList != null) {

                    for (Constraint c : ConstraintList) {

                        if (m == c.getMprim()) {
                            //System.out.println("we are here");
                            newU = c.filterU(m, c.getMprim(), c.getVprim(), vgm, newU);
                            newA = c.filterA(m, c.getMprim(), c.getVprim(), vgm, newA);
                            newS = c.filterS(m, c.getMprim(), c.getVprim(), vgm, newS);
                            newH = c.filterH(m, c.getMprim(), c.getVprim(), vgm, newH);
                            newT = c.filterT(m, c.getMprim(), c.getVprim(), vgm, newT);
                        }
                    }
                }

                double component = 0;
                if (ConstraintList == null) {
                    component = U.size() + A.size() + (S.size() * H.size()) + T.size();
                } else {
                    component = newU.size() + newA.size() + (newS.size() * newH.size()) + newT.size();
                }


                objectWeight = objectWeight * component;
            }

            if (CumulativeObjectsweights.isEmpty()) {
                System.out.println(objectWeight);
                CumulativeObjectsweights.add(objectWeight);
            } else {
                System.out.println(objectWeight);
                double lastCumulativeWeight = CumulativeObjectsweights.get(CumulativeObjectsweights.size() - 1);
                CumulativeObjectsweights.add(lastCumulativeWeight + objectWeight);
            }

            System.out.println("Object: "+g+" Weight: "+objectWeight );
            ObjectstotalWeight = ObjectstotalWeight + objectWeight;

        }
        if (Objects.equals(CumulativeObjectsweights.get(0), CumulativeObjectsweights.get(CumulativeObjectsweights.size() - 1))) {
            System.out.println("WARINING: The CumulativeObjectsweights contains the same weights");
        }
    }


    public List<List<Set<Double>>> GetAvailableValuesWRTconstraints(int g, ArrayList<Constraint> ConstraintList) {
        /*
         * Method that process values that satisfy the constraints for a given object
         * */
        List<List<Set<Double>>> Result = new ArrayList<>();

        for (int m = 0; m < this.dataset.getColumnsNumberNumerical(); m++) {

            ArrayList<Set<Double>>AttributeSet= new ArrayList<>();
            double vgm = this.dataset.getObject(g).get(m); //v_g,m

            if (vgm == -1) {
                System.out.println("ERROR: We should not be here");
            }
            Set<Double> U = new HashSet<>();
            Set<Double> A = new HashSet<>();
            Set<Double> S = new HashSet<>();
            Set<Double> H = new HashSet<>();
            Set<Double> T = new HashSet<>();

            //1 check the conditions for the basic NIP terms
            for (Double v : dataset.distinctValues.get(m)) {
                if (v < vgm) {
                    U.add(v);
                }
                if (v > vgm) {
                    A.add(v);
                }
                if (v < vgm) {
                    S.add(v);
                }

                if (v > vgm) {
                    H.add(v);
                }
                if (v == vgm) {
                    T.add(vgm);
                }
            }
            //2- Handling constraints
            Set<Double> newU = new HashSet<>(U);
            Set<Double> newA = new HashSet<>(A);
            Set<Double> newS = new HashSet<>(S);
            Set<Double> newH = new HashSet<>(H);
            Set<Double> newT = new HashSet<>(T);
            if (ConstraintList != null) {

                for (Constraint c : ConstraintList) {
                    if (m == c.getMprim()) {
                        newU = c.filterU(m, c.getMprim(), c.getVprim(), vgm, newU);
                        newA = c.filterA(m, c.getMprim(), c.getVprim(), vgm, newA);
                        newS = c.filterS(m, c.getMprim(), c.getVprim(), vgm, newS);
                        newH = c.filterH(m, c.getMprim(), c.getVprim(), vgm, newH);
                        newT = c.filterT(m, c.getMprim(), c.getVprim(), vgm, newT);
                    }
                }
            }

            if(newU.isEmpty() && newA.isEmpty() && (newS.isEmpty() || newH.isEmpty()) && newT.isEmpty()){
                throw new RuntimeException("NO FEASIBLE SOLUTION: try relax some constraints");
            }
            AttributeSet.add(newU);
            AttributeSet.add(newA);
            AttributeSet.add(newS);
            AttributeSet.add(newH);
            AttributeSet.add(newT);
            Result.add(AttributeSet);
        }

        return Result;
    }



    public double[] tirerIntervalle(Set<Double> U, Set<Double> A, Set<Double> S, Set<Double> H, Set<Double> T, double vgm) {
        /*
        * Method that draws an interval respecting a set of constraints
        * */

        // Convertir les ensembles en listes pour un accès indexé direct
        List<Double> uList = new ArrayList<>(U);
        List<Double> aList = new ArrayList<>(A);
        List<Double> sList = new ArrayList<>(S);
        List<Double> hList = new ArrayList<>(H);
        List<Double> tList = new ArrayList<>(T);

        // Calcul de la taille totale K
        int sizeU = uList.size();
        int sizeA = aList.size();
        int sizeB = sList.size() * hList.size();
        int sizeT = tList.size();
        int K = sizeU + sizeA + sizeB + sizeT;

        // Tirage uniforme d'un indice correspondant a une intervalle (entre 1 et K)
        Random rand = new Random();
        int i = rand.nextInt(K) + 1; // Tire un entier entre 1 et K inclus
        //System.out.println("i: "+i);
        // Vérifier dans quel ensemble se situe l'indice et construire l'intervalle correspondant
        if (i <= sizeU) {
            // Indice dans U
            double v = uList.get(i - 1);
            //System.out.println("U: "+v);
            //System.out.println("\n");
            return new double[] {v,vgm};
        } else if (i <= sizeU + sizeA) {
            // Indice dans A
            double v = aList.get(i - sizeU - 1);
            //System.out.println("A: "+v);
            //System.out.println("\n");
            return new double[] {vgm,v};
        } else if (i <= sizeU + sizeA + sizeB) {
            // Indice dans B (S * H)
            int indexB = i - sizeU - sizeA - 1;
            double s = sList.get(indexB / hList.size());
            double h = hList.get(indexB % hList.size());

            //System.out.println("B: s: "+s+" H: "+h);
            //System.out.println("\n");
            if(!(s < vgm && h > vgm)){
                System.out.println("ERROR: between interval does not work (tirerIntervalle method)");
            }
            return new double[] {s,h};
        } else {
            // Indice dans T
            //int v = tList.get(i - sizeU - sizeA - sizeB - 1);
            //System.out.println("T: "+vgm);
            //System.out.println("\n");
            return new double[] {vgm,vgm};
        }

    }



}
