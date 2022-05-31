package Beans;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HitsDataTest {

    @Test
    public void test() {
        boolean result = HitsData.methodForTest(1);
        assertEquals(result, true);
    }
}
