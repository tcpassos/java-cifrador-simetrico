package cipher.util;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 */
public class SplitOperationsTest {

    @Test
    public void getByteSegmentsBigIntegerTest() {
        int[] expected = {
            0b10110010,
            0b11001001,
            0b11100010,
            0b11010001
        };
        assertArrayEquals(expected,
                          SplitOperations.getByteSegments(BigInteger.valueOf(0b10110010110010011110001011010001L), 4));
    }

    @Test
    public void getByteSegmentsStringTest() {
        int[] expected = {97, 98, 99, 100};
        assertArrayEquals(expected, SplitOperations.getByteSegments("abcd"));
    }
    
}
