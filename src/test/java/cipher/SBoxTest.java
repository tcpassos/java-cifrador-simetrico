package cipher;

import core.util.SBox;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SBoxTest {

    @Test
    public void transformTest() {
        int[] arr = {0x11, 0x42, 0xFF};
        int[] expected = {0x82, 0x2c, 0x16};
        assertArrayEquals(expected, SBox.transform(arr));
    }

    @Test
    public void reverseTest() {
        int[] arr = {0x82, 0x2c, 0x16};
        int[] expected = {0x11, 0x42, 0xFF};
        assertArrayEquals(expected, SBox.reverse(arr));
    }

}
