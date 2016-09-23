import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {
  Client client;
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp(){
    client = new Client("Gabrielle", "Sunflower blonde color, every 6 months");
  }

  @Test
  public void client_instantiatesCorrectly_true() {
    assertEquals(true, client instanceof Client);
  }

  @Test
  public void getName_returnsNameOfClient_String() {
    assertEquals("Gabrielle", client.getName());
  }

  @Test
  public void getDescription_returnsDescriptionOfClient_String() {
    assertEquals("Sunflower blonde color, every 6 months", client.getDescription());
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreTheSame_true() {
    Client secondClient = new Client("Gabrielle", "Sunflower blonde color, every 6 months");
    assertTrue(client.equals(secondClient));
  }

  @Test
  public void save_savesObjectToDatabase_true(){
    client.save();
    String sql = "SELECT * FROM clients WHERE name='Gabrielle'";
    Client secondClient;
    try(Connection con = DB.sql2o.open()){
      secondClient = con.createQuery(sql).executeAndFetchFirst(Client.class);
    }
    assertTrue(client.equals(secondClient));
  }

  @Test
  public void getId_returnsIdOfClient_true() {
    client.save();
    assertTrue(client.getId() > 0);
  }

}
