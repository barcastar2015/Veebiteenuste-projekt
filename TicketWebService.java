/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticket;

import _142390iapb.ticketservice.AddClientRequest;
import _142390iapb.ticketservice.AddClientResponse;
import _142390iapb.ticketservice.AddClientTicketRequest;
import _142390iapb.ticketservice.AddTicketRequest;
import _142390iapb.ticketservice.AddTicketResponse;
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
import _142390iapb.ticketservice.GetTicketResponse;
import _142390iapb.ticketservice.TicketType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author Martin
 */
@WebService(serviceName = "TicketService", portName = "TicketPort", endpointInterface = "_142390iapb.ticketservice.TicketPortType", targetNamespace = "142390IAPB/TicketService", wsdlLocation = "WEB-INF/wsdl/TicketWebService/TicketService.wsdl")
public class TicketWebService {

    static int nextTicketId = 1;
    static int nextClientId = 1;
    static List<TicketType> ticketList = new ArrayList<>();
    static List<ClientType> clientList = new ArrayList<>();
    static String TOKEN = "salajane";
    static List<String> idTokenList = new ArrayList<>();

    public AddTicketResponse addTicket(AddTicketRequest parameter) {
        TicketType ticket = new TicketType();
        if (parameter.getToken().equalsIgnoreCase(TOKEN)) {
            String tokenId = parameter.getToken() + parameter.getRequestId();
            if (idTokenList.contains(tokenId)) {return null;}
            if (!idTokenList.contains(tokenId)) {idTokenList.add(tokenId);}
            ticket.setId(BigInteger.valueOf(nextTicketId++));
            ticket.setMuseumName(parameter.getMuseumName());
            ticket.setTicketName(parameter.getTicketName());
            ticket.setTicketDate(parameter.getTicketDate()); 
            ticket.setDueDate(parameter.getDueDate());
            if (parameter.getTicketName().equalsIgnoreCase("family")) {
                ticket.setPrice(10);
            }
            if (parameter.getTicketName().equalsIgnoreCase("adult")) {
                ticket.setPrice(7);
            }
            if (parameter.getTicketName().equalsIgnoreCase("child")) {
                ticket.setPrice(4);
            }
            if (parameter.getTicketName().equalsIgnoreCase("student")) {
                ticket.setPrice(5);
            }
            ticket.setVatAmount(ticket.getPrice()/80*20);
            ticketList.add(ticket);
        }
        AddTicketResponse tt = new AddTicketResponse();
        tt.setTicket(ticket);
        return tt;  
    }

    public GetTicketResponse getTicket(GetTicketRequest parameter) {
        GetTicketResponse it = null;
        if(parameter.getToken().equalsIgnoreCase(TOKEN)){
            for(int i = 0;i < ticketList.size();i++){
                if(ticketList.get(i).getId().equals(parameter.getId())){
                    TicketType ticket = ticketList.get(i);
                    it = new GetTicketResponse();
                    it.setTicket(ticket);
                }
            }
        }
        return it;
    }

    public GetTicketListResponse getTicketList(GetTicketListRequest parameter) {
        //tuleb kontrollida, et property ei ole null
        GetTicketListResponse response = new GetTicketListResponse();
        if (parameter.getToken().equalsIgnoreCase(TOKEN)) {
            ticketList.stream().forEach((ticketType) -> {
                response.getTicket().add(ticketType);
            });
        }
        return response;
    }

    public AddClientResponse addClient(AddClientRequest parameter) {
        ClientType client = new ClientType();
        if (parameter.getToken().equalsIgnoreCase(TOKEN)) {
            String tokenId = parameter.getToken() + parameter.getRequestId();
            if (idTokenList.contains(tokenId)) {return null;}
            if (!idTokenList.contains(tokenId)) {idTokenList.add(tokenId);}
            client.setId(BigInteger.valueOf(nextClientId++));
            client.setFirstName(parameter.getFirstName());
            client.setLastName(parameter.getLastName());
            client.setDateOfBirth(parameter.getDateOfBirth());
            client.setSex(parameter.getSex());
            client.setClientTicketList(new ClientTicketListType());
            client.setPhoneNumber(parameter.getPhoneNumber());
            clientList.add(client);
        }
        AddClientResponse acr = new AddClientResponse();
        acr.setClient(client);
        return acr;
    }

