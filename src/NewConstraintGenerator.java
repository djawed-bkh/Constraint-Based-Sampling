import ConstraintNewList.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NewConstraintGenerator {
    ArrayList<Constraint> Query;
    ArrayList<Integer> ConstraintPool;
    int ConstraintNumber;
    int GenratorType;
    Database db;


    public NewConstraintGenerator(Database db, int constraintNumber, int genratorType) {
        ConstraintNumber = constraintNumber;
        GenratorType = genratorType;
        this.db = db;
        this.Query = new ArrayList<>();
        ConstraintPool = new ArrayList<Integer>() {{
            add(1); //Inclusion constraint
            add(2); //Exclusion constraint
            add(3); //Sup constraint
            add(4); //Inf constraint
            add(5); //SupEq constraint
            add(6); //InfEq constraint
        }};
    }

    // Distinctvaluesorder =  true ======> Ascending order of distinc values
    // Distinctvaluesorder =  false ======> descending order of distinc values
    public NewConstraintGenerator(Database db, int constraintNumber, int genratorType, boolean Distinctvaluesorder) {


        ConstraintNumber = constraintNumber;
        GenratorType = genratorType;
        this.db = db;
        this.Query = new ArrayList<>();
        ConstraintPool = new ArrayList<Integer>() {{
            add(1); //Inclusion constraint
            add(2); //Exclusion constraint
            add(3); //Sup constraint
            add(4); //Inf constraint
            add(5); //SupEq constraint
            add(6); //InfEq constraint
        }};
    }


    public ArrayList<Constraint> TotalyRandomConstraints(int constraintNumber) {
        long startTime = System.currentTimeMillis();
        long timeout = 60_000; // 1 minute en millisecondes


        boolean IsFeasible = false;
        while (!IsFeasible) {

            if (System.currentTimeMillis() - startTime > timeout) {
                System.out.println("Timeout: no feasible solution found within 1 minute.");
                return new ArrayList<>(); // ou return null, selon ce que tu préfères
            }

            ArrayList<Constraint> TempQuery = new ArrayList<>();
            ArrayList<Integer> AttList = FillAttList();

            for (int i = 0; i < constraintNumber; i++) {
                if (AttList.isEmpty()) {
                    AttList = FillAttList();
                }

                //1- draw uniformly an attribute
                int Attindex = (int) (Math.random() * (AttList.size()));
                AttList.remove(Attindex);

                //2- draw uniformly a constraint
                int Constraintindex = (int) (Math.random() * (ConstraintPool.size()));


                double disctinctValue;
                //3- draw uniformly a distinct value
                if (Constraintindex == 2) { // the constraint is SUP
                    disctinctValue = db.getDistinctValues()
                            .get(Attindex)
                            .get((int) (Math.random() * (db.getDistinctValues().get(Attindex).size() - 1)));
                } else if (Constraintindex == 4) {  //The constraint is Inf
                    double randomVal = Math.random();
                    if (db.getDistinctValues().get(Attindex).size() > 1 && randomVal == 0.0) {
                        disctinctValue = db.getDistinctValues()
                                .get(Attindex)
                                .get(1);
                    } else {
                        disctinctValue = db.getDistinctValues()
                                .get(Attindex)
                                .get((int) (randomVal * (db.getDistinctValues().get(Attindex).size())));
                    }

                } else {
                    disctinctValue = db.getDistinctValues()
                            .get(Attindex)
                            .get((int) (Math.random() * (db.getDistinctValues().get(Attindex).size())));
                }


                //4- Add the constraint to the query
                switch (Constraintindex) {
                    case 0:
                        TempQuery.add(new Inclusion(Attindex, disctinctValue));
                        break;
                    case 1:
                        TempQuery.add(new Exclusion(Attindex, disctinctValue));
                        break;
                    case 2:
                        TempQuery.add(new Sup(Attindex, disctinctValue));
                        break;
                    case 3:
                        TempQuery.add(new Inf(Attindex, disctinctValue));
                        break;
                    case 4:
                        TempQuery.add(new SupEq(Attindex, disctinctValue));
                        break;
                    case 5:
                        TempQuery.add(new InfEq(Attindex, disctinctValue));
                        break;
                }

            }
            IsFeasible = QueryValidation(TempQuery);
            if (IsFeasible) {
                this.Query = TempQuery;
            }
        }
        System.out.println("At least One feasible solution");
        return Query;
    }

    public ArrayList<Constraint> RandomConstraintsOrderedAttributes(int constraintNumber, boolean Order) {

        boolean IsFeasible = false;

        while (!IsFeasible) {
            ArrayList<Constraint> TempQuery = new ArrayList<>();
            List<List<Double>> distinctValues = db.getDistinctValues();

            // Create a list of indices corresponding to attributes
            ArrayList<Integer> AttList = new ArrayList<>();
            for (int i = 0; i < distinctValues.size(); i++) {
                AttList.add(i);  // Add the index for each attribute
            }
            SortAttList(AttList, Order);         // sort in an ascending or descending order of number of distinct values


            for (int i = 0; i < constraintNumber; i++) {
                if (AttList.isEmpty()) {
                    AttList = FillAttList();
                    SortAttList(AttList, Order);
                }

                //1- Select the first attribute in the sorted list
                int attribute = AttList.get(0);

                //2- draw uniformly a constraint
                int Constraintindex = (int) (Math.random() * (ConstraintPool.size()));

                //3- draw uniformly a distinct value
                addQuery(Constraintindex, attribute, TempQuery);
                AttList.remove(0);
            }
            IsFeasible = QueryValidation(TempQuery);
            if (IsFeasible) {
                this.Query = TempQuery;
            }
        }
        return Query;
    }


    public ArrayList<Constraint> UserBasedConstraints(int attSelectionType, int constraintNumber) {
        long startTime = System.currentTimeMillis();
        long timeout = 120_000; // 1 minute en millisecondes
        boolean IsFeasible = false;

        while (!IsFeasible) {
            if (System.currentTimeMillis() - startTime > timeout) {
                System.out.println("Timeout: no feasible solution found within 1 minute.");
                return new ArrayList<>();
            }


            ArrayList<Constraint> TempQuery = new ArrayList<>();
            List<List<Double>> distinctValues = db.getDistinctValues();

            // Create a list of indices corresponding to attributes
            ArrayList<Integer> AttList = new ArrayList<>();
            for (int m = 0; m < this.db.getColumnsNumberNumerical(); m++) {
                AttList.add(m);
            }
            AttributeOrderSelection(attSelectionType, AttList);


            for (int i = 0; i < constraintNumber; i++) {
                if (AttList.isEmpty()) {
                    AttList = FillAttList();
                     // si la liste est vide on recommence
                    AttributeOrderSelection(attSelectionType, AttList);
                }

                //1- Select the first attribute in the sorted list
                int attribut = AttList.get(0);
                //2- draw uniformly a constraint
                int Constraintindex;
                if (distinctValues.get(attribut).size() > 5) {
                    Constraintindex = (int) (Math.random() * (4)) + 2;      //Sup, Inf, SupEq, InfEq
                } else {
                    Constraintindex = (int) (Math.random() * 2);       // Inclusion ou exclusion
                }


                //3- draw uniformly a distinct value
                addQuery(Constraintindex, attribut, TempQuery);
                AttList.remove(0);
            }
            IsFeasible = QueryValidation(TempQuery);
            if (IsFeasible) {
                this.Query = TempQuery;
            }
        }

//        constraintsOrderSelection(Query,constSelectionType);
        return Query;
    }


    public void addQuery(int Constraintindex, int Attindex, ArrayList<Constraint> TempQuery) {
        double disctinctValue = db.getDistinctValues()
                .get(Attindex)
                .get((int) (Math.random() * (db.getDistinctValues().get(Attindex).size())));

        //4- Add the constraint to the query
        switch (Constraintindex) {
            case 0:
                TempQuery.add(new Inclusion(Attindex, disctinctValue));
                break;
            case 1:
                TempQuery.add(new Exclusion(Attindex, disctinctValue));
                break;
            case 2:
                TempQuery.add(new Sup(Attindex, disctinctValue));
                break;
            case 3:
                TempQuery.add(new Inf(Attindex, disctinctValue));
                break;
            case 4:
                TempQuery.add(new SupEq(Attindex, disctinctValue));
                break;
            case 5:
                TempQuery.add(new InfEq(Attindex, disctinctValue));
                break;
        }
    }


    public boolean QueryValidation(ArrayList<Constraint> ConstraintList) {
        // todo: seems to work

        /*
         * This method checks if the Contraint list fixed by the user admits at least one solutionn
         * This is done by applying the NIP function on all the objects.
         * Return 0: if there is at least one feasible solution (when the sum of the objects weight is different from zero)
         * Return -1: Otherwise (when the sum of the objects weights is equal to zero)
         *
         * */
        BigDecimal ObjectstotalWeight = BigDecimal.ZERO;
        for (int g = 0; g < this.db.getObjectNumber(); g++) {
            double objectWeight = 1;
            for (int m = 0; m < this.db.getColumnsNumberNumerical(); m++) {
                double vgm = this.db.getObject(g).get(m); //v_g,m

                if (vgm == -1) {
                    System.out.println("ERROR: We should not be here");
                }

                Set<Double> J = new HashSet<>();
                Set<Double> L = new HashSet<>();

                //1 check the conditions for the basic NIP terms
                for (Double v : this.db.distinctValues.get(m)) {
                    if (v <= vgm) {
                        L.add(v);
                    }
                    if (v >= vgm) {
                        J.add(v);
                    }
                }

                //2- Handling constraints

                Set<Double> newL = new HashSet<>(L);
                Set<Double> newJ = new HashSet<>(J);
                if (ConstraintList != null) {

                    //System.out.println("ConstraintList: "+ConstraintList );
                    for (Constraint c : ConstraintList) {

                        if (m == c.getMprim()) {

                            newL = c.filterL(m, c.getMprim(), c.getVprim(), vgm, newL);
                            newJ = c.filterJ(m, c.getMprim(), c.getVprim(), vgm, newJ);
                        }
                    }
                }
                /*System.out.println("m: "+m);
                System.out.println("newJ: "+newJ);
                System.out.println("newL: "+newL);*/

                double component;
                if (ConstraintList == null) {
                    if(J.size() == 0.0 || L.size() == 0.0 ){
                        //System.out.println("No feasible solution");
                        //return false;
                        component = 0.0;
                    }else{
                        component = J.size() + L.size();
                    }

                } else {
                    if(newJ.size() == 0.0 || newL.size() == 0.0 ){
                       // System.out.println("No feasible solution");
                        //return false;
                        component = 0.0;
                    }else{
                        component = newL.size() * newJ.size();
                    }


                }

                objectWeight = objectWeight * component;
            }

            ObjectstotalWeight = ObjectstotalWeight.add(BigDecimal.valueOf(objectWeight));

        }
        if (ObjectstotalWeight.equals(BigDecimal.valueOf(0.0))) {
            System.out.println("No feasible solution");
            return false;      // there is no feasible solution
        } else {
            System.out.println("At least one feasible solution");
            return true;       // there is a feasible solution
        }
    }


    public ArrayList<Integer> FillAttList() {
        return IntStream.range(0, this.db.getColumnsNumberNumerical())
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public void SortAttList(ArrayList<Integer> mylist, boolean Order) {
        // Create a map to count occurrences of each attribute ID
        HashMap<Integer, Integer> attributeCount = new HashMap<>();
        for (Integer attr : mylist) {
            attributeCount.put(attr, attributeCount.getOrDefault(attr, 0) + 1);
        }

        // Create a list of unique attribute IDs
        List<Integer> uniqueAttributes = new ArrayList<>(attributeCount.keySet());

        // Sort unique attribute IDs based on distinct value sizes in the database
        if (Order) {
            // Ascending order
            uniqueAttributes.sort(Comparator.comparingInt(attr -> db.getDistinctValues().get(attr).size()));
        } else {
            // Descending order
            uniqueAttributes.sort((attr1, attr2) -> Integer.compare(
                    db.getDistinctValues().get(attr2).size(),
                    db.getDistinctValues().get(attr1).size()));
        }

        // Rebuild the sorted list with duplicates preserved
        mylist.clear();
        for (Integer attr : uniqueAttributes) {
            for (int i = 0; i < attributeCount.get(attr); i++) {
                mylist.add(attr);
            }
        }

    }


    public void SortConstraintList(ArrayList<Constraint> mylist, boolean Order) {
        // Create a mapping from mprim to all associated constraints
        HashMap<Integer, List<Constraint>> groupedConstraints = new HashMap<>();
        for (Constraint constraint : mylist) {
            int mprim = constraint.getMprim();
            groupedConstraints.computeIfAbsent(mprim, k -> new ArrayList<>()).add(constraint);
        }

        // Create a list of unique mprim values for sorting
        List<Integer> mprimList = new ArrayList<>(groupedConstraints.keySet());

        // Sort mprimList based on the size of distinct values in db
        if (Order) {
            // Ascending order
            mprimList.sort(Comparator.comparingInt(m -> db.getDistinctValues().get(m).size()));
        } else {
            // Descending order
            mprimList.sort((m1, m2) -> Integer.compare(
                    db.getDistinctValues().get(m2).size(),
                    db.getDistinctValues().get(m1).size()));
        }

        // Rebuild the sorted list of constraints
        mylist.clear();
        for (int mprim : mprimList) {
            mylist.addAll(groupedConstraints.get(mprim));
        }
    }

    public void constraintsOrderSelection(ArrayList<Constraint> constList, int constSelectionType) {
        /*
         * 1- ordre croissant
         * 2- ordre décroissant
         * 3- ordre aléatoire
         * */
        if (constSelectionType == 1) {
            SortConstraintList(constList, true);         // sort ascending
        } else if (constSelectionType == 2) {
            SortConstraintList(constList, false);         // sort descending
        } else {                                                  // shuffle attributes
            Collections.shuffle(constList);
        }


    }


    public void AttributeOrderSelection(int attSelectionType, ArrayList<Integer> AttList) {
        /*
         * Function that sorts the attribute list following a desired order:
         * Increasing or increasing distinct values, or random order
         * */

        if (attSelectionType == 1) {            // 1- ascending
            SortAttList(AttList, true);         // sort in an ascending or descending order of number of distinct values
        } else if (attSelectionType == 2) {     // 2- descending
            SortAttList(AttList, false);         // sort in an descending or descending order of number of distinct values
        } else {                                                  // shuffle attributes
            Collections.shuffle(AttList);
        }
    }


    @Override
    public String toString() {
        return "ConstraintGenerator {" +
                " ConstraintNumber= " + ConstraintNumber +
                " Query= " + Query +
                " }";
    }

}