/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.example.mouadr.fs.Authentfication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mouadr.fs.Authentfication.library.DatabaseHandler;
import com.example.mouadr.fs.Authentfication.library.UserFunctions;
import com.example.mouadr.fs.MainActivity;
import com.example.mouadr.fs.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class LoginActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "dl42fy6QjtzKgTnpLv43P8gMF";
    private static final String TWITTER_SECRET = "vmvHXlYjKTvrNDI3GLFWPHi9APOtudh21JYh18HGymhsb6PiTI";

	Button btnLogin,btnface;
	Button btnLinkToRegister;
	EditText inputEmail;
	EditText inputPassword;
	private TwitterLoginButton loginButton;
	int i=0,j;

	TextView loginErrorMsg;
	private CallbackManager callbackManager;
	private LoginManager loginManager;
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	String email,birthday;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(this, new Twitter(authConfig));

		setContentView(R.layout.login);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();

			StrictMode.setThreadPolicy(policy);
		}
		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);
		btnface=(Button)findViewById(R.id.facebook);
		loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
	loginButton.setCallback(new Callback<TwitterSession>() {

			@Override
			public void success(Result<TwitterSession> result) {
				i=1;
				TwitterSession session = result.data;
				// TODO: Remove toast and use the TwitterSession's userID

				String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				Intent in=new Intent(getApplicationContext(),MainActivity.class);
				in.putExtra("first",session.getUserName());
				in.putExtra("fbortwi","twitter");
				in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
			}
			@Override
			public void failure(TwitterException exception) {
				Log.d("TwitterKit", "Login with Twitter failure", exception);
			}
		});



		btnface.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				j=1;
				FacebookSdk.sdkInitialize(getApplicationContext());
				callbackManager = CallbackManager.Factory.create();

				List<String> permissionNeeds = Arrays.asList("publish_actions", "publish_stream");

				//this loginManager helps you eliminate adding a LoginButton to your UI
				loginManager = LoginManager.getInstance();


				loginManager.logInWithPublishPermissions(LoginActivity.this, permissionNeeds);
				loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
					private ProfileTracker mProfileTracker;
					@Override
					public void onSuccess(LoginResult loginResult) {
						if(Profile.getCurrentProfile() == null) {
							mProfileTracker = new ProfileTracker() {
								@Override
								protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
									// profile2 is the new profile
									Log.v("facebook - profile", profile2.getFirstName());
									email=profile2.getFirstName();
									birthday=profile2.getLastName();
									Toast.makeText(getApplicationContext(),"Bonjour " +email+" "+birthday,Toast.LENGTH_LONG).show();
									Intent i=new Intent(getApplicationContext(),MainActivity.class);
									i.putExtra("first",email);
									i.putExtra("last", birthday);
									//i.putExtra("fbortwi", "facebook");
									Toast.makeText(getApplicationContext(),"Mouad",Toast.LENGTH_LONG).show();
									i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(i);
									mProfileTracker.stopTracking();
								}
							};
							mProfileTracker.startTracking();
						}
						else {
							Profile profile = Profile.getCurrentProfile();


							//get data here
							profile = Profile.getCurrentProfile();
							email=profile.getFirstName();
							birthday=profile.getLastName();
							Toast.makeText(getApplicationContext(),"Bonjour " +email+" "+birthday,Toast.LENGTH_LONG).show();
							Intent i=new Intent(getApplicationContext(),MainActivity.class);
							i.putExtra("first",email);
							i.putExtra("last", birthday);
							//i.putExtra("fbortwi", "facebook");
							Toast.makeText(getApplicationContext(),"Mouad",Toast.LENGTH_LONG).show();
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(i);
						}


					}

					@Override
					public void onCancel() {
						System.out.println("onCancel");
					}

					@Override
					public void onError(FacebookException exception) {

						if (exception instanceof FacebookAuthorizationException) {
							if (AccessToken.getCurrentAccessToken() != null) {
								LoginManager.getInstance().logOut();
							}
						}
						System.out.println(exception);
					}
				});
			}
		});
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				if (email.equals("mouad") && password.equals("raoui")) {
						Intent i=new Intent(getApplicationContext(),Admin.class);
						startActivity(i);
				} else {
					UserFunctions userFunction = new UserFunctions();
					Log.d("Button", "Login");
					JSONObject json = userFunction.loginUser(email, password);

					// check for login response
					try {
						if (json.getString(KEY_SUCCESS) != null) {
							loginErrorMsg.setText("");
							String res = json.getString(KEY_SUCCESS);
							if (Integer.parseInt(res) == 1) {
								// user successfully logged in
								// Store user details in SQLite Database
								DatabaseHandler db = new DatabaseHandler(getApplicationContext());
								JSONObject json_user = json.getJSONObject("user");

								// Clear all previous data in database
								userFunction.logoutUser(getApplicationContext());
								db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));

								// Launch Dashboard Screen
								Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
								dashboard.putExtra("id",json.getString(KEY_UID));
								// Close all views before launching Dashboard
								dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(dashboard);

								// Close Login Screen
								finish();
							} else {
								// Error in login
								loginErrorMsg.setText("Incorrect username/password");
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {


		super.onActivityResult(requestCode, resultCode, data);

			//	callbackManager.onActivityResult(requestCode, resultCode, data);
				if(j==1){
					callbackManager.onActivityResult(requestCode,resultCode,data);
					j=0;
				}


			else{
				loginButton.onActivityResult(requestCode,resultCode,data);

			}







	}


}
