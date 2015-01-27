package com.ds.auction;


import com.ds.auction.model.Bid;
import com.ds.auction.model.Product;
import com.ds.auction.model.User;
import com.ds.auction.service.NotificationService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AuctionDemo {
    public static void main(String[] args) throws InterruptedException {
        User[] u = {
                new User("user1", "user2@ff.com", true),
                new User("user2", "user2@ff.com", true),
                new User("user3", "user3@ff.com", true),
                new User("user4", "user4@ff.com", true),
                new User("user5", "user5@ff.com", true),
        };

        List<Product> p = new LinkedList<>();
        p.add( new Product("Mac book pro 13", 1, LocalDateTime.now().plusHours(1), BigDecimal.valueOf(100), BigDecimal.valueOf(1000)));
        p.add( new Product("Skii boots Head", 3, LocalDateTime.now().plusHours(5), BigDecimal.valueOf(100), BigDecimal.valueOf(350)));
        p.add( new Product("Snowboard", 1, LocalDateTime.now().plusHours(2), BigDecimal.valueOf(100), BigDecimal.valueOf(400)));
        p.add( new Product("Snowboard Hql", 2, LocalDateTime.now().plusHours(2), BigDecimal.valueOf(100), BigDecimal.valueOf(465)));
        p.add( new Product("Snowboard MQL", 10, LocalDateTime.now().plusHours(2), BigDecimal.valueOf(100), BigDecimal.valueOf(450)));

        AuctionEngine auction = new AuctionEngineImpl(new NotificationService());

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
                new ScheduledThreadPoolExecutor(u.length);

        //starting new workers
        for (int i = 0; i < u.length; i++) {
            scheduledThreadPoolExecutor.scheduleWithFixedDelay(
                    new UserEmulator(u[i], p, auction),
                    i,
                    50,
                    TimeUnit.MILLISECONDS);
        }



        while(!scheduledThreadPoolExecutor.isTerminated()){
            //wait for all tasks to finish
            if (scheduledThreadPoolExecutor.getQueue().size() == 0) {
                scheduledThreadPoolExecutor.shutdown();
            }
            Thread.sleep(1000);
        }
        System.out.println("Finished all threads");
        System.out.println(auction);

    }



}

class UserEmulator implements Runnable{
    public static final int AMOUNT_CAN_BID_HIGHER = 10;
    private User user;
    private List<Product> products;
    private AuctionEngine auction;
    private Random rand;


    public UserEmulator(User user, List<Product> products, AuctionEngine auction) {
        this.user = user;
        this.products = products;
        this.auction = auction;
        rand = new Random();
    }

    @Override
    public void run() {
        try {
            if (products.size() == 0) {
                throw new RuntimeException("Stop!");
            }
            int prodNumber = rand.nextInt(products.size());
            Product product = products.get(prodNumber);
            int bound = product.getReservedPrice().intValue()
                    - product.getMinimalPrice().intValue() + 1;

            int nextInt = rand.nextInt(bound);
            Bid bid = new Bid(product,
                    BigDecimal.valueOf(nextInt + product.getMinimalPrice().intValue() + AMOUNT_CAN_BID_HIGHER),
                    1,
                    user);
            auction.placeBid(bid);
            if (auction.auctionIsClosed(product)) {
                products.remove(product);
            }
        }catch (Exception e) {
            //System.out.println(e);
            throw e;
        }



    }
}
