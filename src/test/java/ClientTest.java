import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {
  Client client;
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp(){
    client = new Client("Gabrielle");
  }

  @Test
  public void client_instantiatesCorrectly_true() {
    Client testClient = new Client("Gabrielle");
    assertEquals(true, testClient instanceof Client);
  }
}
