# Constraint-Based-Sampling — Run Guide

This README explains how to run:
- `CFIPS`
- `CHFIPS`
- the **rejection-based** experiments (`IPRejet` and `RandomIPRejet`)

## 1) Prerequisites

- Java 17+ (or any version compatible with your IDE)
- The dataset folder must exist at the path expected by `Main.java`:
  - `../Normalized_data/benchmark/<DATASET_NAME>_T.dat`

> Example: if you run from the project root, `Main.java` reads `../Normalized_data/benchmark/NT_T.dat`.

## 2) Compile and run

From the project root:

```bash
rm -rf out
mkdir -p out
javac -d out src/ConstraintList/*.java src/ConstraintNewList/*.java src/*.java
java -cp out Main
```

## 3) Select the dataset

In `src/Main.java`, adjust the list:

```java
String [] databases={"NT"};
```

You can include multiple datasets (e.g., `"AP", "NT", "glass"`).

---

## 4) Run CFIPS (constraint-based)

### A. Define a constraint query (`ConstraintList`)
In `src/Main.java`, create a `query`:

```java
ArrayList<Constraint> query = new ArrayList<>();
query.add(new Sup(0, 15));
query.add(new InfEq(2, 18));
query.add(new Inclusion(1, 1));
```

### B. Enable the CFIPS experiment
Still in `src/Main.java`, enable:

```java
e.evolutionTempsCPUConstrainedIP(
    500,
    query,
    db,
    "results/CpuEvolution/ConstrainedIP/" + query.size() + "constraints/"
        + database + "_CPU_Evolution_ConstrainedIP" + query.size() + "constraints.csv"
);
```

Expected output: CSV file in `results/CpuEvolution/ConstrainedIP/...`.

---

## 5) Run CHFIPS

`CHFIPS` uses constraints from the `ConstraintNewList` package.

### A. Add the import in `src/Main.java`
At the top of the file, add:

```java
import ConstraintNewList.*;
```

### B. Define a `ConstraintNewList` query

```java
ArrayList<ConstraintNewList.Constraint> queryNew = new ArrayList<>();
queryNew.add(new ConstraintNewList.InfEq(2, 18));
queryNew.add(new ConstraintNewList.Sup(0, 15));
queryNew.add(new ConstraintNewList.Inclusion(1, 1));
```

### C. Enable CHFIPS evaluation

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

Expected output: text/CSV file in `results/NBDrawsAndCPU/CHFIPS/...`.

---

## 6) Run rejection-based experiments

The related methods are in `Evaluation.java`:
- `evolutionTempsCPUIPRejet(...)` (FIPS sampling + constraint filtering)
- `evolutionTempsCPURandomIPRejet(...)` (random sampling + constraint filtering)

### Important
In `src/Main.java`, some commented calls still use an outdated/incomplete signature.
The correct method signature has **5 parameters**: `(seed, k, query, db, output)`.

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

Expected outputs:
- `results/CpuEvolution/IPRejet/...`
- `results/CpuEvolution/RandomIPRejet/...`

---

## 7) Practical tips

- Start with one dataset (`NT`) and a small query (2–3 constraints).
- Avoid enabling too many experiments at once in the beginning (run one block at a time).
- Ensure output folders exist before running.
- If a query is too restrictive, you may get timeouts or very few accepted patterns.
