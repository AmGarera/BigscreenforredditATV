package com.example.anthony.bigscreenforreddit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.AuthenticationMethod;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.http.oauth.OAuthHelper;
import net.dean.jraw.http.LoggingMode;
import java.net.URL;
import android.widget.ProgressBar;
/**
 * Created by Anthony on 6/18/2015.
 */
public class OAuth extends Activity {

    // Use an empty string as the client secret because "The 'password' for non-confidential clients
// (installed apps) is an empty string". See https://github.com/reddit/reddit/wiki/OAuth2#token-retrieval
    private static final String LOG_TAG = OAuth.class.getSimpleName();
    private static final String CLIENT_ID = "1g_2vCWlwRS_og";
    private static final String REDIRECT_URL = "http://127.0.0.1:3000/users/auth/reddit/callback";
    private static RedditClient reddit;
    private String mRefreshToken = "PUT_TOKEN_HERE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);

        // Create our RedditClient
        final RedditClient reddit = new RedditClient(UserAgent.of("com.example.anthony.bigscreenforreddit"));
        reddit.setLoggingMode(LoggingMode.ALWAYS);
        final OAuthHelper helper = reddit.getOAuthHelper();
        // This is Android, so our OAuth app should be an installed app.
        final Credentials credentials = Credentials.installedApp(getString(R.string.BigScreen), getString(R.string.redirectUrl));

        // Try to refresh the token
        if(!mRefreshToken.isEmpty()) {
            new TokenRefreshTask().execute();
            return;
        }
        // If this is true, then you will be able to refresh to access token
        boolean permanent = true;
        // OAuth2 scopes to request. See https://www.reddit.com/dev/api/oauth for a full list
        String[] scopes = {"identity", "read", "vote", "save", "mysubreddits"};

        URL authorizationUrl = helper.getAuthorizationUrl(credentials, permanent, scopes);
        // Load the authorization URL into the browser
        WebView wv = (WebView)findViewById(R.id.webview);
        wv.loadUrl(authorizationUrl.toExternalForm());
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.contains("code=")) {
                    Log.v(LOG_TAG, "WebView URL: " + url);
                    // We've detected the redirect URL
//
                    new UserChallengeTask(helper, credentials).execute(url);
//
                }
                // <assign fields to parameters>
            }
        });
    };

//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//    Log.v(LOG_TAG, "onCreateOptionsMenu()");
//    // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu,menu_main,menu);
//    return true;

    private  void  performRedditLogin() {

    }
    private final class TokenRefreshTask extends AsyncTask<String, Void, OAuthData> {

        @Override
        protected OAuthData doInBackground(String... params) {

            if (!mRefreshToken.isEmpty()) {
                final Credentials credentials = Credentials.installedApp(CLIENT_ID, REDIRECT_URL);
                OAuthHelper oAuthHelper = reddit.getOAuthHelper();
                oAuthHelper.setRefreshToken(mRefreshToken);
                try {
                    OAuthData finalData = oAuthHelper.refreshToken(credentials);
                    reddit.authenticate(finalData);
                    if (reddit.isAuthenticated()) {
                        Log.v(LOG_TAG, "Authenticated");
                    }
                } catch (OAuthException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }


    private final class UserChallengeTask extends AsyncTask<String, Void, OAuthData> {
        private OAuthHelper mOAuthHelper;
        private Credentials mCredentials;


        public UserChallengeTask(OAuthHelper helper, Credentials creds) {
            // <assign fields to parameters>
            mOAuthHelper = helper;
            mCredentials = creds;
        }

        @Override
        protected OAuthData doInBackground(String...Params) {
            //is the first setting
            Log.v(LOG_TAG, "doInBackground()");
            Log.v(LOG_TAG, "Params[0]: " + Params[0]);
            try {
                return mOAuthHelper.onUserChallenge(Params[0], mCredentials);
            } catch (IllegalStateException | NetworkException | OAuthException e) {
//Handle me Gracefully
                Log.e(LOG_TAG, "OAuth failed");
                Log.e(LOG_TAG, e.getMessage());}
            return null;
        }
        @Override
        protected void onPostExecute(OAuthData oAuthData) {
            Log.v(LOG_TAG, "onPostExecute()");
            if (oAuthData != null) {
                reddit.authenticate(oAuthData);
                Log.v(LOG_TAG,"Reddit client Authentication: " + reddit.isAuthenticated());
                //TODO: Save refresh token:
                String refreshToken = reddit.getOAuthData().getRefreshToken();
                Log.v(LOG_TAG, "Refresh Token: " + refreshToken);
            } else {
                Log.e(LOG_TAG, "Passed in OAuthData was null");
            }
        }
    }
}


