package cipher.blockstream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Classe para a escrita de blocos de bits em um arquivo.
 */
public class OutputBlockStream {

    private OutputStream writer;

    public OutputBlockStream(OutputStream writer, int blockSize) {
        this.writer = writer;
    }
    
    public void write() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
