package cipher.util;

import java.math.BigInteger;

/**
 * Classe com metodos utilitarios para a permutacao de bits.
 */
public class Permutations {

    private static final int[] P_TABLE = { 16,  7, 20, 21, 29, 12, 28, 17,
                                            1, 15, 23, 26,  5, 18, 31, 10,
                                            2,  8, 24, 14, 32, 27,  3,  9,
                                           19, 13, 30,  6, 22, 11,  4, 25 };
    
    /**
     * Permuta os bits do valor informado de acordo com a funcao P
     *
     * @param source Valor original
     * @return {@code BigInteger}
     */    
    public static BigInteger permute32(BigInteger source) {
        BigInteger permutedValue = BigInteger.ZERO;
        for (int bitIndex=P_TABLE.length-1; bitIndex>=0; bitIndex--) {
            int tableIndex = P_TABLE.length - bitIndex - 1;
            if (!source.testBit(P_TABLE.length - P_TABLE[tableIndex])) {
                continue;
            }            
            permutedValue = permutedValue.setBit(bitIndex);
        }
        return permutedValue;
    }    
    
}
