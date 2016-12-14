/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticket;

import _142390iapb.ticketservice.AddClientRequest;
import _142390iapb.ticketservice.AddClientResponse;
import _142390iapb.ticketservice.AddClientTicketRequest;
import _142390iapb.ticketservice.ClientTicketListType;
import _142390iapb.ticketservice.ClientTicketType;
import _142390iapb.ticketservice.ClientType;
import _142390iapb.ticketservice.GetClientListRequest;
import _142390iapb.ticketservice.GetClientListResponse;
import _142390iapb.ticketservice.GetClientRequest;
import _142390iapb.ticketservice.GetClientResponse;
import _142390iapb.ticketservice.GetClientTicketListRequest;
import _142390iapb.ticketservice.GetTicketListRequest;
import _142390iapb.ticketservice.GetTicketListResponse;
import _142390iapb.ticketservice.GetTicketRequest;
import _142390iapb.ticketservice.TicketType;
import java.math.BigInteger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


/**
 * REST Web Service
 *
 * @author Martin
 */
@Path("clients")
public class ClientsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TicketsResource
     */
    public ClientsResource() {
    }
    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ClientType getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    */
    /**
     * Retrieves representation of an instance of ticket.TicketsResource
     * @param content
     * @param token
     * @return an instance of _142390iapb.ticketservice.TicketType
     */
    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public AddClientResponse addClient(ClientType content, @QueryParam("token") String token, @QueryParam("requestId") String requestId) {
        TicketWebService ws = new TicketWebService();   
        AddClientRequest request = new AddClientRequest();
        request.setFirstName(content.getFirstName());
        request.setLastName(content.getLastName());
        request.setDateOfBirth(content.getDateOfBirth());
        request.setSex(content.getSex());
        request.setToken(token);
        if (content.getPhoneNumber() != null) request.setPhoneNumber(content.getPhoneNumber());
        request.setRequestId(BigInteger.valueOf(Integer.parseInt(requestId)));
        return ws.addClient(request);
    }
    
    @GET
    @Path("{id : \\d+}")
    @Produces("application/json")
    public GetClientResponse getClient(@PathParam("id") String id, @QueryParam("token") String token) {
        TicketWebService ws = new TicketWebService();    
        GetClientRequest request = new GetClientRequest();
        request.setId(BigInteger.valueOf(Integer.parseInt(id)));
        request.setToken(token);
        return ws.getClient(request);
    }
    
    
    @GET
    @Produces("application/json")
    public GetClientListResponse getClientList(@QueryParam("hasRelatedTickets") String hasRelatedTickets, 
            @QueryParam("filterBySex") String filterBySex, 
            @QueryParam("filterByLastName") String filterByLastName, @QueryParam("token") String token) {
        TicketWebService ws = new TicketWebService();
        GetClientListRequest request = new GetClientListRequest();
        request.setToken(token);
        if (hasRelatedTickets != null) request.setHasRelatedTickets(hasRelatedTickets);
        if (filterBySex != null) request.setFilterBySex(filterBySex);
        if (filterByLastName != null) request.setFilterByLastName(filterByLastName);
        return ws.getClientList(request);
    }
    
    @POST
    @Path("{quantity : \\d+}")
    @Consumes("application/json")
    @Produces("application/json")
    public ClientTicketType addClientTicket(@QueryParam("requestId") String requestId, @QueryParam("clientId") String clientId, @QueryParam("ticketId") String ticketId, @PathParam("quantity") String quantity, @QueryParam("token") String token) {
        AddClientTicketRequest request = new AddClientTicketRequest();
        request.setToken(token);
        request.setRequestId(BigInteger.valueOf(Integer.parseInt(requestId)));
        request.setClientId(BigInteger.valueOf(Integer.parseInt(clientId)));
        request.setTicketId(BigInteger.valueOf(Integer.parseInt(ticketId)));
        request.setQuantity(BigInteger.valueOf(Integer.parseInt(quantity)));
        TicketWebService ws = new TicketWebService(); 
        return ws.addClientTicket(request);
    }
}

