package org.unibl.etf.api;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.unibl.etf.model.Client;
import org.unibl.etf.service.ClientService;

@Path("/klijenti")
public class APIService {

	ClientService service;
	
	public APIService() {
		service = new ClientService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Client> getAll() {
		return service.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addClient(Client klijent) {
		if (service.addClient(klijent)) {
			return Response.status(200).entity(klijent).build();
		} else {
			return Response.status(500).entity("Greska pri dodavanju").build();
		}
	}
	
	
	@PUT
	@Path("/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(@PathParam("username") String korisnickoIme, String newPass) {
		JSONObject input = new JSONObject(newPass);
		newPass = input.getString("password");
		if (service.updatePassword(korisnickoIme, newPass)) {
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}
	
	
	@DELETE
	@Path("/{username}")
	public Response remove(@PathParam("username") String koriscnikoIme) {
		if (service.deleteClient(koriscnikoIme)) {
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}
	
	@PUT
	@Path("/{username}" + "/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateInfo(@PathParam("username") String korisnickoIme, String lozinka) {
		JSONObject input = new JSONObject(lozinka);
		lozinka = input.getString("password");
		Client klijent = service.validateInfo(korisnickoIme, lozinka);
		if (klijent != null) {
			return Response.status(200).entity(klijent).build();
		} else {
			return Response.status(404).build();
		}
	}
	
}
