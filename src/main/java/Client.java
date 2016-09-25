import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Client {
  private String name;
  private String description;
  private int id;
  private int stylist_id;

  public Client(String name, String description, int stylist_id) {
    this.name = name;
    this.description = description;
    this.stylist_id = stylist_id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public int getStylistId() {
    return stylist_id;
  }

  @Override
  public boolean equals(Object otherClient) {
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getName().equals(newClient.getName()) && this.getDescription().equals(newClient.getDescription());
    }
  }

  public void save(){
    String sql = "INSERT INTO clients (name, description, stylist_id) VALUES (:name, :description, :stylist_id)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("description", this.description)
        .addParameter("stylist_id", this.stylist_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Client> all(){
    String sql = "SELECT * FROM clients";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where id=:id";
      Client client = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
      return client;
    }
  }

  public void update(String description) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET description=:description WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("description", description)
        .executeUpdate();
    }
  }

}
