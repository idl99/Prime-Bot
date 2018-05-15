import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.util.List;

public class Main {
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param args search query
     */
    public static void main(String[] args) {

        TwitterFactory tf = new TwitterFactory();
        Twitter twitter = tf.getInstance();
        twitter.setOAuthConsumer("TsCqJshf3gl3Q10o5hdyZyyck","vqEG7IRVuH8EDjHA2dd515Ib9XAaFfy1Tydn5QUUCpmNjjOIiI");
        twitter.setOAuthAccessToken(new AccessToken("902734843372384256-LbNRBSiqdAx1hFqmEPMik6h8JiJYVfo",
                "9cyMxVJLBBsv1LefpKFwkS72W1wyHU5bhnpqvhUv2lIeO"));

        try {
            Query query = new Query("calculateprime");
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    int value = Integer.valueOf(tweet.getText().replaceAll("\\D+",""));
                    System.out.printf("%s (@%s) requested to calculate primes less than %d\n",
                            tweet.getUser().getName(),tweet.getUser().getScreenName(),value);
                    System.out.print("The primes are as follows: ");
                    for(int i=1; i<value;i++){
                        if(isPrime(i))
                            System.out.print(i+" ");
                    }
                    System.out.println();
                }
            } while ((query = result.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }

    public static boolean isPrime(int value){
        boolean isPrime = true;
        for(int divisor = 2; divisor <= value / 2; divisor++) {
            if (value % divisor == 0) {
                isPrime = false;
                break; // num is not a prime, no reason to continue checking
            }
        }
        return isPrime;
    }

}
