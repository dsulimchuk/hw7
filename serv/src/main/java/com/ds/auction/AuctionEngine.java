package com.ds.auction;

import com.ds.auction.model.Bid;
import com.ds.auction.model.Product;

/**
 * Created by ds on 11/01/15.
 */
public interface AuctionEngine {
    void placeBid(Bid bid);

    boolean auctionIsClosed(Product product);
}
