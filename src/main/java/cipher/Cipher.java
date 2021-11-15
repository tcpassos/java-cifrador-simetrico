package cipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface com os metodos para a criptografia e defiframento de um arquivo.
 */
public interface Cipher {
    
    public void encrypt(InputStream reader, OutputStream writer) throws IOException;
    
    public void decrypt(InputStream reader, OutputStream writer) throws IOException;
    
}
