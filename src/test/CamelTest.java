import org.junit.Assert;
import org.junit.Test;

import com.camel.cup.Camel;
import com.camel.cup.Color;
import com.camel.cup.Position;

/**
 * User: jan-marc
 * Date: 09.01.15
 * Time: 10:39
 */
public class CamelTest {
    @Test
    public void testGetLevel() throws Exception {
        Camel camel = new Camel(Color.WHITE,0);
        Assert.assertEquals(-1, camel.getLevel(new Position(0)));
    }
}
