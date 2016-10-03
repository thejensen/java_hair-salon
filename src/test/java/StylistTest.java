import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {
  Stylist stylist;
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp(){
    stylist = new Stylist("Xena", "Straightforward, elegant style");
  }

  @Test
  public void stylist_instantiatesCorrectly_true() {
    assertTrue(stylist instanceof Stylist);
  }

  @Test
  public void getName_returnsNameOfStylist_String() {
    assertEquals("Xena", stylist.getName());
  }

  @Test
  public void getDescription_returnsDescriptionOfStylist_String() {
    assertEquals("Straightforward, elegant style", stylist.getDescription());
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreTheSame_true() {
    Stylist secondStylist = new Stylist("Xena", "Straightforward, elegant style");
    assertTrue(stylist.equals(secondStylist));
  }

  @Test
  public void save_savesObjectToDatabase_true(){
    stylist.save();
    String sql = "SELECT * FROM stylists WHERE name='Xena'";
    Stylist secondStylist;
    try(Connection con = DB.sql2o.open()){
      secondStylist = con.createQuery(sql).executeAndFetchFirst(Stylist.class);
    }
    assertTrue(stylist.equals(secondStylist));
  }

  @Test
  public void getId_returnsIdOfStylist_true() {
    stylist.save();
    assertTrue(stylist.getId() > 0);
  }

  @Test
  public void all_returnsAllInstancesOfStylist_true(){
    stylist.save();
    Stylist secondStylist = new Stylist("Hercules", "Specializes in wavy layers");
    secondStylist.save();
    assertTrue(Stylist.all().contains(stylist));
    assertTrue(Stylist.all().contains(secondStylist));
  }

  @Test
  public void find_returnsStylistWithSameId_secondStylist() {
    stylist.save();
    Stylist secondStylist = new Stylist("Hercules", "Specializes in wavy layers");
    secondStylist.save();
    assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
  }

  @Test
  public void getClients_retrievesAllClientsWithStylistId_clientList(){
    stylist.save();
    Client client = new Client("Boxer", "Bowlcut trim every 2 weeks", stylist.getId());
    client.save();
    Client secondClient = new Client("Lala", "Lala", stylist.getId());
    secondClient.save();
    Client[] clients = new Client[] {client, secondClient};
    assertTrue(stylist.getClients().containsAll(Arrays.asList(clients)));
  }

  @Test
  public void updateDescription_updatesStylistDescription_true() {
    stylist.save();
    stylist.updateDescription("Blunt bangs");
    assertEquals("Blunt bangs", Stylist.find(stylist.getId()).getDescription());
  }

  @Test
  public void delete_deletesStylist_true() {
    stylist.save();
    int stylistId = stylist.getId();
    stylist.delete();
    assertEquals(null, Stylist.find(stylistId));
  }

  @Test
  public void getOrphanedClients_retrievesListOfClientsWhoseStylistIdsAreZero_true() {
    stylist.save();
    Client client = new Client("Boxer", "Bowlcut trim every 2 weeks", stylist.getId());
    client.save();
    stylist.delete();
    assertTrue(Stylist.getOrphanedClients().contains(client));
  }

}
