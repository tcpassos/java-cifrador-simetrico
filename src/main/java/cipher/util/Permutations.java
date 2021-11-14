package cipher.util;

/**
 * Classe com metodos utilitarios para a permutacao de bits.
 */
public class Permutations {

    private static final int[] P_TABLE = { 16,  7, 20, 21, 29, 12, 28, 17,
                                            1, 15, 23, 26,  5, 18, 31, 10,
                                            2,  8, 24, 14, 32, 27,  3,  9,
                                           19, 13, 30,  6, 22, 11,  4, 25 };
    
    /**
     * Permuta os elementos do array informado de acordo com a funcao P
     *
     * @param source Array original
     * @return {@code int[]}
     */
    public static int[] permuteP(int[] source) {
        int[] permutedArray = new int[32];
        for (int i=0; i<P_TABLE.length; i++) {
            permutedArray[i] = source[ P_TABLE[i] - 1 ];
        }
        return permutedArray;
    }

    public static Boolean[] permuteP(Boolean[] source) {
        Boolean[] permutedArray = new Boolean[32];
        for (int i=0; i<P_TABLE.length; i++) {
            permutedArray[i] = source[ P_TABLE[i] - 1 ];
        }
        return permutedArray;
    }

    /**
     * Retorna os elementos permutados pela funcao P para sua ordem original
     *
     * @param source Array permutado em P
     * @return {@code int[]}
     */
    public static int[] unpermuteP(int[] source) {
        int[] nonPermutedArray = new int[32];
        for (int i=0; i<P_TABLE.length; i++) {
            nonPermutedArray[P_TABLE[i] - 1] = source[ i ];
        }
        return nonPermutedArray;
    }

    public static Boolean[] unpermuteP(Boolean[] source) {
        Boolean[] nonPermutedArray = new Boolean[32];
        for (int i=0; i<P_TABLE.length; i++) {
            nonPermutedArray[P_TABLE[i] - 1] = source[ i ];
        }
        return nonPermutedArray;
    }
    
    
}
