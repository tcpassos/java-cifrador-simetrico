package cipher.util;

import java.math.BigInteger;

/**
 * Classe com metodos utilitarios para a permutacao de bits.
 */
public class Permutations {

    private static final int[] P_TABLE_32 = {
        16,  7, 20, 21, 29, 12, 28, 17,
         1, 15, 23, 26,  5, 18, 31, 10,
         2,  8, 24, 14, 32, 27,  3,  9,
        19, 13, 30,  6, 22, 11,  4, 25 };

    private static final int[] P_TABLE_48 = {
        14, 12, 44, 28, 21, 40, 11, 18, 31, 45, 23, 24,
        32, 35,  4,  8,  6, 48, 37, 27, 17, 13, 20,  7,
        30, 26, 38, 46,  5, 19, 33, 42, 25, 34,  9, 39,
        3 ,  1,  2, 29, 36, 16, 22, 41, 15, 10, 47, 43
    };
    
    /**
     * Permuta os bits do valor informado de acordo com a tabela de permutacao de 32 bits
     *
     * @param source Valor original
     * @return {@code BigInteger}
     */    
    public static BigInteger permute32(BigInteger source) {
        return _permute(source, P_TABLE_32);
    }

    /**
     * Retorna os elementos permutados pela tabela de 32 bits para sua ordem original
     *
     * @param source Valor permutado
     * @return {@code BigInteger}
     */    
    public static BigInteger unpermute32(BigInteger source) {
        return _unpermute(source, P_TABLE_32);
    }

    /**
     * Permuta os bits do valor informado de acordo com a tabela de permutacao de 48 bits
     *
     * @param source Valor original
     * @return {@code BigInteger}
     */    
    public static BigInteger permute48(BigInteger source) {
        return _permute(source, P_TABLE_48);
    }

    /**
     * Retorna os elementos permutados pela tabela de 48 bits para sua ordem original
     *
     * @param source Valor permutado
     * @return {@code BigInteger}
     */    
    public static BigInteger unpermute48(BigInteger source) {
        return _unpermute(source, P_TABLE_48);
    }
    
    private static BigInteger _permute(BigInteger source, int[] map) {
        BigInteger permutedValue = BigInteger.ZERO;
        for (int bitIndex=map.length-1; bitIndex>=0; bitIndex--) {
            int tableIndex = map.length - bitIndex - 1;
            if (source.testBit(map.length - map[tableIndex])) {
                permutedValue = permutedValue.setBit(bitIndex);
            }            
        }
        return permutedValue;
    }

    private static BigInteger _unpermute(BigInteger source, int[] map) {
        BigInteger unpermutedValue = BigInteger.ZERO;
        for (int bitIndex=map.length-1; bitIndex>=0; bitIndex--) {
            int tableIndex = map.length - bitIndex - 1;
            if (source.testBit(bitIndex)) {
                unpermutedValue = unpermutedValue.setBit(map.length - map[tableIndex]);
            }            
        }
        return unpermutedValue;
    }
    
}
