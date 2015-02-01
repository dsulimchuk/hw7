package com.farata.course.mwd.auction.service;

import com.farata.course.mwd.auction.data.DataEngine;
import com.farata.course.mwd.auction.engine.AuctionEngine;
import com.farata.course.mwd.auction.entity.Bid;
import com.farata.course.mwd.auction.entity.Product;
import com.farata.course.mwd.auction.entity.User;
import com.farata.course.mwd.auction.websocket.BidEndpoint;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Path("bid")
@Produces("application/json")
public class BidService {

    private DataEngine dataEngine;
    private AuctionEngine auction;
    private UserService userService;
    private AtomicInteger bidCounter;
    private BidEndpoint bidEndpoint;
    @Inject
    public void setBidEndpoint(BidEndpoint bidEndpoint) {
        this.bidEndpoint = bidEndpoint;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setAuction(AuctionEngine auction) {
        this.auction = auction;
    }

    @Inject
    public void setDataEngine(DataEngine dataEngine) {
        this.dataEngine = dataEngine;
    }

    public BidService() {
        bidCounter = new AtomicInteger(0);
    }

    private static final Logger log = Logger.getLogger(BidService.class.getName());

    // Set up all the default values
    private static final String DEFAULT_MESSAGE = "Hello, World!";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/test";
    private static final String DEFAULT_MESSAGE_COUNT = "57";
    private static final String DEFAULT_USERNAME = "quickstartUser";
    private static final String DEFAULT_PASSWORD = "quickstartPwd1!";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";

//    @Resource(lookup ="java:/ConnectionFactory")
//    ConnectionFactory connectionFactory;
//
//    @Resource(lookup = "queue/test")
//    Queue testQueue;


    // TODO: Provide actual implementation
    @GET
    @Path("/{id}/")
    public Bid getBid(@PathParam("id") int id, @Context HttpHeaders headers) {
        return new Bid(id, new BigDecimal(42));
    }

    // TODO: Provide actual implementation
    @POST
    @Consumes("application/json")
    public Response placeBid(JsonObject bidJson) {
        Product product = dataEngine.findProductById(bidJson.getInt("productId"));
        User user = userService.getUserByID(bidJson.getInt("userId"));
        BigDecimal amount = new BigDecimal(bidJson.getString("amount"));
        int desiredQuantity = bidJson.getInt("desiredQuantity");

        Bid bid = new Bid(bidCounter.incrementAndGet(), product, amount, desiredQuantity, user, ZonedDateTime.now(), false);
        //direct invoke engine
        auction.placeBid(bid);

        //sendBidToQueue(); // Send a message to the queue
        bidEndpoint.sendTimeToAll(bid.getProduct(), auction);
        return Response.ok().build();

    }


//
//    private void sendBidToQueue(){
//
//        try (JMSContext context =
//               connectionFactory.createContext(DEFAULT_USERNAME, DEFAULT_PASSWORD)) {
//            log.info("\n Sending Hello Bid message from BidService to the queue");
//
//            JMSProducer producer = context.createProducer();
//
//            producer.send(testQueue, "Hello Bid!");
//        }
//    }
}
