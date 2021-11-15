package cipher.blockstream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Classe para a leitura de um arquivo em blocos.
 */
public class InputBlockStream {

    private InputStream reader;
    
    public InputBlockStream(InputStream reader, int blockSize) {
        this.reader = reader;
    }
    
    public boolean hasNext() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int[] next() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
