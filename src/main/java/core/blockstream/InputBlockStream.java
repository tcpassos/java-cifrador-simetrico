package core.blockstream;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Classe para a leitura de um arquivo em blocos.
 */
public class InputBlockStream {

    private InputStream reader;
    private int blockSize;
    private int[] buffer;
    private int allocatedInBuffer;
    
    public InputBlockStream(InputStream reader, int blockSize) {
        this.reader = reader;
        this.blockSize = blockSize;
        this.buffer = new int[blockSize];
        allocatedInBuffer = 0;
    }
    
    public boolean hasNext() throws IOException {
        return _fetch();
    }

    public int[] nextBlock() throws IOException {
        if (!_fetch()) {
            throw new EOFException();
        }
        allocatedInBuffer -= blockSize;
        int[] next = new int[buffer.length];
        System.arraycopy(buffer, 0, next, 0, buffer.length);
        Arrays.fill(buffer, 0);
        return next;
    }
    
    private boolean _fetch() throws IOException {
        if (allocatedInBuffer == blockSize) {
            return true;
        }
        int next = reader.read();
        while(next != -1) {
            buffer[allocatedInBuffer] = next;
            allocatedInBuffer++;
            if (allocatedInBuffer == blockSize) break;
            next = reader.read();
        }
        return allocatedInBuffer > 0;
    }
    
}
