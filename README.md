Modern Web Application Development for Java Programmers Homework 7
==

###### Integrate developed earlier in this course AngularJS Product page with this endpoint:
1. The user select the product and places a bid
2. The AngularJS app creates a JSON object Bid
3. The AngularJS app makes a Rest call to the endpoint implemented in WildFly
4. The Java code validates the received Bid, and if the price below the minimal price on the Product, the bid is rejected. If the price is more than a reserved price, the user receives congratulation on purchasing the product. In any other case the Bid is added to the collection of Bids, which has to be re-sorted, and the top bid price and the top bidder’s ID is returned to the front end.
5. The AngularJS app displays the Bid status top bid and top bidder on the Product page.
6. Implement WebSocket API. Provide logic in a dedicated BidService on the client side.

###### to run demo:
1. In web folder run "npm install && bower install && grunt build".
2. In auction_jaxrs folder run "./gradlew build" and then manually deploy "auction_jaxrs-1.0.war" under WildFly
