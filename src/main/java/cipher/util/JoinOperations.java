package cipher.util;

import java.math.BigInteger;

/**
 * Classe com metodos para operações de juncao.
 */
public class JoinOperations {
    
    /**
     * Agrupa um conjunto de bytes em um unico valor
     *
     * @param bytes Bytes a serem agrupados
     * @return {@code BigInteger}
     */
    public static BigInteger joinBytes(int ... bytes) {
        return join(Byte.SIZE, bytes);
    }
    
    /**
     * Agrupa os bits de n elementos em um unico valor
     *
     * @param size Tamanho de cada elemento em bits
     * @param values Valores a serem agrupados
     * @return {@code BigInteger}
     */
    public static BigInteger join(int size, int ... values) {
        BigInteger joinedKey = BigInteger.ZERO;
        for (int i=0; i<values.length; i++) {
            BigInteger valueToAppend = BigInteger.valueOf(values[i])
                                                 .shiftLeft((values.length - i - 1) * size);
            joinedKey = joinedKey.or(valueToAppend);
        }
        return joinedKey;
    }
    
}
