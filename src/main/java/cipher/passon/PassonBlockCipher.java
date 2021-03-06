package cipher.passon;

import cipher.BlockCipher;
import core.util.SBox;
import core.util.JoinOperations;
import core.util.Permutations;
import core.util.ShiftOperations;
import core.util.SplitOperations;
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
            // P-box 32 bits -> 32 bits
            encryptedBlock = Permutations.permute48(encryptedBlock);
            // S-box
            encryptedBlock = SBox.transform(encryptedBlock, BLOCK_SIZE);
            // bloco XOR K(n)
            encryptedBlock = encryptedBlock.xor(BigInteger.valueOf(key));
            // bloco << K(n)
            int shift = (int) (key % (Byte.SIZE * BLOCK_SIZE/2));
            long shiftedBlock = ShiftOperations.circularShiftLeft(encryptedBlock.longValue(), BLOCK_SIZE * Byte.SIZE, shift);
            encryptedBlock = BigInteger.valueOf(shiftedBlock);
        }
        return SplitOperations.getByteSegments(encryptedBlock, block.length);
    }

    @Override
    public int[] decrypt(int[] block) {
        BigInteger decryptedBlock = JoinOperations.joinBytes(block);
        for (int i=keys.length-1; i>=0; i--) {
            int shift = (int) (keys[i] % (Byte.SIZE * BLOCK_SIZE/2));
            long shiftedBlock = ShiftOperations.circularShiftRight(decryptedBlock.longValue(), BLOCK_SIZE * Byte.SIZE, shift);
            decryptedBlock = BigInteger.valueOf(shiftedBlock);
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
