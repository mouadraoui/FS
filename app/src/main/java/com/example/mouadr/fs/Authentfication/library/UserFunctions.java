/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.example.mouadr.fs.Authentfication.library;

import android.content.Context;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFunctions {
	
	private JSONParser jsonParser;
	
	private static String loginURL = "http://10.0.2.2/android_login_api/";
	private static String registerURL = "http://10.0.2.2/android_login_api/";
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String score_tag = "score";
	private static String score_somme = "scoresomme";
	private static String score_new = "scorenew";
	private static String filter = "filtre";
	private static String filtersecond = "filtresecond";
	private static String recherchequiz= "quizrecher";
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password,String localisation){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("localisation", localisation));
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// return json
		return json;
	}
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	public JSONObject addscore(String id,String score,String ques){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", score_tag));
		params.add(new BasicNameValuePair("scorenumber",score));
		params.add(new BasicNameValuePair("iduser", id));
		params.add(new BasicNameValuePair("idques", ques));
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;


	}
	public JSONObject getscore(String id){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", score_somme));
		params.add(new BasicNameValuePair("scorenumber",id));
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;

	}
	public JSONObject addnewscore(String id,String scorenew,String quest){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", score_new));
		params.add(new BasicNameValuePair("scorenumber",scorenew));
		params.add(new BasicNameValuePair("iduser", id));
		params.add(new BasicNameValuePair("questionid", quest));
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;
	}
	public JSONObject filter(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", filter));

		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;
	}
	public  JSONObject filtresecond(String id){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", filtersecond));
		params.add(new BasicNameValuePair("iduser",id));

		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;
	}
	public JSONObject rechercherquizid(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", recherchequiz));
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;
	}
}
