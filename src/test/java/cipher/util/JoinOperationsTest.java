package cipher.util;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JoinOperationsTest {
    
    public JoinOperationsTest() {
    }
    
    @Test
    public void joinBytesTest() {
        assertEquals(BigInteger.valueOf(66051), JoinOperations.joinBytes(1, 2, 3));
        assertEquals(BigInteger.valueOf(1029), JoinOperations.joinBytes(4, 5));
        assertEquals(BigInteger.valueOf(6), JoinOperations.joinBytes(6));
        assertEquals(BigInteger.ZERO, JoinOperations.joinBytes(0));
    }

    @Test
    public void joinTest() {
        assertEquals(new BigInteger("18446744082299486211"), JoinOperations.join(Integer.SIZE, 1, 2, 3));
        assertEquals(BigInteger.valueOf(17179869189l), JoinOperations.join(Integer.SIZE, 4, 5));
        assertEquals(BigInteger.valueOf(6), JoinOperations.join(Integer.SIZE, 6));
        assertEquals(BigInteger.ZERO, JoinOperations.join(Integer.SIZE, 0));
    }
    
}
