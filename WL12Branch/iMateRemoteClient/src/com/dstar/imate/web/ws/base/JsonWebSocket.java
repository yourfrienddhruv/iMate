package com.dstar.imate.web.ws.base;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.websockets.DefaultWebSocket;
import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocketException;
import org.glassfish.grizzly.websockets.WebSocketListener;

import com.dstar.imate.data.IData;
import com.dstar.imate.web.json.base.gson.Json;
import com.dstar.imate.web.ws.base.data.JsonResponse;

public abstract class JsonWebSocket extends DefaultWebSocket {
    private static final Logger logger = Grizzly.logger(JsonWebSocket.class);
    public JsonWebSocket(ProtocolHandler handler, WebSocketListener... listeners) {
        super(handler, listeners);
    }
    
    /**
     * Send the message in JSON encoding acceptable by browser's javascript.
     * @param resp 
    */
    private void send(JsonResponse<? extends IData> resp) {
        try {
            final String msg =  Json.to(resp);
            logger.log(Level.INFO, "<<== {0}",msg);
            send(msg);
        } catch (WebSocketException e) {
            logger.log(Level.SEVERE, "<!== Closing due to {0} for {1}" ,new Object[]{ e,resp});
            close(PROTOCOL_ERROR, e.getMessage());
        }
    }
    
    public void notify(JsonResponse<? extends IData> notif) {
        send(notif);
    }
    
    public void sendResponse(JsonResponse<? extends IData> resp){
    	send(resp);
    }
    protected String escapeHTML(String txt) {
        StringBuilder buffer = new StringBuilder(txt.length());

        for (int i = 0; i < txt.length(); i++) {
            char c = txt.charAt(i);
            switch (c) {
                case '\b':
                    buffer.append("\\b");
                    break;
                case '\f':
                    buffer.append("\\f");
                    break;
                case '\n':
                    buffer.append("<br />");
                    break;
                case '\r':
                    // ignore
                    break;
                case '\t':
                    buffer.append("\\t");
                    break;
                case '\'':
                    buffer.append("\\'");
                    break;
                case '\"':
                    buffer.append("\\\"");
                    break;
                case '\\':
                    buffer.append("\\\\");
                    break;
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                default:
                    buffer.append(c);
            }
        }

        return buffer.toString();
    }
}
