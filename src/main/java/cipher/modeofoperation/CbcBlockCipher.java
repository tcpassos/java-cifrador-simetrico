package cipher.modeofoperation;

import cipher.BlockCipher;
import core.util.JoinOperations;
import core.util.SplitOperations;
import java.math.BigInteger;

/**
 * Classe responsável pela criptografia do modo de operação CBC.
 */
public class CbcBlockCipher implements BlockCipher {

    private BlockCipher encryptor;
    private BigInteger salt;
    
    public CbcBlockCipher(BlockCipher encryptor) {
        this.encryptor = encryptor;
        this.salt = BigInteger.ONE;
    }

    @Override
    public int[] encrypt(int[] block) {
        BigInteger transformedBlock = JoinOperations.joinBytes(block).xor(salt);
        int[] encryptedBlock = encryptor.encrypt(SplitOperations.getByteSegments(transformedBlock, encryptor.getBlockSize()));
        salt = JoinOperations.joinBytes(encryptedBlock);
        return encryptedBlock;
    }

    @Override
    public int[] decrypt(int[] block) {
        BigInteger newSalt = JoinOperations.joinBytes(block);
        BigInteger decryptedBlock = JoinOperations.joinBytes(encryptor.decrypt(block))
                                                  .xor(salt);
        salt = newSalt;
        return SplitOperations.getByteSegments(decryptedBlock, encryptor.getBlockSize());
    }

    @Override
    public int getBlockSize() {
        return encryptor.getBlockSize();
    }
    
}
