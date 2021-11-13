package cipher;

import cipher.bean.PassonConstants;
import java.util.Arrays;

/**
 * Classe responsavel pela geracao das subchaves do algoritmo
 */
public class PassonKey implements PassonConstants {
    
    public long[] generateKeys(String originalKey) {
        long[] keys = new long[ROUND_COUNT];
        int[] keyArray = _toIntArray(originalKey);
        // 5 rounds
        for (int round=0; round<ROUND_COUNT; round++) {
            int[][] m = _generateMatrix(keyArray);
            int left  = (int) (_joinBytes(m[0]) ^ _joinBytes(_circularShiftLeft(m[2], 1)));
            int right = (int) (_joinBytes(m[1]) ^ _joinBytes(_circularShiftLeft(m[2], 2)));
            keys[round] = _join(3*Byte.SIZE, left, right);
            // Atualiza a chave para o proximo round
            // A=E, B=F, C=G, D=H
            keyArray[0] = m[0][2];
            keyArray[1] = m[1][2];
            keyArray[2] = m[2][0];
            keyArray[3] = m[2][1];
        }
        return keys;
    }
    
    private int[] _toIntArray(String str) {
        int[] arr = new int[str.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Character.digit(str.charAt(i), 10);
        }
        return arr;
    }
    
    private long _joinBytes(int ... bytes) {
        return _join(Byte.SIZE, bytes);
    }
    
    private long _join(int size, int ... values) {
        long joinedKey = 0;
        for (int i=0; i<values.length; i++) {
            joinedKey |= values[i] << (values.length - i - 1)*size;
        }
        return joinedKey;
    }
    
    private int[][] _generateMatrix(int[] originalKey) {
        int[][] matrix = new int[3][3];
        /*
                       [ A B ? ]
        [ A B C D ] => [ C D ? ]
                       [ ? ? ? ]
        */
        matrix[0][0] = originalKey[0];
        matrix[0][1] = originalKey[1];
        matrix[1][0] = originalKey[2];
        matrix[1][1] = originalKey[3];
        // Popula os valores da periferia da matriz
        matrix[0][2] = _circularShiftLeft(matrix[0][0], 1) ^ _circularShiftLeft(matrix[0][1], 2);
        matrix[1][2] = _circularShiftLeft(matrix[1][0], 1) ^ _circularShiftLeft(matrix[1][1], 2);
        matrix[2][0] = _circularShiftLeft(matrix[0][0], 1) ^ _circularShiftLeft(matrix[1][0], 2);
        matrix[2][1] = _circularShiftLeft(matrix[0][1], 1) ^ _circularShiftLeft(matrix[1][1], 2);
        matrix[2][2] = _circularShiftLeft(matrix[0][0], 1) ^ _circularShiftLeft(matrix[1][1], 2);
        return matrix;
    }
    
    private int _circularShiftLeft(int value, int amount) {
        int msbPosition = (int)(Math.log(value) / Math.log(2));
        int leftValueLength = msbPosition - amount;
        int leftValue = _applyLengthMask(value, leftValueLength) << amount;
        int rightValue = value >> leftValueLength;
        return leftValue | rightValue;
    }
    
    private int[] _circularShiftLeft(int[] arr, int amount) {
        int[] transformedArray = Arrays.copyOf(arr, arr.length);
        for (int shift = 0; shift < (amount % transformedArray.length); shift++) {
            int first = transformedArray[0];
            System.arraycopy(transformedArray, 1, transformedArray, 0, transformedArray.length - 1);
            transformedArray[transformedArray.length - 1] = first;
        }
        return transformedArray;
    }
    
    /**
     * Retorna o resultado de uma operacao de mascara para o comprimento especificado
     *
     * @param value Valor original
     * @param length Comprimento da mascara
     * @return Valor apos a operacao
     */
    private int _applyLengthMask(int value, int length) {
        int mask = Integer.MAX_VALUE & (Integer.MAX_VALUE ^ (Integer.MAX_VALUE << length));
        return (value & mask);
    }

}
