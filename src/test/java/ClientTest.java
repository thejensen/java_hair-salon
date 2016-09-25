import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {
  Client client;
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp(){
    client = new Client("Gabrielle", "Sunflower blonde color, every 6 months", 1);
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
    Client secondClient = new Client("Gabrielle", "Sunflower blonde color, every 6 months", 1);
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

  @Test
  public void all_returnsAllInstancesOfClient_true(){
    client.save();
    Client secondClient = new Client("Hades", "Raven black hair, every 1 month, likes a lot of gel", 2);
    secondClient.save();
    assertTrue(Client.all().contains(client));
    assertTrue(Client.all().contains(secondClient));
  }

  @Test
    public void find_returnsClientWithSameId_secondClient() {
      client.save();
      Client secondClient = new Client("Hades", "Raven black hair, every 1 month, likes a lot of gel", 2);
      secondClient.save();
      assertEquals(Client.find(secondClient.getId()), secondClient);
    }

  @Test
  public void getStylistId_returnsStylistId_int() {
    assertEquals(1, client.getStylistId());
  }

  @Test
  public void update_updatesDescriptionForClient_true() {
    client.save();
    client.update("A new do, don't care what");
    assertEquals("A new do, don't care what", Client.find(client.getId()).getDescription());
  }


}
