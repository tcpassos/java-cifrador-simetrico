package cipher.passon;

import cipher.BlockCipher;
import cipher.util.JoinOperations;
import cipher.util.Permutations;
import cipher.util.SplitOperations;
import java.math.BigInteger;

/**
 * Algoritmo de criptografia bloco a bloco.
 */
public class PassonBlockCipher implements BlockCipher, PassonConstants {
    
    private long[] keys;

    public PassonBlockCipher(String key) {
        this.keys = PassonKey.generateKeys(key);
    }

    @Override
    public int[] encrypt(int[] block) {
        BigInteger encryptedBlock = JoinOperations.joinBytes(block);
        for(long key: keys) {
            encryptedBlock = Permutations.permute48(encryptedBlock);
            // encryptedBlock = SBox.transform(encryptedBlock, BLOCK_SIZE);
            encryptedBlock = encryptedBlock.xor(BigInteger.valueOf(key));
            // TODO: Gerar e aplicar funcoes no o vetor
        }
        return SplitOperations.getByteSegments(encryptedBlock, block.length);
    }

    @Override
    public int[] decrypt(int[] block) {
        BigInteger decryptedBlock = JoinOperations.joinBytes(block);
        _invertArray(keys);
        for(long key: keys) {
            // TODO: Gerar e aplicar funcoes no o vetor
            decryptedBlock = decryptedBlock.xor(BigInteger.valueOf(key));
            // decryptedBlock = SBox.reverse(decryptedBlock, BLOCK_SIZE);
            decryptedBlock = Permutations.unpermute48(decryptedBlock);
        }
        return SplitOperations.getByteSegments(decryptedBlock, block.length);
    }

    private void _invertArray(long[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            long temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

}
