# Constraint-Based-Sampling — Guide de lancement

Ce README explique comment lancer :
- `CFIPS`
- `CHFIPS`
- les expérimentations **avec rejet** (`IPRejet` et `RandomIPRejet`)

## 1) Prérequis

- Java 17+ (ou version compatible avec votre IDE)
- Le dossier de données doit exister au chemin attendu par `Main.java` :
  - `../Normalized_data/benchmark/<NOM_BDD>_T.dat`

> Exemple : si vous exécutez depuis la racine du projet, `Main.java` lit `../Normalized_data/benchmark/NT_T.dat`.

## 2) Compiler et exécuter

Depuis la racine du projet :

```bash
rm -rf out
mkdir -p out
javac -d out src/ConstraintList/*.java src/ConstraintNewList/*.java src/*.java
java -cp out Main
```

## 3) Sélectionner la base de données

Dans `src/Main.java`, ajuster la liste :

```java
String [] databases={"NT"};
```

Vous pouvez mettre plusieurs bases (ex. `"AP", "NT", "glass"`).

---

## 4) Lancer CFIPS (construit sous contraintes)

### A. Définir une requête de contraintes (`ConstraintList`)
Dans `src/Main.java`, créer une `query` :

```java
ArrayList<Constraint> query = new ArrayList<>();
query.add(new Sup(0, 15));
query.add(new InfEq(2, 18));
query.add(new Inclusion(1, 1));
```

### B. Activer l’expérimentation CFIPS
Toujours dans `src/Main.java`, activer :

```java
e.evolutionTempsCPUConstrainedIP(
    500,
    query,
    db,
    "results/CpuEvolution/ConstrainedIP/" + query.size() + "constraints/"
        + database + "_CPU_Evolution_ConstrainedIP" + query.size() + "constraints.csv"
);
```

Résultat attendu : CSV dans `results/CpuEvolution/ConstrainedIP/...`.

---

## 5) Lancer CHFIPS

`CHFIPS` utilise les contraintes du package `ConstraintNewList`.

### A. Ajouter l’import dans `src/Main.java`
En haut du fichier, ajouter :

```java
import ConstraintNewList.*;
```

### B. Définir une requête `ConstraintNewList`

```java
ArrayList<ConstraintNewList.Constraint> queryNew = new ArrayList<>();
queryNew.add(new ConstraintNewList.InfEq(2, 18));
queryNew.add(new ConstraintNewList.Sup(0, 15));
queryNew.add(new ConstraintNewList.Inclusion(1, 1));
```

### C. Activer l’évaluation CHFIPS

```java
e.evaluateCHFIPSNumberOfDraws(
    seed,
    db,
    "results/NBDrawsAndCPU/CHFIPS/" + database + "_CHFIPS_CPU_DrawNumberEvaluation.txt",
    100,
    10,
    queryNew
);
```

Résultat attendu : fichier texte/CSV dans `results/NBDrawsAndCPU/CHFIPS/...`.

---

## 6) Lancer les expérimentations avec rejet

Les deux méthodes concernées sont dans `Evaluation.java` :
- `evolutionTempsCPUIPRejet(...)` (échantillonnage FIPS + filtre de contraintes)
- `evolutionTempsCPURandomIPRejet(...)` (échantillonnage random + filtre de contraintes)

### Important
Dans `src/Main.java`, les appels commentés existants ont une signature incomplète.
Les bonnes signatures prennent **5 paramètres** : `(seed, k, query, db, output)`.

### A. IPRejet

```java
e.evolutionTempsCPUIPRejet(
    seed,
    500,
    query,
    db,
    "results/CpuEvolution/IPRejet/" + query.size() + "constraints/"
        + database + "_CPU_Evolution_IPRejet" + query.size() + "constraints.csv"
);
```

### B. RandomIPRejet

```java
e.evolutionTempsCPURandomIPRejet(
    seed,
    500,
    query,
    db,
    "results/CpuEvolution/RandomIPRejet/" + query.size() + "constraints/"
        + database + "_CPU_Evolution_RandomIPRejet" + query.size() + "constraints.csv"
);
```

Résultats attendus :
- `results/CpuEvolution/IPRejet/...`
- `results/CpuEvolution/RandomIPRejet/...`

---

## 7) Conseils pratiques

- Démarrer avec une base (`NT`) et peu de contraintes (2–3).
- Éviter d’activer trop d’expériences en même temps au début (un bloc à la fois).
- Vérifier que les dossiers de sortie existent, sinon les créer avant exécution.
- Si une requête est trop restrictive, vous pouvez obtenir un timeout ou très peu de motifs acceptés.
