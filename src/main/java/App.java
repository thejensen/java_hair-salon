import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import org.sql2o.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("unassociatedClients", Stylist.getUnassociatedClients());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //posts from /stylist/new/
    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String description = request.queryParams("description");
      model.put("name", name);
      model.put("description", description);
      Stylist stylist = new Stylist(name, description);
      stylist.save();
      int id = stylist.getId();
      model.put("id", id);
      model.put("success-add", stylist.getName());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylist/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist  = Stylist.find(Integer.parseInt(request.params("id")));
      model.put("stylist", stylist);
      model.put("template", "templates/stylist-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylist/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      String description = request.queryParams("description");
      stylist.updateDescription(description);
      String name = request.queryParams("name");
      stylist.updateName(name);
      model.put("success-edit", stylist.getName());
      String url = String.format("/stylist/%d", stylist.getId());
      response.redirect(url);
      return null;
    });

    post("/stylist/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      stylist.delete();
      String url = String.format("/");
      response.redirect(url);
      return null;
    });

    get("/stylist/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/stylist-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylist/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist  = Stylist.find(Integer.parseInt(request.params("id")));
      model.put("stylist", stylist);
      model.put("clients", stylist.getClients());
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylist/:id/client/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist  = Stylist.find(Integer.parseInt(request.params("id")));
      model.put("stylist", stylist);
      model.put("clients", stylist.getClients());
      model.put("template", "templates/client-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylist/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String description = request.queryParams("description");
      int stylistId = Integer.parseInt(request.queryParams("stylistId"));
      Client client = new Client(name, description, stylistId);
      client.save();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      model.put("clients", stylist.getClients());
      model.put("name", name);
      model.put("description", description);
      model.put("stylist", stylist);
      model.put("success-add", client.getName());
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/client/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client  = Client.find(Integer.parseInt(request.params("id")));
      Stylist stylist = Stylist.find(client.getStylistId());
      model.put("stylists", Stylist.all());
      model.put("stylist", stylist);
      model.put("client", client);
      model.put("template", "templates/client-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/client/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params("id")));
      String description = request.queryParams("description");
      String name = request.queryParams("name");
      int stylistId = Integer.parseInt(request.queryParams("stylistId"));
      Stylist stylist = Stylist.find(client.getStylistId());
      client.updateDescription(description);
      client.updateName(name);
      client.updateStylist(stylistId);
      String url = String.format("/stylist/%d", stylistId);
      response.redirect(url);
      return null;
    });

    post("/stylist/:stylist_id/client/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params("id")));
      Stylist stylist = Stylist.find(client.getStylistId());
      model.put("success-delete", client.getName());
      client.delete();
      String url = String.format("/");
      response.redirect(url);
      return null;
    });

    get("/hair", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/hair-gallery.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
