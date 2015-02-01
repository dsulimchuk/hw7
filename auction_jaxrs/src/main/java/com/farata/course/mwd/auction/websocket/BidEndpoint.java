package com.farata.course.mwd.auction.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// TODO: Pick one of
@ServerEndpoint("/bid")
public class BidEndpoint {

    private static Set<Session> allSessions;
    static ScheduledExecutorService timer =
            Executors.newSingleThreadScheduledExecutor();

    DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public transient List<Session> participantList = new ArrayList<>();

    @OnMessage
    public void onMessage(String textMessage, Session mySession) {
        try {
            mySession.getBasicRemote().sendText(
                "[Server speaking]: Got your message " + textMessage + "Sending it back to you");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.err.println("Closing: " + closeReason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable t) {
        System.err.println("Error: " + t.getLocalizedMessage());
    }

    @OnOpen
    public void showTime(Session session){
        allSessions = session.getOpenSessions();

        // start the scheduler on the very first connection
        // to call sendTimeToAll every second
        if (allSessions.size()==1){
            timer.scheduleAtFixedRate(
                    () -> sendTimeToAll(session),0,1, TimeUnit.SECONDS);
        }
    }

    private void sendTimeToAll(Session session){
        allSessions = session.getOpenSessions();
        for (Session sess: allSessions){
            try{
                sess.getBasicRemote().sendText("Local time: " +
                        LocalTime.now().format(timeFormatter));
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }


}
