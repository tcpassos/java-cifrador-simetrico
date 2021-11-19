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
        assertEquals(nonPermuted, Permutations.unpermute32(permuted));
    }
    
}
