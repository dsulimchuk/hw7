Modern Web Application Development for Java Programmers Homework 6
==

###### Using the provided classes in the package streams.auction write a Java program that will implement the auction bidding rules below. Use Streams API with the collection of Bid objects. Use Comparator and sort the stream.

+ Multiple people can bid on a Product. Just create a couple of Product instances with hardcoded data. Use TimerTask and random number generator to emulate bidding process.

+ A collection of Bid objects represents current bids

+ When a “user” places a new bid, add it to the collection of bids, and “send an email” (just print on the console) to all bidders who opted for receiving overbid emails.

+ If a bid is greater or equal to the Product reserved price, send the bidder a winning If a bid is less that a min Product price, send a bidder a sorry email.

+ Implement the test suite for auction prototype using Spock Framework

###### to run demo use "./gradlew run" or "gradle.bat run" on windows
