package com.farata.course.mwd.auction.websocket;

import javax.websocket.*;
import java.io.IOException;

public class BidEndpointApi extends Endpoint {
    @Override
    public void onClose(Session session, CloseReason closeReason) {
        System.err.println("Closing: " + closeReason.getReasonPhrase());
    }

    @Override
    public void onError(Session session, Throwable t) {
        System.err.println("Error: " + t.getLocalizedMessage());
    }

    @Override public void onOpen(Session session, EndpointConfig config) {
        final Session mySession = session;
        session
            .addMessageHandler((MessageHandler.Whole<String>) new MessageHandler.Whole<String>() {
                @Override public void onMessage(String message) {
                    try {
                        mySession.getBasicRemote().sendText(
                            "[Server speaking]: Got your message" + message
                                + "Sending it back to you");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
    }
}
