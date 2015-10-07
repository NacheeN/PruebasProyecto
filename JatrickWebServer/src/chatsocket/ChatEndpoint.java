package chatsocket;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;


//ws://host/contextPath/websocket/[username].

@ServerEndpoint(value = "/chat/{room}", configurator=Configuracion.class)
public class ChatEndpoint {
	
	private EndpointConfig config;		
	private Session wsSession;
    private HttpSession httpSession; 	
	
	private static final String USERNAME_KEY = "username";
	private static Map<String, Session> clients = Collections.synchronizedMap(new LinkedHashMap<String, Session>());
	
	private String join(Collection<?> s, String delimiter) {
	     StringBuilder builder = new StringBuilder();
	     Iterator<?> iter = s.iterator();
	     while (iter.hasNext()) {
	         builder.append(iter.next());
	         if (!iter.hasNext()) {
	           break;                  
	         }
	         builder.append(delimiter);
	     }
	     return builder.toString();
	 }
	
	
	@OnOpen
	public void onOpen(@PathParam("room") String room, Session session,EndpointConfig config) throws Exception {
		
		// el session que viene por paramtero, es la session pero dentro del contexto de websockets, no la de http.
		// para conseguir la sesio se recurre al parametro que se seteo 			
		this.httpSession = (HttpSession) config.getUserProperties().get("httpSession");
		
		// Con "String username = (String) this.httpSession.getAttribute("usuario")" accedemos a los datos del usuario por sesion y no por parametro, 		
		//permitiendo usar el @PathParam para otro dato �til en el futuro
		//System.out.println(this.httpSession.getAttribute("usuario"));
		
		String username = (String) this.httpSession.getAttribute("usuario");
		
		
/*
		
		System.out.println(this.httpSession);
		System.out.println(this.wsSession);
	*/		
		
		// Se guarda para ver si se precisa en los otros metodos, por ejemplo en el onMessage
		 this.config = config;
		 		

	    //Add the new socket to the collection
		   clients.put(username, session);		
	    
	    //also set username property of the session.
	    //so when there a new message from a particular socket's session obj
	    //we can get the username whom send the message
	  
	    session.getUserProperties().put(USERNAME_KEY, username);
	    
	    // guardo la sesion de socket
	 		this.wsSession = session;

	    //Give a list current online users to the new socket connection
	    //because we store username as the key of the map, we can get all
	    //  username list from the map's keySet
	    String response = "newUser|" + this.join(clients.keySet(),"|");
	    
	    session.getBasicRemote().sendText(response);

	    //Loop through all socket's session obj, then send a text message
	    for (Session client : clients.values()) {
	        if(client == session) 
	        	continue;
	        client.getBasicRemote().sendText("newUser|" + username);
	    }
	}

	@OnMessage
	public void onMessage(Session session, String message) throws Exception {
		
		
		/*
		for (Session session : userSession.getOpenSessions()) {
            if (session.isOpen())
                session.getAsyncRemote().sendText(message);
        }
		*/
		
	    //Extract the information of incoming message.
	    //Message format: 'From|Message'
	    //so we split the incoming message with '|' token
	    //to get the destination and message content data
		 System.out.println(message);
		
	    String[] data = message.split("\\|");
	    String destination = data[0];
	    String messageContent = data[1];
	    
	    //Date d = new Date();

	    //Retrieve the sender's username from it's property
	    String sender = (String)session.getUserProperties().get(USERNAME_KEY);

	    //Deliver the message according to the destination
	    //Outgoing Message format: "message|sender|content|messageType?"
	    //the message type is optional, if the message is intended to be broadcast
	    //  then the message type value is "|all"
	    if(destination.equals("all")) {
	        //if the destination chat is 'all', then we broadcast the message
	        for (Session client : clients.values()) {
	            if(client.equals(session)) continue;
	            client.getBasicRemote().sendText("message|" + sender + "|" + messageContent + "|all" );
	        }
	    } else {
	        //find the username to be sent, then deliver the message
	        Session client = clients.get(destination);
	        String response = "message|" + sender + "|" + messageContent;
	        client.getBasicRemote().sendText(response);
	    }
	    
	    
	    /*
	       wsSession.getBasicRemote().sendText(msg); 
	     * 
	     */
	    
	}
	

	@OnClose
	public void onClose(Session session) throws Exception {
		
		System.out.println("Cerro");
		//this.httpSession = (HttpSession) config.getUserProperties().get("httpSession");
		
	    //remove client from collecton  
	    String username = (String)session.getUserProperties().get(USERNAME_KEY);
	    clients.remove(username);
	    
	    //broadcast to all people, that the current user is leaving the chat room
	    for (Session client : clients.values()) {
	        client.getBasicRemote().sendText("removeUser|" + username);
	    }
	}
	
	
	
	
	
	
	
	
	
	
}
