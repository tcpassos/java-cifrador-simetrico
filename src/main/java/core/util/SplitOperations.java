package core.util;

import java.math.BigInteger;

/**
 * Classe com metodos para a segmentacao de valores.
 */
public class SplitOperations {

    /**
     * Segmenta um valor inteiro em uma quantidade n de bytes
     *
     * @param source Valor original
     * @param n Quantidade de segmentos
     * @return {@code int[]}
     */
    public static int[] getByteSegments(BigInteger source, int n) {
        int[] segments = new int[n];
        for (int i=0; i<n; i++) {
            segments[n-i-1] = source.shiftRight(Byte.SIZE * i)
                                  .and(BigInteger.valueOf(0xFF))
                                  .intValue();
        }
        return segments;
    }
    
    /**
     * Converte uma String para um array com os caracteres representados por inteiros
     *
     * @param str String a ser convertida
     * @return {@code int[]}
     */
    public static int[] getByteSegments(String str) {
        int[] arr = new int[str.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) str.charAt(i);
        }
        return arr;
    }
    
}