    public GetClientResponse getClient(GetClientRequest parameter) {
        GetClientResponse it = null;
        if(parameter.getToken().equalsIgnoreCase(TOKEN)){
            for(int i = 0;i <clientList.size();i++){
                if(clientList.get(i).getId().equals(parameter.getId())){
                    ClientType client = clientList.get(i);
                    it = new GetClientResponse();
                    it.setClient(client);
                }
            }
        }
        return it;
    }

    public GetClientListResponse getClientList(GetClientListRequest parameter) {
        GetClientListResponse response = new GetClientListResponse();
        if (parameter.getToken().equalsIgnoreCase(TOKEN)) {
            if (parameter.getHasRelatedTickets() != null) {
                clientList.stream().filter((clientType) -> (((parameter.getHasRelatedTickets().equalsIgnoreCase("ei" ) && (clientType.getClientTicketList()== null || clientType.getClientTicketList().getClientTicket().isEmpty()))
                        || (parameter.getHasRelatedTickets().equalsIgnoreCase("jah" ) && clientType.getClientTicketList()!= null && !clientType.getClientTicketList().getClientTicket().isEmpty()))
                        )).forEach((clientType) -> {
                            response.getClient().add(clientType);
                });
            }
            if (parameter.getFilterBySex()!= null) {
                clientList.stream().filter((clientType) -> (((parameter.getFilterBySex().equalsIgnoreCase("female" ) && (clientType.getSex().equals(clientType.getSex().FEMALE)))
                        || (parameter.getFilterBySex().equalsIgnoreCase("male" ) && clientType.getSex().equals(clientType.getSex().MALE)))
                        )).forEach((clientType) -> {
                            response.getClient().add(clientType);
                });
            }
            
            if (parameter.getFilterByLastName() != null) {
                clientList.stream().filter((clientType) -> (((parameter.getFilterByLastName().equalsIgnoreCase(clientType.getLastName())))
                        )).forEach((clientType) -> {
                            response.getClient().add(clientType);
                });
            }
            
        }
        return response;
    }

    public ClientTicketType addClientTicket(AddClientTicketRequest parameter) {
        ClientTicketType clientTicket = new ClientTicketType();
        if (parameter.getToken().equalsIgnoreCase(TOKEN)) {
            String tokenId = parameter.getToken() + parameter.getRequestId();
            if (idTokenList.contains(tokenId)) {return null;}
            if (!idTokenList.contains(tokenId)) {idTokenList.add(tokenId);}
            GetTicketRequest ticketRequest = new GetTicketRequest();
            ticketRequest.setId(parameter.getTicketId());
            ticketRequest.setToken(parameter.getToken());
            clientTicket.setTicket(getTicket(ticketRequest).getTicket());
            clientTicket.setQuantity(parameter.getQuantity());
            clientTicket.setUnitPrice(getTicket(ticketRequest).getTicket().getPrice() + getTicket(ticketRequest).getTicket().getVatAmount());
            for (int i = 0; i < clientList.size(); i++) {
                if (clientList.get(i).getId().equals(parameter.getClientId())) {
                    clientList.get(i).getClientTicketList().getClientTicket().add(clientTicket);
                    return clientTicket;
                }
            } 
        }
        return null;
    }

    public ClientTicketListType getClientTicketList(GetClientTicketListRequest parameter) {
        ClientTicketListType clientTicketList = null;
        if (parameter.getToken().equalsIgnoreCase(TOKEN)) {
            for (int i = 0; i < clientList.size(); i++) {
                if (clientList.get(i).getId().equals(parameter.getClientId())) {
                    clientTicketList = clientList.get(i).getClientTicketList();
                }
            }    
        }
        return clientTicketList;
    }
    
}
