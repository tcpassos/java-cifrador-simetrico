package cipher.util;

import core.util.ShiftOperations;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Passos
 */
public class ShiftOperationsTest {
    
    @Test
    public void circularShiftLeftTest() {
        assertEquals(0, ShiftOperations.circularShiftLeft(0, 16, 2));
        assertEquals(0b1100000011100100, ShiftOperations.circularShiftLeft(12345L, 16, 2));
        assertEquals(0b0000001110010011, ShiftOperations.circularShiftLeft(12345L, 16, 4));
        assertEquals(0b0011100100110000, ShiftOperations.circularShiftLeft(12345L, 16, 8));
        assertEquals(0b0011000000111001, ShiftOperations.circularShiftLeft(12345L, 16, 16));
    }

    @Test
    public void circularShiftRightTest() {
        assertEquals(0, ShiftOperations.circularShiftRight(0, 16, 2));
        assertEquals(0b0100110000001110, ShiftOperations.circularShiftRight(12345L, 16, 2));
        assertEquals(0b1001001100000011, ShiftOperations.circularShiftRight(12345L, 16, 4));
        assertEquals(0b0011100100110000, ShiftOperations.circularShiftRight(12345L, 16, 8));
        assertEquals(0b0011000000111001, ShiftOperations.circularShiftRight(12345L, 16, 16));
    }
    
}
