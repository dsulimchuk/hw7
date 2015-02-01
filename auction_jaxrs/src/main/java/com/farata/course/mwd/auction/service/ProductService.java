package com.farata.course.mwd.auction.service;

import com.farata.course.mwd.auction.data.DataEngine;
import com.farata.course.mwd.auction.engine.AuctionEngine;
import com.farata.course.mwd.auction.entity.Product;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;

@Path("/product")
@Produces("application/json")
public class ProductService {

    DataEngine dataEngine;
    private AuctionEngine auction;

    @Inject
    public void setAuction(AuctionEngine auction) {
        this.auction = auction;
    }

    @Inject
    public void setDataEngine(DataEngine dataEngine) {
        this.dataEngine = dataEngine;
    }

    @GET
    public List<Product> getAllProducts() {
        return dataEngine.findAllProducts();
    }

    @GET
    @Path("/featured")
    public Response getFeaturedProducts() {
        final JsonObjectBuilder jsonResult = itemsWithHeading("Featured products");
        return Response.ok(jsonResult.build()).build();

    }

    /**
     * TODO Dumb search implementation. Provide implementation in DataEngine
     *
     * @return
     */
    @GET
    @Path("/search")
    public Response getSearchResults(@QueryParam("product") String product,
                                     @QueryParam("category") String category,
                                     @QueryParam("maxCloseDate") String maxCloseDate_str,
                                     @QueryParam("numOfBids") int numOfBids,
                                     @QueryParam("lowPrice") double lowPrice,
                                     @QueryParam("highPrice") double highPrice) {
        LocalDate maxCloseDate = null;
        if (maxCloseDate_str != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y");
            maxCloseDate = LocalDate.parse(maxCloseDate_str, formatter);
        }

        Predicate<Product> prodNamePredicate = p  -> p.getTitle().contains(product);
        List<Product> filteredProducts = dataEngine.findAllProducts(prodNamePredicate);

        final JsonObjectBuilder jsonResult = addHeading("Search results", filteredProducts);
        return Response.ok(jsonResult.build()).build();

    }

    private JsonObjectBuilder itemsWithHeading(String heading) {
        JsonArrayBuilder itemsBuilder = Json.createArrayBuilder();
        dataEngine.findAllFeaturedProducts().forEach(product -> {
            itemsBuilder.add(product.getJsonObject());

        });

        return Json.createObjectBuilder()
            .add("heading", heading)
            .add("items", itemsBuilder);
    }

    private JsonObjectBuilder addHeading(String heading, List<Product> products) {
        JsonArrayBuilder itemsBuilder = Json.createArrayBuilder();
        products.forEach(product -> {
            itemsBuilder.add(product.getJsonObject());
        });

        return Json.createObjectBuilder()
                .add("heading", heading)
                .add("items", itemsBuilder);
    }
    @GET
    @Path("/{id}/")
    public Response getProductById(@PathParam("id") int productId, @Context HttpHeaders headers) {

        String userAgent = headers.getRequestHeader("user-agent").get(0);
        System.out.println("Got request form " + userAgent);
        Product product = dataEngine.findProductById(productId);


        if (product != null) {
            System.out.println("findProduct method has returned " + product
                .getTitle());


            return Response.ok(auction.getJsonReportForProduct(product).build()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/jaxb")
    public Product getProductByIdWithBinding(@PathParam("id") int productId,
        @Context HttpHeaders headers) {

        String userAgent = headers.getRequestHeader("user-agent").get(0);
        System.out.println("Got request form " + userAgent);
        Product product = dataEngine.findProductById(productId);


        if (product != null) {
            System.out.println("findProduct method has returned " + product
                .getTitle());
            return product;
        } else {
            return null;
        }
    }

}