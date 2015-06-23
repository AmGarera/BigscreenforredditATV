package com.example.anthony.bigscreenforreddit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.http.oauth.OAuthHelper;

import java.net.URL;

/**
 * Created by Anthony on 6/18/2015.
 */
public class OAuth extends Activity {
        private static final String REDIRECT_URL = "https://ssl.reddit.com/api/login";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login2);

            // Create our RedditClient
            RedditClient reddit = new RedditClient(UserAgent.of("com.example.anthony.bigscreenforreddit"));
            final OAuthHelper helper = reddit.getOAuthHelper();
            // This is Android, so our OAuth app should be an installed app.
            final Credentials credentials = Credentials.installedApp("username", "password");

            // If this is true, then you will be able to refresh to access token
            boolean permanent = true;
            // OAuth2 scopes to request. See https://www.reddit.com/dev/api/oauth for a full list
            String[] scopes = {"identity", "read"};

            URL authorizationUrl = helper.getAuthorizationUrl(credentials, permanent, scopes);
            // Load the authorization URL into the browser
            WebView wv = (WebView)findViewById(R.id.webView);
            wv.loadUrl(authorizationUrl.toExternalForm());
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    if (url.contains("code=")) {
                        // We've detected the redirect URL
                        new UserChallengeTask(helper, credentials).execute(url);
                    }
                }
            });
        }

        private static final class UserChallengeTask extends AsyncTask<String, Void, OAuthData> {
            private OAuthHelper helper;
            private Credentials creds;
            private RedditClient reddit;

            public UserChallengeTask(OAuthHelper helper, Credentials creds) {
                // <assign fields to parameters>
            }

            @Override
            protected OAuthData doInBackground(String... params) {
                //is the first setting
                try {

                    return helper.onUserChallenge(REDIRECT_URL, creds);
                } 
                
                catch (NetworkException | OAuthException e) {
                    // Handle me gracefully
                    return null;
                }
            }

            @Override
            protected void onPostExecute(OAuthData oAuthData) {
                reddit.authenticate(oAuthData);
            }
        }
    }
