package cipher;

import core.blockstream.InputBlockStream;
import core.util.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
        File outputFile = Files.appendExtension(file, "pson");
        try (FileInputStream reader = new FileInputStream(file);
             FileOutputStream writer = new FileOutputStream(outputFile)) {
            InputBlockStream bstream = new InputBlockStream(reader, blockCipher.getBlockSize());
            _writeHeader(writer);
            // Le o arquivo enquanto existirem bytes disponiveis
            while(bstream.hasNext()) {
                int[] block = bstream.nextBlock();
                _writeBytes(writer, blockCipher.encrypt(block));
            }
        }
    }

    public void decrypt() throws IOException {
        File outputFile = Files.changeExtension(file, "dec");
        try (FileInputStream reader = new FileInputStream(file);
             FileOutputStream writer = new FileOutputStream(outputFile)) {
            InputBlockStream bstream = new InputBlockStream(reader, blockCipher.getBlockSize());
            int padding = reader.read();
            // Le o arquivo enquanto existirem bytes disponiveis 
            while(bstream.hasNext()) {
                int[] block = bstream.nextBlock();
                if (bstream.hasNext()) {
                    _writeBytes(writer, blockCipher.decrypt(block));
                } else {
                    _writeBytesWithPadding(writer, blockCipher.decrypt(block), padding);
                }
            }
        }
    }

    private void _writeBytes(OutputStream writer, int[] bytes) throws IOException {
        for(int b: bytes) {
            writer.write(b);
        }
    }

    private void _writeBytesWithPadding(OutputStream writer, int[] bytes, int padding) throws IOException {
        for (int i=0; i<bytes.length - padding; i++) {
            writer.write(bytes[i]);
        }
    }
    
    private void _writeHeader(OutputStream writer) throws IOException {
        int blockSize = blockCipher.getBlockSize();
        int remainder = (int) (file.length() % blockSize);
        int padding = remainder == 0 ? 0 : blockSize - remainder;
        writer.write(padding);
    }

}
