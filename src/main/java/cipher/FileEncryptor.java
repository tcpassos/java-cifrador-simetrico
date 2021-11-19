package cipher;

import cipher.blockstream.InputBlockStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Classe responsável pela criptografia e deciframento de arquivos.
 */
public class FileEncryptor {

    private File file;
    private BlockCipher blockCipher;

    public FileEncryptor(File file, BlockCipher blockCipher) {
        this.file = file;
        this.blockCipher = blockCipher;
    }
    
    public void encrypt() throws IOException {
        int[] block = new int[blockCipher.getBlockSize()];
        File outputFile = new File(file.getParent(), file.getName() + ".pson");
        try (FileInputStream reader = new FileInputStream(file);
             FileOutputStream writer = new FileOutputStream(outputFile)) {
            InputBlockStream bstream = new InputBlockStream(reader, blockCipher.getBlockSize());
            _writeHeaders(writer);
            // Le o arquivo enquanto existirem bytes disponiveis
            while(bstream.hasNext()) {
                Arrays.fill(block, (byte) 0);
                block = bstream.nextBlock();
                _writeBytes(writer, blockCipher.encrypt(block));
            }
        }
    }

    public void decrypt() throws IOException {
        int[] block = new int[blockCipher.getBlockSize()];
        File outputFile = new File(file.getParent(), _getFileNameWithoutExtension(file));
        try (FileInputStream reader = new FileInputStream(file);
             FileOutputStream writer = new FileOutputStream(outputFile)) {
            InputBlockStream bstream = new InputBlockStream(reader, blockCipher.getBlockSize());
            // Le o arquivo enquanto existirem bytes disponiveis
            while(bstream.hasNext()) {
                Arrays.fill(block, (byte) 0);
                block = bstream.nextBlock();
                _writeBytes(writer, blockCipher.decrypt(block));
            }
        }
    }
    
    private String _getFileNameWithoutExtension(File file) {
        String filename = file.getName();
        if (filename.contains(".")) {
            filename = filename.substring(0, filename.lastIndexOf('.'));
        }
        return filename;
    }
    
    private void _writeBytes(OutputStream writer, int[] bytes) throws IOException {
        for(int b: bytes) {
            writer.write(b);
        }
    }
    
    private void _writeHeaders(OutputStream writer) throws IOException {
        int blockSize = blockCipher.getBlockSize();
        int remainder = (int) (file.length() % blockSize);
        int padding = remainder == 0 ? 0 : blockSize - remainder;
        writer.write(padding);
    }

}
