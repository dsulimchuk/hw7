package com.farata.course.mwd.auction.engine;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by ds on 31/01/15.
 */
@Path("tst")
@Produces("application/json")
public class Tst {


    private AuctionEngine engine;

    public AuctionEngine getEngine() {
        return engine;
    }
    @Inject
    public void setEngine(AuctionEngine engine) {
        this.engine = engine;
    }

    @GET
    public String testMe(){
        return "dsd";
    }
}
