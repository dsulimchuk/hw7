package com.ds.auction
import com.ds.auction.model.Bid
import com.ds.auction.model.Product
import com.ds.auction.model.User
import com.ds.auction.service.Notification
import com.ds.auction.service.NotificationService
import spock.lang.Shared
import spock.lang.Title

import java.time.LocalDateTime
/**
 * Created by ds on 11/01/15.
 */

@Title("AuctionEngine Specification")
class AuctionEngineImplSpec extends spock.lang.Specification {

    @Shared
    def auction;

    def u = [new User("user1", "user2@ff.com", true),
            new User("user2", "user2@ff.com", true)
            ] as User[];

    def p = [
            new Product("Mac book pro 13", 1, LocalDateTime.now().plusHours(1), BigDecimal.valueOf(100), BigDecimal.valueOf(1000))
            ] as Product[];

    def setup() {
        //println "// run before every feature method"
        //auction = new AuctionEngine();
    }

    def "should send NotificationMsg.ERR_BID_LESS_MIN_PRICE to user"() {
        setup:
            def b1 = new Bid(p[0], BigDecimal.valueOf(1), 1, u[0]);
            def notServ = Spy(NotificationService, constructorArgs: []);
            auction = new AuctionEngineImpl(notServ);
        when:
            auction.placeBid(b1)
        then:
            1 * notServ.sendNotification(u[0], Notification.ERR_BID_LESS_MIN_PRICE)
    }

    def "should send NotificationMsg.OK_YOUR_BID_IS_WINNING to user"() {
        setup:
            def notServ = Spy(NotificationService, constructorArgs: []);
            auction = new AuctionEngineImpl(notServ);
            def b1 = new Bid(p[0], BigDecimal.valueOf(1020), 1, u[0]);
        when:
            auction.placeBid(b1)
            auction.placeBid(b1)
        then:
            1 * notServ.sendNotification(u[0], Notification.OK_YOUR_BID_IS_WINNING)
    }

    def "should send  NotificationMsg.NOTIF_YOUR_BID_IS_OVERBIDDED to user 3 times"() {
        setup:

            def notServ = Spy(NotificationService, constructorArgs: []);
            auction = new AuctionEngineImpl(notServ);
            def b1 = new Bid(p[0], BigDecimal.valueOf(200), 1, u[0]);
            def b2 = new Bid(p[0], BigDecimal.valueOf(201), 1, u[1]);
            def b3 = new Bid(p[0], BigDecimal.valueOf(202), 1, u[0]);
            def b4 = new Bid(p[0], BigDecimal.valueOf(204), 1, u[1]);
            def b5 = new Bid(p[0], BigDecimal.valueOf(210), 1, u[0]);
            def b6 = new Bid(p[0], BigDecimal.valueOf(500), 1, u[1]);
        when:
            auction.placeBid(b1)
            auction.placeBid(b2)
            auction.placeBid(b3)
            auction.placeBid(b4)
            auction.placeBid(b5)
            auction.placeBid(b6)
        then:
            3 * notServ.sendNotification(u[0], Notification.NOTIF_YOUR_BID_IS_OVERBIDDED)
            2 * notServ.sendNotification(u[1], Notification.NOTIF_YOUR_BID_IS_OVERBIDDED)
    }

    def "should send  NotificationMsg.ERR_AUCT_IS_CLOSE to user 1 times"() {
        setup:

            def notServ = Spy(NotificationService, constructorArgs: []);
            auction = new AuctionEngineImpl(notServ);
            def b1 = new Bid(p[0], BigDecimal.valueOf(900), 1, u[0]);
            def b2 = new Bid(p[0], BigDecimal.valueOf(1001), 1, u[1]);
            def b3 = new Bid(p[0], BigDecimal.valueOf(500), 1, u[0]);
        when:
            auction.placeBid(b1)
            auction.placeBid(b2)
            auction.placeBid(b3)

        then:
            1 * notServ.sendNotification(u[0], Notification.ERR_AUCT_IS_CLOSE)

    }
    def "should return true if auction is closed"() {
        setup:
            auction = new AuctionEngineImpl(new NotificationService());
            def b1 = new Bid(p[0], BigDecimal.valueOf(900), 1, u[0]);
            def b2 = new Bid(p[0], BigDecimal.valueOf(1000), 1, u[1]);
        when:
            auction.placeBid(b1);
        then:
            auction.auctionIsClosed(p[0]) == false

        when:
            auction.placeBid(b2);
        then:
            auction.auctionIsClosed(p[0]) == true
    }



}
