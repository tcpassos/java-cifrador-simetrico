package cipher.math;

import core.math.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {
    
    private Vector vector;
    
    @BeforeEach
    public void setUp() {
        vector = new Vector(100, 200, 300);
    }
    
    @Test
    public void rotateXTest() {
        assertEquals(new Vector(100, 200, 300), vector.rotateX(0));
        assertEquals(new Vector(100, -300, 200), vector.rotateX(Math.PI/2));
        assertEquals(new Vector(100, -200, -300), vector.rotateX(Math.PI));
        assertEquals(new Vector(100, 200, 300), vector.rotateX(2*Math.PI));
    }

    @Test
    public void rotateYTest() {
        assertEquals(new Vector(100, 200, 300), vector.rotateY(0));
        assertEquals(new Vector(300, 200, -100), vector.rotateY(Math.PI/2));
        assertEquals(new Vector(-100, 200, -300), vector.rotateY(Math.PI));
        assertEquals(new Vector(100, 200, 300), vector.rotateY(2*Math.PI));
    }

    @Test
    public void rotateZTest() {
        assertEquals(new Vector(100, 200, 300), vector.rotateZ(0));
        assertEquals(new Vector(-200, 100, 300), vector.rotateZ(Math.PI/2));
        assertEquals(new Vector(-100, -200, 300), vector.rotateZ(Math.PI));
        assertEquals(new Vector(100, 200, 300), vector.rotateZ(2*Math.PI));
    }
    
}
