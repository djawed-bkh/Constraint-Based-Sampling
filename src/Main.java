import ConstraintList.*;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {



        // Initialize the database with empty lists
        Database db = new Database(new ArrayList<>(), new ArrayList<>());

        int seed = 28;

        //String [] databases={"AP","balance-scale", "BK", "cancer", "CH", "diabetes", "glass","heart", "Iris","NT", "LW","sonar", "yacht" };

        //String [] databases={ "cancer", "NT", "glass", "diabetes"}; //"AP",
        //String [] databases={"glass"};
        //String [] databases={"NT", "cancer", "diabetes", "balance-scale"}; // "AP","NT" ,"glass", "diabetes", "cancer"
        String [] databases={"NT"};

        //String [] databases={"AP"};

        for (String database : databases) {

            db.readDB("../Normalized_data/benchmark/" + database + "_T.dat");
            System.out.println("\n");
            System.out.println("Database: " + database);

            //db.showDB();     // print the database in the console
            //db.getDBStatistics();   // prints the database charachteristics
            //db.showDistinctValues();    // prints the distinct values present in each attribute of the database



            //HFIPS hfips = new HFIPS(db, seed);

            //ArrayList<Constraint> query= new ArrayList<>();
            //query.add(new Sup(2,28));
            //query.add(new Inclusion(0,155));

               // CFIPS cfips = new CFIPS(db,query);

            Evaluation e = new Evaluation();
           // e.evolutionTempsCPUFIPS(seed,500,10,db,);



//            for(int m=0; m< db.getColumnsNumberNumerical(); m++){
//                System.out.println(db.getDistinctValues().get(m).size());
//            }



//            System.out.println("Totaly random generator: ");
//            ConstraintGenerator constGenTotalyRandom = new ConstraintGenerator(db,4,1);
//            System.out.println(constGenTotalyRandom);
//
           // System.out.println("Random generator with ordered attributes: ");
            //ConstraintGenerator constGen = new ConstraintGenerator(db,4,2, false);
            //System.out.println(constGen);

            //ArrayList<Constraint> query = constGen.RandomConstraintsOrderedAttributes(1,true);

            //System.out.println("query: "+query);

            //CHFIPS chfips = new CHFIPS(db,query);

            /*for (int i =0; i < 10; i++){
                chfips.drawIP().showIP();
            }*/






            /*System.out.println("First version:");
            IPSampling ips = new IPSampling(db);
            System.out.println("\n");*/

            //System.out.println("Second version constraints: ");

            //ArrayList<Constraint> query= new ArrayList<>();

            //------------------------------ constraints for Cancer:

            //query.add(new Sup(0,40));
            //query.add(new Exclusion(0,49));

            //query.add(new Inclusion(1,90));
            //query.add(new Exclusion(0,107));

            //query.add(new Exclusion(0,47));
            //query.add(new Inclusion(0,50));

            //----------------------------- constraints for heart:
            //query.add(new ConstraintList.Sup("",0,20));
            //query.add(new ConstraintList.Exclusion("",0,30));

            //query.add(new ConstraintList.InfEq("",0,34));
            //query.add(new ConstraintList.Inclusion("",1,1));

            //query.add(new ConstraintList.Exclusion("",0,31));
            //query.add(new ConstraintList.Inclusion("",0,32));


            // --------------------------------Constraints for NT
            //query.add(new ConstraintList.Sup("",0,15));
            //query.add(new ConstraintList.Inf("",0,20));

            //query.add(new ConstraintList.Sup("",2,10));
            //query.add(new ConstraintList.Inf("",2,17));

            //query.add(new ConstraintList.Inclusion("",1,1));
            //query.add(new ConstraintList.Exclusion("",1,0));


            // ---------------------------constraints for balance scale:

            //query.add(new ConstraintList.Sup("",0,2));
            //query.add(new ConstraintList.Exclusion("",0,0));


            //query.add(new ConstraintList.Sup("",1,0));
            //query.add(new ConstraintList.Exclusion("",1,1));

            //query.add(new ConstraintList.Inf("",2,4));
            //query.add(new ConstraintList.Sup("",2,2));


            //query.add(new Sup(0,5));


//            try {
//                constrainedIPSampling constrainedIps = new constrainedIPSampling(db,query); // put null instead of c for no query
//                System.out.println("\n");
//                constrainedIps.drawIP().showIP();
//            } catch (Exception e) {
//                System.err.println(e.getMessage());
//                e.printStackTrace();
//            }

            /*FIPS fips= new FIPS(db);
            for(int i = 0; i < 10; i++){
                IP ip = fips.drawIP();
                ip.showIP();
                //ip.showCoverage();
            }*/
          /*  System.out.println("Distinct values: "+db.getDistinctValues().get(0));
            for (double v : db.getDistinctValues().get(0)){
                System.out.println(v);
                }*/

           /* HIPS hips= new HIPS(db);

            for(int i =0; i < 10; i++){
                IP ip = hips.drawIP();
                ip.showIP();
                ip.showCoverage();
            }*/

            /*for(int i = 0; i < 10; i++){
                IP ip = fips.drawIP();
                ip.showIP();
                //ip.showCoverage();
            }*/








          /*  Evaluation e = new Evaluation();

            System.out.println("-----------FIPS:");
            System.out.println(e.fipsnombreMotifavecAttnonContraint(db,seed,500,10));

            System.out.println("-----------HIPS:");
            System.out.println(e.hfipsnombreMotifavecAttnonContraint(db,seed,500,10));

            System.out.println("-----------Uniform:");
            System.out.println(e.aleanombreMotifavecAttnonContraint(db,seed,500,10));*/





            //System.out.println("-----------FIPS:");
            //e.DensityEvaluationFIPS(db,seed,1000);

            //System.out.println("-----------HIPS:");
            //e.DensityEvaluationHIPS(db,seed,1000);

            //System.out.println("-----------Uniform:");
            //e.DensityEvaluationUniform(db,seed,1000);



            //System.out.println("-----------FIPS:");
            //e.meanFrequencyAndVolumeFIPS(db,seed,1000);

            //System.out.println("\n-----------HIPS:");
            //e.meanFrequencyAndVolumeHIPS(db,seed,1000);


            //e.expeVolumesurFrequence(db, seed,500, "results/ExpeDKE_BRUNO/",database);
            //e.expeTableau_comparatif_frequence(db,seed,500);

            // A executer
            //e.expeDensity(db, seed,500, "results/ExpeDKE_BRUNO/",database);

            //e.volumeAndFrequencyFIPS(500, 10, db);
            //e.volumeAndFrequencyHIPS(500, 10, db);
            //e.volumeAndFrequencyUniform(500, 10, db);

            //e.evolutionVolumeTimesFrequencyHIPS(seed,500,10,db,"results/VolumeTimesFrequency/HIPS/"+database+"_volume_Evolution_HIPS.csv");
            //e.evolutionVolumeTimesFrequencyFIPS(seed,500,10,db,"results/VolumeTimesFrequency/FIPS/"+database+"_volume_Evolution_FIPS.csv");
            //e.evolutionVolumeTimesFrequencyRandIP(seed,500,10,db,"results/VolumeTimesFrequency/UNIFORM/"+database+"_volume_Evolution_Random.csv");
              //e.DensityEvaluationFIPS(db,10,10);





             //NewConstraintGenerator constGen = new NewConstraintGenerator(db, 10, 1);

            /* ArrayList<Constraint> query = new ArrayList<>();

            query.add(new InfEq(0,98.9));
            query.add(new SupEq(2,66));
            query.add(new Inclusion(1,2));*/


            //System.out.println("Resultat de la query:" + constGen.QueryValidation(query));;


            //System.out.println("******************************* Random query ******************************* \n");
      //      ArrayList<Constraint> query = constGen.TotalyRandomConstraints(7);

            // 1 --> ascending order of distinct values
            //2 ---> descending order of distinct values
            //System.out.println("******************************* User based descending query ******************************* \n");


            //ArrayList<Constraint> query = constGen.UserBasedConstraints(2,10);

            //ArrayList<Constraint> query = new ArrayList<>();



            //-------------------------------------------------------- AP---------------------------------------

                // UserBased Random
//            query.add(new InfEq(4,133));
//            query.add(new Sup(2,47));
//            query.add(new SupEq(4,42));
//            query.add(new Sup(1,25));
//            query.add(new Inf(0,131));
//            query.add(new Sup(0,49));
//            query.add(new SupEq(3,1));
//            query.add(new SupEq(2,41));
//            query.add(new SupEq(1,37));
//            query.add(new SupEq(3,88));


            // UserBased croissant---------------------DONE
            /*query.add(new SupEq(4,42));
            query.add(new InfEq(4,133));
            query.add(new Sup(0,49));
            query.add(new Inf(0,131));
            query.add(new Sup(1,25));
            query.add(new SupEq(1,37));
            query.add(new SupEq(2,41));
            query.add(new Sup(2,47));
            query.add(new SupEq(3,1));
            query.add(new SupEq(3,88));*/



            // UserBased DÉcroissant--------------------------DONE
            /*query.add(new Sup(0,49));
            query.add(new Inf(0,131));
            query.add(new Sup(1,25));
            query.add(new SupEq(1,37));
            query.add(new SupEq(2,41));
            query.add(new Sup(2,47));
            query.add(new SupEq(3,1));
            query.add(new SupEq(3,88));
            query.add(new SupEq(4,42));
            query.add(new InfEq(4,133));*/


            //-------------------------------------------------------- cancer---------------------------------------
            // Userbased aleatoire ---------------------DONE


//            query.add(new InfEq(4,78));
//            query.add(new Inf(6,20));
//            query.add(new InfEq(5,64));
//            query.add(new Inf(0,30));
//            query.add(new InfEq(2,49));
//            query.add(new SupEq(8,49));
//            query.add(new Inf(3,27));
//            query.add(new Sup(7,59));
//            query.add(new SupEq(1,13));
//            query.add(new SupEq(0,2));


            // Userbased croissant ---------------------DONE
          /*  query.add(new InfEq(2,49));
            query.add(new SupEq(0,2));
            query.add(new Inf(0,30));
            query.add(new SupEq(1,13));
            query.add(new Inf(3,27));
            query.add(new SupEq(8,49));
            query.add(new Inf(6,20));
            query.add(new InfEq(4,78));
            query.add(new InfEq(5,64));
            query.add(new Sup(7,59));*/


            // Userbased DÉCROISSANT

           /* query.add(new InfEq(4,78));
            query.add(new InfEq(5,64));
            query.add(new Sup(7,59));
            query.add(new Inf(6,20));
            query.add(new Inf(3,27));
            query.add(new SupEq(8,49));
            query.add(new SupEq(1,13));
            query.add(new SupEq(0,2));
            query.add(new Inf(0,30));
            query.add(new InfEq(2,49));*/

            //-------------------------------------------------------- DIABETES---------------------------------------

            // userbased aleatoire

//            query.add(new SupEq(1,81));
//            query.add(new Inf(5,180));
//            query.add(new Inf(6,458));
//            query.add(new Inf(0,7));
//            query.add(new Sup(1,116));
//            query.add(new InfEq(0,4));
//            query.add(new SupEq(3,25));
//            query.add(new SupEq(4,75));
//            query.add(new InfEq(7,28));
//            query.add(new Inf(2,33));



            // Userbased Croissant
            /*
            query.add(new Inf(0,7));
            query.add(new InfEq(0,4));
            query.add(new Inf(2,33));
            query.add(new SupEq(3,25));
            query.add(new InfEq(7,28));
            query.add(new Sup(1,116));
            query.add(new SupEq(1,81));
            query.add(new SupEq(4,75));
            query.add(new Inf(5,180));
            query.add(new Inf(6,458));
*/


            // Userbased DECroissant

         /*   query.add(new Inf(6,458));
            query.add(new Inf(5,180));
            query.add(new SupEq(4,75));
            query.add(new Sup(1,116));
            query.add(new SupEq(1,81));
            query.add(new InfEq(7,28));
            query.add(new SupEq(3,25));
            query.add(new Inf(2,33));
            query.add(new Inf(0,7));
            query.add(new InfEq(0,4));*/


            //-------------------------------------------------------- glass---------------------------------------

            // userbased aleatoire

//            query.add(new SupEq(2,4));
//            query.add(new Inf(7,31));
//            query.add(new Inf(0,71));
//            query.add(new Sup(5,9));
//            query.add(new InfEq(0,79));
//            query.add(new Inf(8,6));
//            query.add(new SupEq(1,57));
//            query.add(new SupEq(4,66));
//            query.add(new Inf(6,86));
//            query.add(new Inf(3,64));


                // Userbased Croissant
         /*   query.add(new Inf(3,65));
            query.add(new Inf(7,31));
            query.add(new Sup(5,9));
            query.add(new SupEq(2,4));
            query.add(new Inf(3,64));
            query.add(new SupEq(4,66));
            query.add(new SupEq(1,57));
            query.add(new Inf(6,86));
            query.add(new InfEq(0,79));
            query.add(new Inf(0,71));*/

            //CHFIPS chfips  = new CHFIPS(seed,db, query);




            // Userbased DECroissant

            /*query.add(new InfEq(0,79));
            query.add(new Inf(0,71));
            query.add(new Inf(6,86));
            query.add(new SupEq(1,57));
            query.add(new SupEq(4,66));
            query.add(new Inf(3,64));
            query.add(new SupEq(2,4));
            query.add(new Sup(5,9));
            query.add(new Inf(7,31));
            query.add(new Inf(3,64));*/





            //-------------------------------------------------------- NT---------------------------------------

            // userbased aleatoire

//            query.add(new Exclusion(0,0));
//            query.add(new InfEq(2,18));
//            query.add(new Inclusion(1,1));
//            query.add(new Inf(0,11));
//            query.add(new SupEq(2,5));
//            query.add(new InfEq(0,29));
//            query.add(new InfEq(0,19));
//            query.add(new SupEq(2,13));
//            query.add(new Inf(0,31));
//            query.add(new Inclusion(1,1));



            // Userbased Croissant
          /*  query.add(new Inclusion(1,0));
            query.add(new Inclusion(1,1));
            query.add(new Inclusion(1,1));
            query.add(new SupEq(2,13));
            query.add(new SupEq(2,5));
            query.add(new InfEq(2,18));
            query.add(new Inf(0,31));
            query.add(new InfEq(0,19));
            query.add(new Inf(0,11));
            query.add(new InfEq(0,29));*/




            // Userbased DECroissant

          /*  query.add(new Inf(0,31));
            query.add(new InfEq(0,19));
            query.add(new Inf(0,11));
            query.add(new InfEq(0,29));
            query.add(new SupEq(2,13));
            query.add(new SupEq(2,5));
            query.add(new InfEq(2,18));
            query.add(new Inclusion(1,0));
            query.add(new Inclusion(1,1));
            query.add(new Inclusion(1,1));
*/


//            if(!query.isEmpty()){
//
//              /* System.out.println("-----------------CFIPS METHOD----------------- ");
//                e.evaluateOptimizedCFIPSNumberOfDraws(seed,db, "results/NBDrawsAndCPU/CFIPS/UserbasedCroissant/" + database + "_CFIPS_CPU_DrawNumberEvaluation.txt", 100, 10, query);
//                System.out.println("-----------------End CFIPS METHOD----------------- ");*/
//
//
//                System.out.println("-----------------CHFIPS METHOD ----------------- ");
//               // e.evaluationpreprocessingTime(new CHFIPS(seed,db,query), query);
//                e.evaluateCHFIPSNumberOfDraws(seed,db, "results/NBDrawsAndCPU/CHFIPS/UserbasedAleatoire/" + database + "_CHFIPS_CPU_DrawNumberEvaluation.txt", 100, 10, query);
//                System.out.println("-----------------End CHFIPS Method----------------- ");
//
//                /*System.out.println("-----------------FIPS METHOD ----------------- ");
//                //e.evaluationpreprocessingTime(new CHFIPS(seed,db,query), query);
//                e.evaluateFIPSNumberOfDraws(seed,db, "results/NBDrawsAndCPU/FIPS/UserbasedCroissant/" + database + "_FIPS_CPU_DrawNumberEvaluation.txt", 100, 10, query);
//                System.out.println("-----------------FIPS Method----------------- ");*/
//
//                 System.out.println("-----------------HFIPS METHOD----------------- ");
//                 e.evaluateHFIPSNumberOfDraws(seed,db, "results/NBDrawsAndCPU/HFIPS/UserbasedAleatoire/" + database + "_HFIPS_CPU_DrawNumberEvaluation.txt", 100, 10, query);
//                 System.out.println("-----------------End HFIPS METHOD----------------- ");
//
//
//               /* System.out.println("-----------------Uniform METHOD----------------- ");
//                e.evaluateRandomNumberOfDraws(seed,db, "results/NBDrawsAndCPU/RANDOM/UserbasedCroissant/" + database + "_HFIPS_CPU_DrawNumberEvaluation.txt", 100, 10, query);
//                System.out.println("-----------------End Uniform METHOD----------------- ");*/
//
//            }



          /*  System.out.println("-----------------CHFIPS METHOD ----------------- ");
            e.evaluationpreprocessingTime(new CHFIPS(seed,db,query), query);
            e.evaluateCHFIPSNumberOfDraws(seed,db, "results/NBDrawsAndCPU/CHFIPS/RandomGenerator/" + database + "_CHFIPS_CPU_DrawNumberEvaluation.txt", 100, 10, query);
            System.out.println("-----------------End CHFIPS Method----------------- ");
*/

           // System.out.println("-----------------HFIPS METHOD----------------- ");
            //e.evaluateHFIPSNumberOfDraws(seed,db, "results/NBDrawsAndCPU/HFIPS/RandomGenerator/" + database + "_HFIPS_CPU_DrawNumberEvaluation.txt", 100, 10, query);
            // System.out.println("-----------------End HFIPS METHOD----------------- ");






           /* ConstraintGenerator constGen = new ConstraintGenerator(db, 10, 1);
            System.out.println("******************************* Random query ******************************* \n");
            ArrayList<Constraint> query = constGen.TotalyRandomConstraints(10);

                        System.out.println("-----------------CFIPS METHOD ----------------- ");

            e.evaluateCFIPSNumberOfDraws(db, "results/NBDrawsAndCPU/test/CFIPS/RandomGenerator/" + database + "_CHFIPS_CPU_DrawNumberEvaluation.txt", 100, 10, query);
            System.out.println("-----------------End CFIPS Method----------------- ");


            System.out.println("-----------------FIPS METHOD----------------- ");
            e.evaluateFIPSNumberOfDraws(seed,db, "results/NBDrawsAndCPU/test/FIPS/RandomGenerator/" + database + "_HFIPS_CPU_DrawNumberEvaluation.txt", 100, 10, query);
            System.out.println("-----------------End FIPS METHOD----------------- ");*/




















            /*System.out.println("******************************* Ascending order of attributes query ******************************* \n");
            ArrayList<Constraint> query = constGen.TotalyRandomConstraints(10);*/



//
//            System.out.println("-----------------CFIPS METHOD ----------------- ");
//            e.evaluationpreprocessingTime(new CFIPS(db,query), query);
//            e.evaluateCFIPSNumberOfDraws(db, "results/NBDrawsAndCPU/CFIPS/RandomGenerator/" + database + "_CFIPS_CPU_DrawNumberEvaluation.txt", 100, 10, 1, 10, query);
//            System.out.println("-----------------End CFIPS Method----------------- ");
//
//
//            System.out.println("-----------------FIPS METHOD----------------- ");
//            e.evaluateFIPSNumberOfDraws(db, "results/NBDrawsAndCPU/FIPS/RandomGenerator/" + database + "_FIPS_CPU_DrawNumberEvaluation.txt", 100, 10, 1, 10, query);
//            System.out.println("-----------------End FIPS METHOD----------------- ");
//
//
//            System.out.println("-----------------RANDOM METHOD----------------- ");
//            e.evaluateRandomNumberOfDraws(db, "results/NBDrawsAndCPU/RANDOM/RandomGenerator/" + database + "_RANDOM_CPU_DrawNumberEvaluation.txt", 100, 10, 1, 10, query);
//            System.out.println("-----------------END RANDOM  METHOD----------------- ");
//
//            System.out.println("******************************* End of random query *******************************\n ");



//            ArrayList<Constraint> initialQuery = constGen.UserBasedConstraints(10);  //generation de 10 contraintes
//
//            System.out.println(initialQuery);
//
//
//            System.out.println("******************************* User Based query (Ordre croissant) ******************************* \n");
//
//            constGen.constraintsOrderSelection(initialQuery,1); // Ordre croissant
//            System.out.println("Ordre croissant: "+initialQuery);
//
//            System.out.println("-----------------CFIPS METHOD ----------------- ");
//            e.evaluationpreprocessingTime(new CFIPS(db, initialQuery), initialQuery);
//            e.evaluateCFIPSNumberOfDraws(db, "results/NBDrawsAndCPU/CFIPS/UserbasedCroissant/" + database + "_CFIPS_CPU_DrawNumberEvaluation.txt", 100, 10, initialQuery);
//            System.out.println("-----------------End CFIPS Method----------------- ");
//
//
//            System.out.println("-----------------FIPS METHOD----------------- ");
//            e.evaluateFIPSNumberOfDraws(db, "results/NBDrawsAndCPU/FIPS/UserbasedCroissant/" + database + "_FIPS_CPU_DrawNumberEvaluation.txt", 100, 10, initialQuery);
//            System.out.println("-----------------End FIPS METHOD----------------- ");
//
//
//            System.out.println("-----------------RANDOM METHOD----------------- ");
//            e.evaluateRandomNumberOfDraws(db, "results/NBDrawsAndCPU/RANDOM/UserbasedCroissant/" + database + "_RANDOM_CPU_DrawNumberEvaluation.txt", 100, 10,  initialQuery);
//            System.out.println("-----------------END RANDOM  METHOD----------------- ");
//
//
//            System.out.println("******************************* End of  query  (Ordre croissant) *******************************\n ");
//
//            System.out.println("******************************* User Based query (Ordre décroissant) ******************************* \n");
//              constGen.constraintsOrderSelection(initialQuery,2); //Ordre DÉcroissant
//             System.out.println("Ordre décroissant: "+initialQuery);
//
//            System.out.println("-----------------CFIPS METHOD ----------------- ");
//            e.evaluationpreprocessingTime(new CFIPS(db, initialQuery), initialQuery);
//            e.evaluateCFIPSNumberOfDraws(db, "results/NBDrawsAndCPU/CFIPS/UserbasedDeCroissant/" + database + "_CFIPS_CPU_DrawNumberEvaluation.txt", 100, 10,  initialQuery);
//            System.out.println("-----------------End CFIPS Method----------------- ");
//
//
//            System.out.println("-----------------FIPS METHOD----------------- ");
//            e.evaluateFIPSNumberOfDraws(db, "results/NBDrawsAndCPU/FIPS/UserbasedDeCroissant/" + database + "_FIPS_CPU_DrawNumberEvaluation.txt", 100, 10,  initialQuery);
//            System.out.println("-----------------End FIPS METHOD----------------- ");
//
//
//            System.out.println("-----------------RANDOM METHOD----------------- ");
//            e.evaluateRandomNumberOfDraws(db, "results/NBDrawsAndCPU/RANDOM/UserbasedDeCroissant/" + database + "_RANDOM_CPU_DrawNumberEvaluation.txt", 100, 10,  initialQuery);
//            System.out.println("-----------------END RANDOM  METHOD----------------- ");
//
//
//
//
//            System.out.println(" ******************************* End of  query  (Ordre décroissant) ******************************* \n ");
//
//            System.out.println("******************************* User Based query (Ordre Aléatoire d'attributs)******************************* \n");
//            constGen.constraintsOrderSelection(initialQuery,3); //Ordre aléatoire
//            System.out.println("Ordre aléatoire: "+initialQuery);
//
//            System.out.println("-----------------CFIPS METHOD ----------------- ");
//            e.evaluationpreprocessingTime(new CFIPS(db, initialQuery), initialQuery);
//            e.evaluateCFIPSNumberOfDraws(db, "results/NBDrawsAndCPU/CFIPS/UserbasedAleatoire/" + database + "_CFIPS_CPU_DrawNumberEvaluation.txt", 100, 10,  initialQuery);
//            System.out.println("-----------------End CFIPS Method----------------- ");
//
//
//            System.out.println("-----------------FIPS METHOD----------------- ");
//            e.evaluateFIPSNumberOfDraws(db, "results/NBDrawsAndCPU/FIPS/UserbasedAleatoire/" + database + "_FIPS_CPU_DrawNumberEvaluation.txt", 100, 10,  initialQuery);
//            System.out.println("-----------------End FIPS METHOD----------------- ");
//
//
//            System.out.println("-----------------RANDOM METHOD----------------- ");
//            e.evaluateRandomNumberOfDraws(db, "results/NBDrawsAndCPU/RANDOM/UserbasedAleatoire/" + database + "_RANDOM_CPU_DrawNumberEvaluation.txt", 100, 10,  initialQuery);
//            System.out.println("-----------------END RANDOM  METHOD----------------- ");
//
//            System.out.println("*******************************End of query  (Ordre Aléatoire d'attributs) ******************************* \n ");
//





            //e.drawFipsRespectingConstraints(query,1000,10,db);
            //e.drawRandomIPRespectingConstraints(query,1000,10,db);
            //e.drawConstrainedFipsRespectingConstraints(query,1000,10,db);

            //e.constrainedPlausibilityIP(10000,10,db,0.4f,0.6f);
            //e.constrainedPlausibilityRandomIP(10000,10,db,0.40f,0.50f);
            //e.constrainedPlausibilityHIPS(10000,10,db,0.45f,0.55f);


            //System.out.println("Totaly random IP: Percentage of empty coverage "+e.EvaluationEmptyCoverage(10000,db));

            //System.out.println("Plausibilité for Frequency IP: "+e.PlausibilityFrequencyIP(10000,10,db));
            //System.out.println("Plausibilité for Frequency Random IP: "+e.PlausibilityFrequencyRandIP(10000,10,db));


            //System.out.println("Plausibilité for density IP: "+e.PlausibilityDensityIP(10000,10,db));
            //System.out.println("Plausibilité for density Random IP: "+e.PlausibilityDensityRandomIP(10000,10,db));

            //System.out.println("FIPS: Diversité soulet: "+e.eqClassDiversityIP(100000, db));
            //System.out.println("HIPS: Diversité soulet: "+e.eqClassDiversityHIPS(100000, db));
            //System.out.println("UNIFORM: Diversité soulet: "+e.eqClassDiversityRandomIP(100000, db));

            //e.evolutionFrequenceFIPS(seed,500,db,"results/newFrequency/FIPS/"+database+"_frequency_Evolution_FIPS.csv");
            //e.evolutionFrequenceRandomIP(seed,500,db,"results/newFrequency/RandomIP/"+database+"_frequency_Evolution_RandomIP.csv");
            //e.evolutionFrequenceHIPS(seed,500,db,"results/newFrequency/HIPS/"+database+"_frequency_Evolution_HIPS.csv");

            //e.evolutionTempsCPUClosedIP(500, db,"results/CpuEvolution/Closed_IP/"+ database +"_CPU_Evolution_Closed_IP.csv");
            //e.evolutionTempsCPURandomClosedIP(500, db,"results/CpuEvolution/Closed_Random_IP/"+ database +"_CPU_Evolution_Random_Closed_IP.csv");

            e.evolutionTempsCPUFIPS(seed,500, 10,db,"results/CpuEvolution/FIPS/"+ database +"_CPU_Evolution_FIPS.csv");
            e.evolutionTempsCPUHIPS(seed,500, 10,db,"results/CpuEvolution/HIPS/"+ database +"_CPU_Evolution_HIPS.csv");
            e.evolutionTempsCPUUniform(seed,500, 10,db,"results/CpuEvolution/RandomIP/"+ database +"_CPU_Evolution_Random_IP.csv");


            //e.evolutionTempsCPUConstrainedIP(500,query,db,"results/CpuEvolution/ConstrainedIP/"+query.size()+"constraints/"+ database +"_CPU_Evolution_ConstrainedIP"+query.size()+"constraints.csv");
            //e.evolutionTempsCPURandomIPRejet(500,query,db,"results/CpuEvolution/RandomIPRejet/"+query.size()+"constraints/"+ database +"_CPU_Evolution_RandomIPRejet"+query.size()+"constraints.csv");
            //e.evolutionTempsCPUIPRejet(500,query,db,"results/CpuEvolution/IPRejet/"+query.size()+"constraints/"+ database +"_CPU_Evolution_IPRejet"+query.size()+"constraints.csv");

            //e.jaccardCDFIP(500,db,"results/JaccardCDF/FIPS/"+ database +"_JaccardCDF_FIPS.csv" );
            //e.jaccardCDFHIPS(500,db,"results/JaccardCDF/HIPS/"+ database +"_JaccardCDF_HIPS.csv" );
            //e.jaccardCDFRandomIP(500,db,"results/JaccardCDF/RandomIP/"+ database +"_JaccardCDF_Random_IP.csv" );

            //e.evolutionVolumeIP(500,db,"results/Volume/IP/"+ database +"_Volume_IP.csv" );
            //e.evolutionVolumeRandIP(500,db,"results/Volume/RandIP/"+ database +"_Volume_Rand_IP.csv" );

            //e.TempsCPUIP(1000,db);
            //e.TempsCPURandomIP(100000,db);




        }


    }

}