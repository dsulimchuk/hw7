package com.farata.course.mwd.auction.engine;


import com.farata.course.mwd.auction.entity.Bid;
import com.farata.course.mwd.auction.entity.Product;

import javax.json.JsonObjectBuilder;
import java.util.List;

/**
 * Created by ds on 11/01/15.
 */
public interface AuctionEngine {
    void placeBid(Bid bid);

    boolean auctionIsClosed(Product product);

    List<Bid> getBidsForProduct(Product product);

    public int getQuantityLeft(Product product);

    public JsonObjectBuilder getJsonReportForProduct(Product product);
}
