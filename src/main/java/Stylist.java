import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Stylist {
  private String name;
  private String description;
  private int id;

  public Stylist(String name, String description) {
    this.name = name;
    this.description = description;
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

  @Override
  public boolean equals(Object otherStylist) {
    if (!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getName().equals(newStylist.getName()) && this.getDescription().equals(newStylist.getDescription());
    }
  }

  public void save(){
    String sql = "INSERT INTO stylists (name, description) VALUES (:name, :description)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Stylist> all(){
    String sql = "SELECT * FROM stylists";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists where id=:id";
      Stylist stylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }

  public List<Client> getClients(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where stylist_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Client.class);
    }
  }

  public void updateName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET name=:name WHERE id=:id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateDescription(String description) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET description=:description WHERE id=:id";
      con.createQuery(sql)
        .addParameter("description", description)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE clients SET stylist_id=0 WHERE stylist_id=:id; DELETE FROM stylists WHERE id=:id;";
    con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public static List<Client> getUnassociatedClients(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE stylist_id=0";
      return con.createQuery(sql)
        .executeAndFetch(Client.class);
    }
  }



}
