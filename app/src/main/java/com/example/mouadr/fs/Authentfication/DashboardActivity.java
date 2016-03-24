/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.example.mouadr.fs.Authentfication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import com.example.mouadr.fs.Authentfication.library.UserFunctions;
import com.example.mouadr.fs.R;


public class DashboardActivity extends Activity {
	UserFunctions userFunctions;
	Button btnLogout,bt1,bt2;
	String id,idques;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	//	Bundle e=getIntent().getExtras();
	//	id= String.valueOf(e.getBundle("id"));
        /**
         * Dashboard Screen for the application
         * */        
        // Check login status in database

        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
        	setContentView(R.layout.dashboard);
			id=getIntent().getStringExtra("id");
			//idques=getIntent().getStringExtra("idques");
        	btnLogout = (Button) findViewById(R.id.btnLogout);
			bt1=(Button)findViewById(R.id.button3);
			bt2=(Button)findViewById(R.id.button4);
        	btnLogout.setOnClickListener(new View.OnClickListener() {
    			
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				userFunctions.logoutUser(getApplicationContext());
    				Intent login = new Intent(getApplicationContext(), LoginActivity.class);
    	        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	        	startActivity(login);
    	        	// Closing dashboard screen
    	        	finish();
    			}
    		});

			bt2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i=new Intent(getApplicationContext(),Main.class);
					Toast.makeText(getApplication(),id,Toast.LENGTH_LONG).show();
					i.putExtra("idscore",id);
					//i.putExtra("idques",idques);
					startActivity(i);
				}
			});
        	
        }else{
        	// user is not logged in show login screen
        	Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(login);
        	// Closing dashboard screen
        	finish();
        }
        
        
        
        
    }
}