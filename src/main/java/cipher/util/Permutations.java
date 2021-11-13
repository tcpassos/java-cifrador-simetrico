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
    public static int[] permutateP(int[] source) {
        int[] permutedArray = new int[32];
        for (int i=0; i<P_TABLE.length; i++) {
            permutedArray[i] = source[ P_TABLE[i] ];
        }
        return permutedArray;
    }
    
}
