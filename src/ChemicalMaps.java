import java.util.*;
import java.util.concurrent.ExecutionException;

public class ChemicalMaps {
    HashMap<Integer, String[]> ionMap;
    HashMap<String, Integer> cationMap, anionMap;
    List<String> cationKeys, anionKeys;
    int cationCharge, anionCharge;
    String cation, anion, chemical;
    ArrayList<String> chemicalTokens = new ArrayList<>();
    public ChemicalMaps() {
        ionMap = new HashMap<>();
        ionMap.put(1, new String[]{"H","Li","Na","K","Rb","Cs","Fr"});
        ionMap.put(2,new String[]{"Be","Mg","Ca","Sr","Ba","Ra"});
        ionMap.put(-1, new String[]{"F","Cl","Br","I"});
        ionMap.put(-2, new String[]{"O","S","Se","Te"});
        ionMap.put(-3, new String[]{"N","P"});

        cationMap = new HashMap<>();
        cationMap.put("H", 1);
        cationMap.put("Li", 1);
        cationMap.put("Na", 1);
        cationMap.put("K", 1);
        cationMap.put("Rb", 1);
        cationMap.put("Cs", 1);
        cationMap.put("Fr", 1);
        cationMap.put("Cu", 1);
        cationMap.put("Ag", 1);
        cationMap.put("Au", 1);
        cationMap.put("Hg", 1);
        cationMap.put("Tl", 1);
        cationMap.put("(NH4)", 1);
        cationMap.put("(H3O)", 1);
        cationMap.put("Be", 2);
        cationMap.put("Mg", 2);
        cationMap.put("Ca", 2);
        cationMap.put("Sr", 2);
        cationMap.put("Ba", 2);
        cationMap.put("Ra", 2);
        cationMap.put("Ti", 2);
        cationMap.put("V", 2);
        cationMap.put("Cr", 2);
        cationMap.put("Mn", 2);
        cationMap.put("Fe", 2);
        cationMap.put("Co", 2);
        cationMap.put("Ni", 2);
        cationMap.put("Pd", 2);
        cationMap.put("Pt", 2);
        cationMap.put("Cu", 2);
        cationMap.put("Zn", 2);
        cationMap.put("Cd", 2);
        cationMap.put("Hg", 2);
        cationMap.put("Sn", 2);
        cationMap.put("Pb", 2);
        cationMap.put("(Hg2)", 2);
        cationMap.put("Sc", 3);
        cationMap.put("Y", 3);
        cationMap.put("La", 3);
        cationMap.put("Ac", 3);
        cationMap.put("V", 3);
        cationMap.put("Nb", 3);
        cationMap.put("Cr", 3);
        cationMap.put("Mn", 3);
        cationMap.put("Fe", 3);
        cationMap.put("Ru", 3);
        cationMap.put("Os", 3);
        cationMap.put("Co", 3);
        cationMap.put("Rh", 3);
        cationMap.put("Ir", 3);
        cationMap.put("Ni", 3);
        cationMap.put("Au", 3);
        cationMap.put("Al", 3);
        cationMap.put("Ga", 3);
        cationMap.put("In", 3);
        cationMap.put("Tl", 3);
        cationMap.put("Bi", 3);
        cationMap.put("Ti", 4);
        cationMap.put("Zr", 4);
        cationMap.put("Hf", 4);
        cationMap.put("V", 4);
        cationMap.put("Mn", 4);
        cationMap.put("Tc", 4);
        cationMap.put("Re", 4);
        cationMap.put("Os", 4);
        cationMap.put("Ir", 4);
        cationMap.put("Pd", 4);
        cationMap.put("Pt", 4);
        cationMap.put("Sn", 4);
        cationMap.put("Pb", 4);
        cationMap.put("V", 5);
        cationMap.put("Nb", 5);
        cationMap.put("Ta", 5);
        cationMap.put("Bi", 5);
        cationMap.put("Cr", 6);
        cationMap.put("Mo", 6);
        cationMap.put("W", 6);
        cationMap.put("Tc", 6);
        cationMap.put("Re", 6);
        cationMap.put("Re", 6);
        cationMap.put("Mn", 7);
        cationMap.put("Tc", 7);
        cationMap.put("Re", 7);

        anionMap = new HashMap<>();
        anionMap.put("F", -1);
        anionMap.put("Cl", -1);
        anionMap.put("Br", -1);
        anionMap.put("I", -1);
        anionMap.put("(ClO)", -1);
        anionMap.put("(ClO2)", -1);
        anionMap.put("(ClO3)", -1);
        anionMap.put("(ClO4)", -1);
        anionMap.put("(NO2)", -1);
        anionMap.put("(NO3)", -1);
        anionMap.put("(OH)", -1);
        anionMap.put("(CN)", -1);
        anionMap.put("(C2H3O2)", -1);
        anionMap.put("(HCO3)", -1);
        anionMap.put("(MnO4)", -1);
        anionMap.put("(SCN)", -1);
        anionMap.put("(HSO4)", -1);
        anionMap.put("O", -2);
        anionMap.put("S", -2);
        anionMap.put("Se", -2);
        anionMap.put("Te", -2);
        anionMap.put("(SO3)", -2);
        anionMap.put("(SO4)", -2);
        anionMap.put("(CO3)", -2);
        anionMap.put("(C2O4)", -2);
        anionMap.put("(CrO4)", -2);
        anionMap.put("(Cr2O7)", -2);
        anionMap.put("(O2)", -2);
        anionMap.put("(S2O3)", -2);
        anionMap.put("N", -3);
        anionMap.put("P", -3);
        anionMap.put("(PO4)", -3);
    }

