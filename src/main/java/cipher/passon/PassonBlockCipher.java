package cipher.passon;

import cipher.BlockCipher;
import cipher.SBox;
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
            encryptedBlock = SBox.transform(encryptedBlock, BLOCK_SIZE);
            encryptedBlock = encryptedBlock.xor(BigInteger.valueOf(key));
            // TODO: Gerar e aplicar funcoes no o vetor
        }
        return SplitOperations.getByteSegments(encryptedBlock, block.length);
    }

    @Override
    public int[] decrypt(int[] block) {
        BigInteger decryptedBlock = JoinOperations.joinBytes(block);
        for (int i=keys.length-1; i>=0; i--) {
            // TODO: Gerar e aplicar funcoes no o vetor
            decryptedBlock = decryptedBlock.xor(BigInteger.valueOf(keys[i]));
            decryptedBlock = SBox.reverse(decryptedBlock, BLOCK_SIZE);
            decryptedBlock = Permutations.unpermute48(decryptedBlock);
        }
        return SplitOperations.getByteSegments(decryptedBlock, block.length);
    }

    @Override
    public int getBlockSize() {
        return BLOCK_SIZE;
    }

}
