/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticket;

import _142390iapb.ticketservice.AddTicketRequest;
import _142390iapb.ticketservice.AddTicketResponse;
import _142390iapb.ticketservice.ClientType;
import _142390iapb.ticketservice.GetTicketListRequest;
import _142390iapb.ticketservice.GetTicketListResponse;
import _142390iapb.ticketservice.GetTicketRequest;
import _142390iapb.ticketservice.GetTicketResponse;
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
@Path("tickets")
public class TicketsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TicketsResource
     */
    public TicketsResource() {
    }
    
    @GET
    @Path("{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public GetTicketResponse getTicket(@PathParam("id") String id, @QueryParam("token") String token) {
        TicketWebService ws = new TicketWebService(); 
        GetTicketRequest request = new GetTicketRequest();
        request.setId(BigInteger.valueOf(Integer.parseInt(id)));
        request.setToken(token);
        return ws.getTicket(request);
    }
     
    @GET
    //@Produces("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public GetTicketListResponse getTicketList(@QueryParam("token") String token) {
        TicketWebService ws = new TicketWebService();  
        GetTicketListRequest request = new GetTicketListRequest();
        request.setToken(token);
        return ws.getTicketList(request);
    }
    

    /**
     * Retrieves representation of an instance of ticket.TicketsResource
     * @param id
     * @param content
     * @param token
     * @return an instance of _142390iapb.ticketservice.TicketType
     */
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public AddTicketResponse addTicket(TicketType content, @QueryParam("token") String token, @QueryParam("requestId") String requestId) {
        TicketWebService ws = new TicketWebService(); 
        AddTicketRequest request = new AddTicketRequest();
        request.setMuseumName(content.getMuseumName());
        request.setTicketName(content.getTicketName());
        request.setTicketDate(content.getTicketDate());
        request.setDueDate(content.getDueDate());
        request.setToken(token);
        request.setRequestId(BigInteger.valueOf(Integer.parseInt(requestId)));
        return ws.addTicket(request);
    }
}
