package core.util;

import java.util.Arrays;

/**
 * Classe com metodos para operações de deslocamento.
 */
public class ShiftOperations {
    
    /**
     * Desloca de forma circular os bits do valor informado n vezes para a esquerda
     *
     * @param value Valor que será transformado
     * @param amount Quantidade de bits deslocados
     * @return {@code int}
     */
    public static int circularShiftLeft(int value, int amount) {
        int msbPosition = (int)(Math.log(value) / Math.log(2));
        int leftValueLength = msbPosition - amount;
        int leftValue = (int) _applyLengthMask(value, leftValueLength) << amount;
        int rightValue = value >> leftValueLength;
        return leftValue | rightValue;
    }
    
    /**
     * Desloca de forma circular os bits do valor informado n vezes para a esquerda
     *
     * @param value Valor que será transformado
     * @param size Tamanho do set de bits que será transformado
     * @param amount Quantidade de bits deslocados
     * @return {@code int}
     */
    public static long circularShiftLeft(long value, int size, int amount) {
        int leftValueLength = size - amount;
        long leftValue = _applyLengthMask(value, leftValueLength) << amount;
        long rightValue = value >> leftValueLength;
        return leftValue | rightValue;
    }
    
    /**
     * Desloca de forma circular os elementos do array informado n vezes para a esquerda
     *
     * @param arr Array que será transformado
     * @param amount Quantidade de elementos deslocados
     * @return {@code int[]}
     */
    public static int[] circularShiftLeft(int[] arr, int amount) {
        int[] transformedArray = Arrays.copyOf(arr, arr.length);
        for (int shift = 0; shift < (amount % transformedArray.length); shift++) {
            int first = transformedArray[0];
            System.arraycopy(transformedArray, 1, transformedArray, 0, transformedArray.length - 1);
            transformedArray[transformedArray.length - 1] = first;
        }
        return transformedArray;
    }
    
    /**
     * Desloca de forma circular os bits do valor informado n vezes para a direita
     *
     * @param value Valor que será transformado
     * @param size Tamanho do set de bits que será transformado
     * @param amount Quantidade de bits deslocados
     * @return {@code long}
     */
    public static long circularShiftRight(long value, int size, int amount) {
        int leftValueShift = size - amount;
        long leftValue = _applyLengthMask(value, amount) << leftValueShift;
        long rightValue = value >> amount;
        return leftValue | rightValue;
    }

    /**
     * Retorna o resultado de uma operacao de mascara para o comprimento especificado
     *
     * @param value Valor original
     * @param length Comprimento da mascara
     * @return Valor apos a operacao
     */
    private static long _applyLengthMask(long value, int length) {
        long mask = Long.MAX_VALUE & (Long.MAX_VALUE ^ (Long.MAX_VALUE << length));
        return (value & mask);
    }
    
}
