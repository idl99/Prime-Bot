/*
* Importing the Twitter4J library
* Twitter4J is an unofficial Java library for the Twitter API.
* Using Twitter 4J this application has being integrated with the Twitter service.
* An Open Source Library distributed under the Apache License 2.0
* Source code available at Github on https://github.com/yusuke/twitter4j/
*/
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        TwitterFactory tf = new TwitterFactory();

        Twitter twitter = tf.getInstance();

        // Insert your OuthAuthConsumer as parameter
        // Test OAuthConsumer has been removed due to security reasons
        twitter.setOAuthConsumer("","");

        twitter.setOAuthAccessToken(new AccessToken("","")); // Insert your OuthAuthAccessTokenString as parameter
                                                             // Test OAuthAccessToken has been removed due to security reasons

        try {
            // Search query filter string
            // Tweets containing the string will be returned
            // Using string to filter tweets as numbers and regex can't be used to filter in Twitter API
            Query query = new Query("calculateprime");

            QueryResult result;

            do {

                // Execute search query on Twitter API
                result = twitter.search(query);

                // Gets a list of tweets from the query response
                List<Status> tweets = result.getTweets();

                for (Status tweet : tweets) {
                    // Iterating through each filtered tweet
                    int value = Integer.valueOf(tweet.getText().replaceAll("\\D+",""));
                    System.out.printf("%s (@%s) requested to calculate primes less than %d\n",
                            tweet.getUser().getName(),tweet.getUser().getScreenName(),value);
                    System.out.print("The primes are as follows: ");

                    // Loop which iterates through numbers upto value given by user
                    // Prints iteration value (i) if it's a prime number.
                    for(int i=1; i<value;i++){
                        if(isPrime(i))
                            System.out.print(i+" ");
                    }
                    System.out.println();
                }

            } while ((query = result.nextQuery()) != null);

            System.exit(0);

        } catch (TwitterException e) {
            e.printStackTrace();
            System.out.println("Failed to search tweets: " + e.getMessage());
            System.exit(-1);
        }
    }

    public static boolean isPrime(int value){
        // Method to check if number isPrime
        boolean isPrime = true;
        for(int divisor = 2; divisor <= value / 2; divisor++) {
            if (value % divisor == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }

}