    public String generateChemical() {
        cationKeys = new ArrayList<>(cationMap.keySet());
        anionKeys = new ArrayList<>(anionMap.keySet());
        System.out.println(anionKeys);
        generateCation();
        generateAnion();
        chemical = (cation+anionCharge*-1+anion+cationCharge).replace("1","");
        chemical = (cationCharge == -anionCharge) ? chemical.replace(String.valueOf(cationCharge),"") : chemical;
        System.out.println(chemical);
        chemicalTokens = dissect(chemical);
        return chemical;
    }

    public String generateCation() {
        cation = cationKeys.get((int)(Math.random()*(cationMap.size())));
        cationCharge = cationMap.get(cation);
        return cation;
    }

    public String generateAnion() {
        // System.out.println(Math.random()*(anionMap.size()));
        // System.out.println(anionKeys.get((int)(Math.random()*(anionMap.size()))));
        anion = anionKeys.get((int)(Math.random()*(anionMap.size())));
        anionCharge = anionMap.get(anion);
        return anion;
    }

    public ArrayList<String> dissect(String str) {
        ArrayList<String> tokens = new ArrayList<>();
        String chemical = str;
        for (int i=0; i<chemical.length(); i++) {
            char chr = chemical.charAt(i); 
            if (chr == '(') {
                if (i != 0 && Character.isUpperCase(chemical.charAt(i-1))) {
                    tokens.add(""+chemical.charAt(i-1));
                    System.out.println(tokens);
                }
                if (i != 0 && !Character.isDigit(chemical.charAt(i-1))) {
                    tokens.add("1");
                    System.out.println(tokens);
                }
                tokens.add(chemical.substring(i+1, chemical.indexOf(')', i)));
                System.out.println(tokens);
                i = chemical.indexOf(')', i)-1;
            } 
            if (i != 0 && Character.isUpperCase(chr) && !Character.isDigit(chemical.charAt(i-1))) {
                tokens.add("1");
                System.out.println(tokens);
            }
            if (Character.isLowerCase(chr)) {
                tokens.add(""+chemical.charAt(i-1)+chr);
                System.out.println(tokens);
            } else if (Character.isDigit(chr)) {
                if (Character.isUpperCase(chemical.charAt(i-1))) {
                    tokens.add(""+chemical.charAt(i-1));
                    System.out.println(tokens);
                }
                tokens.add(""+chr);
                System.out.println(tokens);
            }
        }
        if (Character.isUpperCase(chemical.charAt(chemical.length()-1))) {
            tokens.add(""+chemical.charAt(chemical.length()-1));
            System.out.println(tokens);
        }
        if (!Character.isDigit(chemical.charAt(chemical.length()-1))) {
            tokens.add("1");
            System.out.println(tokens);
        }
        System.out.println(tokens);
        return tokens;
    }

    public boolean isValid(ArrayList<String> tokens) {
        ArrayList<String> editedTokens = new ArrayList<>();
        for (int i=0; i<tokens.size(); i++) {
            int upperCase = 0;
            int number = 0;
            if (tokens.get(i).length() > 1) {
                for (int k=0; k<tokens.get(i).length(); k++) {
                    if (Character.isUpperCase(tokens.get(i).charAt(k))) upperCase++;
                    if (Character.isDigit(tokens.get(i).charAt(k)) && Character.getNumericValue(tokens.get(i).charAt(k)) > number) number = Character.getNumericValue(tokens.get(i).charAt(k));
                }
            }
            if (upperCase >= 2 || number >= 2) {
                editedTokens.add("("+tokens.get(i)+")");
            } else {
                editedTokens.add(tokens.get(i));
            }
        }
        System.out.println(editedTokens);
        try {
            System.out.println(cationMap.get(editedTokens.get(0)));
            if (cationMap.get(editedTokens.get(0)) 
            * Integer.parseInt(editedTokens.get(1)) 
            == -1 * anionMap.get(editedTokens.get(2)) 
            * Integer.parseInt(editedTokens.get(3))) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("INVALID ANSWER");
            return false;
        }
    }
}
