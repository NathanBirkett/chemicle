import java.util.*;

public class ChemicalMaps {
    HashMap<Integer, String[]> ionMap;
    HashMap<String, Integer> cationMap, anionMap;
    List<String> cationKeys, anionKeys;
    int cationCharge, anionCharge;
    String cation, anion, chemical;
    public ChemicalMaps() {
        ionMap = new HashMap<>();
        ionMap.put(1, new String[]{"H","Li","Na","K"});
        ionMap.put(2,new String[]{"Be","Mg","Ca"});
        ionMap.put(-1, new String[]{"F","Cl","Br"});
        ionMap.put(-2, new String[]{"O","S","Se"});
        ionMap.put(-3, new String[]{"N","P"});

        cationMap = new HashMap<>();
        cationMap.put("H", 1);
        cationMap.put("Li", 1);
        cationMap.put("Na", 1);
        cationMap.put("K", 1);
        cationMap.put("Be", 2);
        cationMap.put("Mg", 2);
        cationMap.put("Ca", 2);
        cationMap.put("OH", 1);

        anionMap = new HashMap<>();
        anionMap.put("F", -1);
        anionMap.put("Cl", -1);
        anionMap.put("Br", -1);
        anionMap.put("O", -2);
        anionMap.put("S", -2);
        anionMap.put("Se", -2);
        anionMap.put("N", -3);
        anionMap.put("P", -3);
    }

    public String generateChemical() {
        cationKeys = new ArrayList<>(cationMap.keySet());
        anionKeys = new ArrayList<>(anionMap.keySet());
        generateAnion();
        generateCation();
        chemical = (cation+anionCharge*-1+anion+cationCharge).replace("1","");
        chemical = (cationCharge == -anionCharge) ? chemical.replace(String.valueOf(cationCharge),"") : chemical;
        System.out.println(chemical);
        return chemical;
    }

    public String generateCation() {
        cation = cationKeys.get((int)(Math.random()*(cationMap.size())));
        cationCharge = cationMap.get(cation);
        return cation;
    }

    public String generateAnion() {
        anion = anionKeys.get((int)(Math.random()*(anionMap.size())));
        anionCharge = anionMap.get(anion);
        return anion;
    }
}
