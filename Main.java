/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticket;

import _142390iapb.ticketservice.AddClientRequest;
import _142390iapb.ticketservice.AddClientTicketRequest;
import _142390iapb.ticketservice.AddTicketRequest;
import _142390iapb.ticketservice.ClientType;
import _142390iapb.ticketservice.GetClientRequest;
import _142390iapb.ticketservice.GetTicketRequest;
import _142390iapb.ticketservice.GetTicketResponse;
import _142390iapb.ticketservice.TicketType;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Martin
 */
public class Main {
    public static void main(String[] args) throws DatatypeConfigurationException {
        String TOKEN = "salajane";
        // SOAP teenuse instansi loomine
        TicketWebService tws = new TicketWebService();
        // Requesti kokkupanemine
        AddClientRequest addClientRequest = new AddClientRequest();
        addClientRequest.setRequestId(BigInteger.valueOf(358));
        addClientRequest.setToken(TOKEN);
        addClientRequest.setFirstName("mihkel");
        addClientRequest.setLastName("raud");
        addClientRequest.setSex(addClientRequest.getSex().MALE);
        addClientRequest.setPhoneNumber("45633451");
        GregorianCalendar date = new GregorianCalendar();
        date.set(1969, 1, 18);
        XMLGregorianCalendar dateOfBirth = DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
        addClientRequest.setDateOfBirth(dateOfBirth);
        // Requesti tegemine
        tws.addClient(addClientRequest);
        
        AddTicketRequest atr = new AddTicketRequest();
        atr.setRequestId(BigInteger.valueOf(8321));
        atr.setToken(TOKEN);
        atr.setMuseumName("war");
        atr.setTicketName("family");
        
        GregorianCalendar date1 = new GregorianCalendar();
        date1.set(2016, 12, 15);
        XMLGregorianCalendar ticketDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(date1);
        atr.setTicketDate(ticketDate);
        
        GregorianCalendar date2 = new GregorianCalendar();
        date2.set(2017, 5, 15);
        XMLGregorianCalendar dueDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(date2);
        atr.setDueDate(dueDate);
        
        // Requesti tegemine
        tws.addTicket(atr);
        
        AddClientTicketRequest actr = new AddClientTicketRequest();
        actr.setRequestId(BigInteger.valueOf(314));
        actr.setToken(TOKEN);
        actr.setClientId(BigInteger.valueOf(1));
        actr.setTicketId(BigInteger.valueOf(1));
        actr.setQuantity(BigInteger.valueOf(4));
        
        // Requesti tegemine
        tws.addClientTicket(actr);
        
        // Küsin klienti id järgi
        GetClientRequest gcr = new GetClientRequest();
        gcr.setToken(TOKEN);
        gcr.setId(BigInteger.valueOf(1));
        ClientType client = tws.getClient(gcr).getClient();
        System.out.println("Client: ");
        System.out.println("kliendi eesnimi: " + client.getFirstName());
        System.out.println("kliendi perenimi: " + client.getLastName());
        if (client.getPhoneNumber() != null) {
            System.out.println("kliendi perenimi: " + client.getPhoneNumber());
        }
        System.out.println("kliendi sünnikuupäev: " + client.getDateOfBirth());

        

        
        // Küsin piletit id järgi
        GetTicketRequest gtr = new GetTicketRequest();
        gtr.setToken(TOKEN);
        gtr.setId(BigInteger.valueOf(1));
        System.out.println("Ticket: ");
        TicketType ticket = tws.getTicket(gtr).getTicket();
        System.out.println("muuseumi nimi: " + ticket.getMuseumName());
        System.out.println("pileti tüüp: " + ticket.getTicketName());
        System.out.println("pileti alguskuupäev: " + ticket.getTicketDate());
        System.out.println("pileti lõppkuupäev: " + ticket.getTicketDate());
    }  
}
