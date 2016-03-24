package com.example.mouadr.fs.Authentfication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.mouadr.fs.Authentfication.library.UserFunctions;
import com.example.mouadr.fs.R;

public class QuizActivity extends ActionBarActivity {

    private TextView quizQuestion;

    private RadioGroup radioGroup;
    private RadioButton optionOne;
    private RadioButton optionTwo;
    private RadioButton optionThree;
    private RadioButton optionFour;
    private static String KEY_SCORE = "score";
    private int currentQuizQuestion;
    private int quizCount;
    private int idques;
    String id,s;
    private QuizWrapper firstQuestion;
    private List<QuizWrapper> parsedObject;
    int val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        quizQuestion = (TextView)findViewById(R.id.quiz_question);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        optionOne = (RadioButton)findViewById(R.id.radio0);
        optionTwo = (RadioButton)findViewById(R.id.radio1);
        optionThree = (RadioButton)findViewById(R.id.radio2);
        optionFour = (RadioButton)findViewById(R.id.radio3);


        Button nextButton = (Button)findViewById(R.id.nextquiz);

        AsyncJsonObject asyncObject = new AsyncJsonObject();
        asyncObject.execute("");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioSelected = radioGroup.getCheckedRadioButtonId();
                int userSelection = getSelectedAnswer(radioSelected);

                int correctAnswerForQuestion = firstQuestion.getCorrectAnswer();

                id=getIntent().getStringExtra("idscore");
                Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.getscore(id);
                if(userSelection == correctAnswerForQuestion){
                    // correct answer



                    try {
                        JSONObject json_user = json.getJSONObject("user");
                        Toast.makeText(getApplication(),json_user.getString(KEY_SCORE),Toast.LENGTH_LONG).show();
                        s=json_user.getString(KEY_SCORE);
                        Toast.makeText(QuizActivity.this,"id of question is" +String.valueOf(idques), Toast.LENGTH_LONG).show();
                        val=1+Integer.parseInt(json_user.getString(KEY_SCORE));
                        userFunction.addnewscore(id,String.valueOf(val),String.valueOf(idques));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(QuizActivity.this, "You got the answer correct", Toast.LENGTH_LONG).show();
                    currentQuizQuestion++;
                    if(currentQuizQuestion >= quizCount){
                        Toast.makeText(QuizActivity.this, "End of the Quiz Questions", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),DashboardActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("id",id);
                        //i.putExtra("idques",idques);
                        startActivity(i);
                        return;
                    }
                    else{
                        firstQuestion = parsedObject.get(currentQuizQuestion);
                        quizQuestion.setText(firstQuestion.getQuestion());
                        String[] possibleAnswers = firstQuestion.getAnswers().split(",");
                        uncheckedRadioButton();
                        optionOne.setText(possibleAnswers[0]);
                        optionTwo.setText(possibleAnswers[1]);
                        optionThree.setText(possibleAnswers[2]);
                        optionFour.setText(possibleAnswers[3]);
                    }
                }
                else{
                    // failed question
                    try {
                        JSONObject json_user = json.getJSONObject("user");
                        s=json_user.getString(KEY_SCORE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    userFunction.addnewscore(id,s,String.valueOf(idques));
                    Toast.makeText(QuizActivity.this, "You chose the wrong answer", Toast.LENGTH_LONG).show();
                    currentQuizQuestion++;
                    if(currentQuizQuestion >= quizCount){
                        Toast.makeText(QuizActivity.this, "End of the Quiz Questions", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),DashboardActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("id",id);
                        startActivity(i);
                        return;
                    }
                    else{
                        firstQuestion = parsedObject.get(currentQuizQuestion);
                        quizQuestion.setText(firstQuestion.getQuestion());
                        String[] possibleAnswers = firstQuestion.getAnswers().split(",");
                        uncheckedRadioButton();
                        optionOne.setText(possibleAnswers[0]);
                        optionTwo.setText(possibleAnswers[1]);
                        optionThree.setText(possibleAnswers[2]);
                        optionFour.setText(possibleAnswers[3]);
                    }
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
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

    private class AsyncJsonObject extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost("http://10.0.2.2/quiz/quiz.php");
            String jsonResult = "";

            try {
                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                System.out.println("Returned Json object " + jsonResult.toString());

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return jsonResult;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(QuizActivity.this, "Downloading Quiz", "Wait....", true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            System.out.println("Resulted Value: " + result);
            parsedObject = returnParsedJsonObject(result);
            if(parsedObject == null){
                return;
            }
            quizCount = parsedObject.size();
            firstQuestion = parsedObject.get(0);
            idques=firstQuestion.getId();
            quizQuestion.setText(firstQuestion.getQuestion());
            String[] possibleAnswers = firstQuestion.getAnswers().split(",");
            optionOne.setText(possibleAnswers[0]);
            optionTwo.setText(possibleAnswers[1]);
            optionThree.setText(possibleAnswers[2]);
            optionFour.setText(possibleAnswers[3]);
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }
    }
    private List<QuizWrapper> returnParsedJsonObject(String result){

        List<QuizWrapper> jsonObject = new ArrayList<QuizWrapper>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        QuizWrapper newItemObject = null;

        try {
            resultObject = new JSONObject(result);
            System.out.println("Testing the water " + resultObject.toString());
            jsonArray = resultObject.optJSONArray("quiz_questions");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonChildNode = null;
            try {
                jsonChildNode = jsonArray.getJSONObject(i);
                int id = jsonChildNode.getInt("id");
                //idques=jsonChildNode.getInt("id");
                String question = jsonChildNode.getString("question");
                String answerOptions = jsonChildNode.getString("possible_answers");
                int correctAnswer = jsonChildNode.getInt("correct_answer");
                newItemObject = new QuizWrapper(id, question, answerOptions, correctAnswer);
                jsonObject.add(newItemObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private int getSelectedAnswer(int radioSelected){

        int answerSelected = 0;
        if(radioSelected == R.id.radio0){
            answerSelected = 1;
        }
        if(radioSelected == R.id.radio1){
            answerSelected = 2;
        }
        if(radioSelected == R.id.radio2){
            answerSelected = 3;
        }
        if(radioSelected == R.id.radio3){
            answerSelected = 4;
        }
        return answerSelected;
    }
    private void uncheckedRadioButton(){
        optionOne.setChecked(false);
        optionTwo.setChecked(false);
        optionThree.setChecked(false);
        optionFour.setChecked(false);
    }

}
