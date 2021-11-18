package cipher;

import cipher.bean.PassonConstants;
import cipher.util.JoinOperations;
import cipher.util.Permutations;
import cipher.util.SplitOperations;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Classe responsavel pela criptografia em blocos.
 */
public class PassonCipher implements PassonConstants {
    
    private File file;
    
    public PassonCipher(File file) {
        this.file = file;
    }

    public void encrypt(String key) throws IOException {
        long keys[] = PassonKey.generateKeys(key);
        byte[] block = new byte[BLOCK_SIZE];
        File outputFile = new File(file.getParent(), file.getName() + ".pson");
        try (DataInputStream reader = new DataInputStream(new FileInputStream(file));
             DataOutputStream writer = new DataOutputStream(new FileOutputStream(outputFile))) {
            // Le o arquivo enquanto existirem bytes disponiveis
            while(reader.available() > 0) {
                Arrays.fill(block, (byte) 0);
                reader.read(block);
                _writeBytes(writer, _encryptBlock(block, keys));
            }
        }
    }

    public void decrypt(String key) throws IOException {
        long keys[] = PassonKey.generateKeys(key);
        byte[] block = new byte[BLOCK_SIZE];
        File outputFile = new File(file.getParent(), _getFileNameWithoutExtension(file));
        try (DataInputStream reader = new DataInputStream(new FileInputStream(file));
             DataOutputStream writer = new DataOutputStream(new FileOutputStream(outputFile))) {
            // Le o arquivo enquanto existirem bytes disponiveis
            while(reader.available() > 0) {
                Arrays.fill(block, (byte) 0);
                reader.read(block);
                _writeBytes(writer, _decryptBlock(block, keys));
            }
        }
    }
    
    private int[] _encryptBlock(byte[] block, long[] keys) {
        BigInteger encryptedBlock = JoinOperations.joinBytes(block);
        for(long key: keys) {
            encryptedBlock = Permutations.permute48(encryptedBlock);
            // TODO: Aplicar S-Box
            encryptedBlock = encryptedBlock.xor(BigInteger.valueOf(key));
            // TODO: Gerar e aplicar funcoes no o vetor
        }
        return SplitOperations.getByteSegments(encryptedBlock, keys.length);
    }

    private int[] _decryptBlock(byte[] block, long[] keys) {
        BigInteger decryptedBlock = JoinOperations.joinBytes(block);
        for(long key: keys) {
            decryptedBlock = Permutations.unpermute48(decryptedBlock);
            // TODO: Aplicar S-Box
            decryptedBlock = decryptedBlock.xor(BigInteger.valueOf(key));
            // TODO: Gerar e aplicar funcoes no o vetor
        }
        return SplitOperations.getByteSegments(decryptedBlock, keys.length);
    }
    
    private void _writeBytes(OutputStream writer, int[] bytes) throws IOException {
        for(int b: bytes) {
            writer.write(b);
        }
    }
    
    private String _getFileNameWithoutExtension(File file) {
        String filename = file.getName();
        if (filename.contains(".")) {
            filename = filename.substring(0, filename.lastIndexOf('.'));
        }
        return filename;
    }

}
