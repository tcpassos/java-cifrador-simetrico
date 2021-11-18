package cipher.util;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PermutationsTest {
    
    @Test
    public void permute32Test() {
        BigInteger nonPermuted = BigInteger.valueOf(0b10110010110010011110001011010001L);
        BigInteger permuted = BigInteger.valueOf(0b11000011101101010000101111000011L);
        assertEquals(permuted, Permutations.permute32(nonPermuted));
    }

    @Test
    public void unpermute32Test() {
        BigInteger permuted = BigInteger.valueOf(0b11000011101101010000101111000011L);
        BigInteger nonPermuted = BigInteger.valueOf(0b10110010110010011110001011010001L);
        // 1101 0100 1011 0010 1111 1001 0000 0101
        // 1011 0010 1100 1001 1110 0010 1101 0001
        assertEquals(nonPermuted, Permutations.unpermute32(permuted));
    }
    
}
