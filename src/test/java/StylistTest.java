import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {
  Stylist stylist;
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp(){
    stylist = new Stylist("Xena");
  }

  @Test
  public void stylist_instantiatesCorrectly_true() {
    Stylist testStylist = new Stylist("Xena");
    assertTrue(testStylist instanceof Stylist);
  }
}
