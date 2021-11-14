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

    @Test
    public void permutationsTest(){
        int[] nonPermutedArray = {1, 0, 1, 1, 0, 0, 1, 0,
                                    1, 1, 0, 0, 1, 0, 0, 1,
                                    1, 1, 1, 0, 0, 0, 1, 0,
                                    1, 1, 0, 1, 0, 0, 0, 1 };
        int[] permutedArray = { 1, 1, 0, 0, 0, 0, 1, 1,
                                  1, 0, 1, 1, 0, 1, 0, 1,
                                  0, 0, 0, 0, 1, 0, 1, 1,
                                  1, 1, 0, 0, 0, 0, 1, 1 };

        Boolean[] nonPermutedArrayBool = {true, false, true, true, false, false, true, false,
                                          true, true, false, false, true, false, false, true,
                                          true, true, true, false, false, false, true, false,
                                          true, true, false, true, false, false, false, true };

        Boolean[] permutedArrayBool = { true, true, false, false, false, false, true, true,
                                        true, false, true, true, false, true, false, true,
                                        false, false, false, false, true, false, true, true,
                                        true, true, false, false, false, false, true, true };
        
        assertArrayEquals( permutedArray, Permutations.permuteP(nonPermutedArray));
        assertArrayEquals( nonPermutedArray, Permutations.unpermuteP(Permutations.permuteP(nonPermutedArray)));

        assertArrayEquals( permutedArrayBool, Permutations.permuteP(nonPermutedArrayBool));
        assertArrayEquals( nonPermutedArrayBool, Permutations.unpermuteP(Permutations.permuteP(nonPermutedArrayBool)));
    }

    @Test
    public void unpermutationsTest(){
        int[] nonPermutedArray = {1, 0, 1, 1, 0, 0, 1, 0,
                                    1, 1, 0, 0, 1, 0, 0, 1,
                                    1, 1, 1, 0, 0, 0, 1, 0,
                                    1, 1, 0, 1, 0, 0, 0, 1 };
        int[] permutedArray = { 1, 1, 0, 0, 0, 0, 1, 1,
                                  1, 0, 1, 1, 0, 1, 0, 1,
                                  0, 0, 0, 0, 1, 0, 1, 1,
                                  1, 1, 0, 0, 0, 0, 1, 1 };

        Boolean[] nonPermutedArrayBool = {true, false, true, true, false, false, true, false,
                                          true, true, false, false, true, false, false, true,
                                          true, true, true, false, false, false, true, false,
                                          true, true, false, true, false, false, false, true };

        Boolean[] permutedArrayBool = { true, true, false, false, false, false, true, true,
                                        true, false, true, true, false, true, false, true,
                                        false, false, false, false, true, false, true, true,
                                        true, true, false, false, false, false, true, true };
        
        assertArrayEquals( nonPermutedArray, Permutations.unpermuteP(permutedArray));
        assertArrayEquals( permutedArray, Permutations.permuteP(Permutations.unpermuteP(permutedArray)));

        assertArrayEquals( nonPermutedArrayBool, Permutations.unpermuteP(permutedArrayBool));
        assertArrayEquals( permutedArrayBool, Permutations.permuteP(Permutations.unpermuteP(permutedArrayBool)));
    }
    
}
