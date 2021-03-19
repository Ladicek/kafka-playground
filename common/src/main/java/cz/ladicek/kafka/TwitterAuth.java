package cz.ladicek.kafka;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class TwitterAuth {
    // we use the OAuth 1.0a variant of Twitter API,
    // because that's what Twitter4J has
    //
    // must obtain CONSUMER_KEY and CONSUMER_SECRET first
    // (e.g. use https://www.floodgap.com/software/ttytter/dist2/2.1.00.txt)
    //
    // when you have CONSUMER_KEY and CONSUMER_SECRET,
    // run the `main` method to obtain ACCESS_TOKEN and ACCESS_TOKEN_SECRET

    public static final String CONSUMER_KEY = "...";
    public static final String CONSUMER_SECRET = "...";

    public static final String ACCESS_TOKEN = "...";
    public static final String ACCESS_TOKEN_SECRET = "...";

    public static void main(String[] args) throws IOException, TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (accessToken == null) {
            System.out.println("Open the following URL and grant access to your account:");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("Enter the PIN (if available) or just hit Enter: ");
            String pin = br.readLine();
            try {
                if (pin.length() > 0) {
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                } else {
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException e) {
                if (e.getStatusCode() == 401) {
                    System.out.println("Unable to get the access token.");
                } else {
                    e.printStackTrace();
                }
            }
        }
        twitter.verifyCredentials();

        System.out.println("Access token: " + accessToken.getToken());
        System.out.println("Access token secret: " + accessToken.getTokenSecret());
    }
}
