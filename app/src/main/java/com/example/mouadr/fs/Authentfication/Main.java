package com.example.mouadr.fs.Authentfication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import com.example.mouadr.fs.Authentfication.library.UserFunctions;
import com.example.mouadr.fs.R;

public class Main extends ActionBarActivity {
    String id,idques;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Button startButton = (Button)findViewById(R.id.button);
       // idques=getIntent().getStringExtra("idques");
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.filter();
                JSONObject jsonre=userFunction.rechercherquizid();

                try {
                    JSONObject json_user = json.getJSONObject("user");
                    i=Integer.parseInt(json_user.getString("filtre"));

                if(i==1) {
                    id = getIntent().getStringExtra("idscore");
                    JSONObject jsonse = userFunction.filtresecond(id);
                    Toast.makeText(getApplication(),id,Toast.LENGTH_LONG).show();
                    JSONObject json_usersecond = jsonse.getJSONObject("user");
                    JSONObject jsonrz=jsonre.getJSONObject("user");
                    if(Integer.parseInt(json_usersecond.getString("idques"))!=Integer.parseInt(jsonrz.getString("idquiz"))){
                        Intent intent = new Intent(Main.this, QuizActivity.class);

                        intent.putExtra("idscore", id);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Vous avez deja repondu",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"Le quiz n'est pas dis",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(getApplicationContext(),DashboardActivity.class);
                    startActivity(i);
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
