package com.farata.course.mwd.auction.engine;


import com.farata.course.mwd.auction.engine.service.Notification;
import com.farata.course.mwd.auction.engine.service.NotificationService;
import com.farata.course.mwd.auction.entity.Bid;
import com.farata.course.mwd.auction.entity.Product;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.math.BigDecimal;
import java.util.*;

@Singleton
public class AuctionEngineImpl implements AuctionEngine {

    private NotificationService notificationService;


    private Map<Product, List<Bid>> currentBids = new HashMap<>();

    public AuctionEngineImpl() {
        System.out.println("AuctionEngineImpl");
    }

    @Inject
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    //ENGINE
    synchronized public void placeBid(Bid bid) {
        System.out.println("call placeBid " + bid);
        List<Bid> bids = getBidsForProduct(bid.getProduct());
        if (bids.contains(bid)) {
            return;
        }

        //check bid date
        if (bid.getBidTime().compareTo(bid.getProduct().getAuctionEndTime()) > 0) {
            notificationService.sendNotification(bid.getUser(), Notification.ERR_AUCT_IS_CLOSE);
            return;
        }

        //check auction product available quantity
        if (auctionIsClosed(bid.getProduct())) {
            notificationService.sendNotification(bid.getUser(), Notification.ERR_AUCT_IS_CLOSE);
            return;
        }


        //If a bid is less that a min Product price, send a bidder a sorry email.
        if (bid.getAmount().compareTo(bid.getProduct().getMinimalPrice()) < 0) {
            notificationService.sendNotification(bid.getUser(), Notification.ERR_BID_LESS_MIN_PRICE);
            return;
        }

        bids.add(bid);

        Optional<BigDecimal> currentMax = bids.stream()
                .filter(b -> b != bid && b.getAmount().compareTo(b.getProduct().getReservedPrice()) < 0)
                .map(b -> b.getAmount())
                .reduce((b1, b2) -> b1.compareTo(b2) > 0 ? b1 : b2);

        boolean bidGrThanReservedPrice = bid.getAmount().compareTo(bid.getProduct().getReservedPrice()) >= 0;

        if (!currentMax.isPresent()
                || bid.getAmount().compareTo(currentMax.get()) > 0
                || bidGrThanReservedPrice) {

            //If a bid is greater or equal to the Product reserved price, send the bidder a winning
            if (bidGrThanReservedPrice && (bid.getProduct().getQuantity() - getWinnedQty(bids)) >= bid.getDesiredQuantity()) {
                bid.setWinning(true);
                notificationService.sendNotification(bid.getUser(), Notification.OK_YOUR_BID_IS_WINNING);
            }


            //notify bidders who opted for receiving overbid emails.
            bids.stream()
                    .filter((b) -> b != bid && b.getUser() != bid.getUser())
                    .map(b -> b.getUser())
                    .distinct()
                    .forEach((u) -> {
                        if (u.isHasOverbidNotifications()) {
                            notificationService.sendNotification(u, Notification.NOTIF_YOUR_BID_IS_OVERBIDDED);
                        }
                    });
        }
    }

    @Override
    synchronized public boolean auctionIsClosed(Product product) {
        return (product.getQuantity() - getWinnedQty(getBidsForProduct(product))) <= 0;
    }

    private int getWinnedQty(List<Bid> bids) {
        Optional<Integer> qty = bids.stream()
                .filter(b -> b.isWinning())
                .map(b -> b.getDesiredQuantity())
                .reduce(Integer::sum);
        return qty.isPresent() ? qty.get() : 0;
    }

    @Override
    public int getQuantityLeft(Product product) {
        if (product == null) {
            return 0;
        }
        List<Bid> bids = getBidsForProduct(product);
        return product.getQuantity() - getWinnedQty(bids);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("AuctionEngine winning bids = {\n");
        currentBids.keySet()
                .stream()
                .forEach((product) -> {
                    stringBuffer.append(product);
                    stringBuffer.append("[");
                    getBidsForProduct(product)
                            .stream()
                            .filter((bid) -> bid.isWinning() == true)
                            .forEach((bid) -> stringBuffer.append("\n\t").append(bid).append(','));
                    stringBuffer.append("]\n");

                });
        stringBuffer.append("}");

        return stringBuffer.toString();
    }

    public List<Bid> getBidsForProduct(Product product) {
        List<Bid> bids = currentBids.get(product);

        if (bids == null) {
           bids = new LinkedList<>();
           currentBids.put(product, bids);
        }

        return bids;
    }

    public JsonObjectBuilder getJsonReportForProduct(Product product) {
        JsonObjectBuilder productJsonBuilder = product.getJsonObjectBuilder();


        JsonArrayBuilder bidsJsonArrayBuilder = Json.createArrayBuilder();
        List<Bid> bidsForProduct = getBidsForProduct(product);
        bidsForProduct.stream().forEach(b -> {
            bidsJsonArrayBuilder.add(b.getJsonObject());
        });
        JsonArray bidsArray = bidsJsonArrayBuilder.build();
        productJsonBuilder.add("bids", bidsArray);
        productJsonBuilder.add("bidsQty", bidsArray.size());
        productJsonBuilder.add("quantityLeft", getQuantityLeft(product));
        productJsonBuilder.add("auctionIsClosed", auctionIsClosed(product));

        return productJsonBuilder;

    }

}
