package com.farata.course.mwd.auction.websocket;

import com.farata.course.mwd.auction.data.DataEngine;
import com.farata.course.mwd.auction.engine.AuctionEngine;
import com.farata.course.mwd.auction.entity.Product;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@ServerEndpoint("/api/ws")
public class BidEndpoint {
    private DataEngine dataEngine;

    @Inject
    public void setDataEngine(DataEngine dataEngine) {
        this.dataEngine = dataEngine;
    }

    private static Set<Session> allSessions;
    private static Map<Session, Product> sessionMap = new HashMap<>();

    static ScheduledExecutorService timer =
            Executors.newSingleThreadScheduledExecutor();

    static   DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public transient List<Session> participantList = new ArrayList<>();

    @OnMessage
    public void onMessage(String textMessage, Session mySession) {

//        try {
            sessionMap.put(mySession, dataEngine.findProductById(Integer.parseInt(textMessage)));
            System.out.println("CONNECTED " + sessionMap);
//            mySession.getBasicRemote().sendText(
//                    "[Server speaking]: Got your message " + textMessage + "Sending it back to you");

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
        System.out.println("open new session" + sessionMap);
    }

    public static void sendTimeToAll(Product product, AuctionEngine auction){

        Optional<Session> firstSession = sessionMap.keySet().stream()
                .filter(s -> s.isOpen())
                .findFirst();

        if (firstSession.isPresent()){
            allSessions = firstSession.get().getOpenSessions();

            for (Session sess: allSessions){
                if (!sessionMap.get(sess).equals(product))
                    continue;
                try{
                    JsonObject productReport = auction.getJsonReportForProduct(product).build();
                    sess.getBasicRemote().sendText(productReport.toString());
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
        }

    }


}
