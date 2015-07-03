package com.example.anthony.bigscreenforreddit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.AuthenticationMethod;
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

// Use an empty string as the client secret because "The 'password' for non-confidential clients
// (installed apps) is an empty string". See https://github.com/reddit/reddit/wiki/OAuth2#token-retrieval
private static final String LOG_TAG = OAuth.class.getSimpleName();
    private static final String REDIRECT_URL = "http://localhost";
    private TextView mTextView;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login2);

            // Create our RedditClient
            final RedditClient reddit = new RedditClient(UserAgent.of("com.example.anthony.bigscreenforreddit"));
            final OAuthHelper helper = reddit.getOAuthHelper();
            // This is Android, so our OAuth app should be an installed app.
            final Credentials credentials = Credentials.installedApp(getString(R.string.BigScreen), getString(R.string.redirectUrl));

            // If this is true, then you will be able to refresh to access token
            boolean permanent = true;
            // OAuth2 scopes to request. See https://www.reddit.com/dev/api/oauth for a full list
            String[] scopes = {"identity", "read"};

            URL authorizationUrl = helper.getAuthorizationUrl(credentials, permanent, scopes);
            // Load the authorization URL into the browser
            WebView wv = (WebView)findViewById(R.id.webview);
            wv.loadUrl(authorizationUrl.toExternalForm());
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    if (url.contains("code=")) {
                        // We've detected the redirect URL
                        final OAuthHelper helper = reddit.getOAuthHelper();
                        new UserChallengeTask(helper, credentials).execute(url);
                        {

                        }
                        }
                            // <assign fields to parameters>
                    }
                });
            };



        private static final class UserChallengeTask extends AsyncTask<String, Void, OAuthData> {
            private OAuthHelper helper;
            private Credentials mCredentials;
            private RedditClient reddit;

            public UserChallengeTask(OAuthHelper oAuthHelper, Credentials mCredentials) {
                // <assign fields to parameters>



            }

            @Override
            protected OAuthData doInBackground(String...Params) {
                //is the first setting
                Log.v(LOG_TAG, "doInBackground()");
                Log.v(LOG_TAG, "params[0]: " + Params[0]);
                try {

                    return helper.onUserChallenge(Params[0], mCredentials);
                } 
                
                catch (NetworkException | OAuthException e) {
                    // Handle me gracefully
                    Log.e(LOG_TAG, "OAuth failed");
                    Log.e(LOG_TAG, e.getMessage());
                    }
                    return null;

            }

            @Override
            protected void onPostExecute(OAuthData oAuthData) {
                Log.v(LOG_TAG, "onPostExecute()");
                if (oAuthData != null) {
                    reddit.authenticate(oAuthData);
                    Log.v(LOG_TAG, "Reddit client authentication: " + reddit.isAuthenticated());
                    // mTextView.setText("Logged in");
                    //TODO: Save refresh token:
                    String refreshToken = reddit.getOAuthData().getRefreshToken();
                    Log.v(LOG_TAG, "Refresh Token: " + refreshToken);
                } else {
                    Log.e(LOG_TAG, "Passed in OAuthData was null");
                }
            }
        }
    }
