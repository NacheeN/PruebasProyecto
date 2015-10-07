package chatsocket;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;


public class Configuracion extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
    	
    	//// Con esto obtengo la sesion http y la seteo a la configuracion   
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        config.getUserProperties().put("httpSession", httpSession);
        
        ////// Aqui agarramos los atributos seteados en sesion
        /*
     	System.out.println(httpSession.getAttribute("usuario"));
         */
        
        /**
         * Return a reference to the HttpSession that the web socket handshake that 
         * started this conversation was part of, if the implementation
         * is part of a Java EE web container.
         *
         * @return the http session or {@code null} if either the websocket
         * implementation is not part of a Java EE web container, or there is
         * no HttpSession associated with the opening handshake request.
         */
        
     
    }

}
