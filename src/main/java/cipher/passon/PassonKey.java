package cipher.passon;

import cipher.SBox;
import static cipher.util.JoinOperations.join;
import static cipher.util.JoinOperations.joinBytes;
import cipher.util.Permutations;
import static cipher.util.ShiftOperations.circularShiftLeft;
import static cipher.util.SplitOperations.getByteSegments;
import java.math.BigInteger;

/**
 * Classe responsavel pela geracao das subchaves do algoritmo.
 */
public class PassonKey implements PassonConstants {
    
    /**
     * Gera as sub-chaves utilizadas para criptografia a partir da chave de entrada.
     *
     * @param originalKey Chave informada pelo usuario
     * @return {@code long[]}
     */
    public static long[] generateKeys(String originalKey) {
        long[] keys = new long[ROUND_COUNT];
        int[] keyArray = getByteSegments(originalKey);
        // Realiza a permutacao da chave usando a tabela P
        BigInteger joinedKey = Permutations.permute32((joinBytes(keyArray)));
        // Aplica a S-Box
        keyArray = SBox.transform(getByteSegments(joinedKey, 4));
        for (int round=0; round<ROUND_COUNT; round++) {
            int[][] m = _generateMatrix(keyArray);
            int left  = joinBytes(m[0]).xor(joinBytes(circularShiftLeft(m[2], 1))).intValue();
            int right = joinBytes(m[1]).xor(joinBytes(circularShiftLeft(m[2], 2))).intValue();
            keys[round] = join(3*Byte.SIZE, left, right).longValue();
            // Atualiza a chave para o proximo round
            // A=E, B=F, C=G, D=H
            keyArray[0] = m[0][2];
            keyArray[1] = m[1][2];
            keyArray[2] = m[2][0];
            keyArray[3] = m[2][1];
        }
        return keys;
    }
    
    /**
     * Gera a matriz M a partir do array de 4 bytes da chave ABCD
     *
     * @param originalKey Chave ABCD
     * @return {@code int[][]}
     */
    private static int[][] _generateMatrix(int[] originalKey) {
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
        matrix[0][2] = circularShiftLeft(matrix[0][0], 1) ^ circularShiftLeft(matrix[0][1], 2);
        matrix[1][2] = circularShiftLeft(matrix[1][0], 1) ^ circularShiftLeft(matrix[1][1], 2);
        matrix[2][0] = circularShiftLeft(matrix[0][0], 1) ^ circularShiftLeft(matrix[1][0], 2);
        matrix[2][1] = circularShiftLeft(matrix[0][1], 1) ^ circularShiftLeft(matrix[1][1], 2);
        matrix[2][2] = circularShiftLeft(matrix[0][0], 1) ^ circularShiftLeft(matrix[1][1], 2);
        return matrix;
    }

}
