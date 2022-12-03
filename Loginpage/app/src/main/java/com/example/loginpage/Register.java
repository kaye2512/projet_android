package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    private EditText nFullName;
    private EditText nLastName;
    private EditText nEmail;
    private EditText nPassWord;
    private EditText nDate;
    private Button nRegisterBtn;
    private TextView nlogin;
    private ProgressBar progressBar;
    private TextView nErreur;
    private String nom;
    private String prenom;
    private String mail;
    private String password;
    private String dateN;
    private DataBaseManager dataBaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nFullName = findViewById(R.id.fullname);
        nLastName = findViewById(R.id.lastname);
        nEmail = findViewById(R.id.email);
        nPassWord = findViewById(R.id.password);
        nDate = findViewById(R.id.date);
        nRegisterBtn = findViewById(R.id.registerBtn);
        nlogin = findViewById(R.id.createtext);
        progressBar = findViewById(R.id.progressBar);
        nErreur = findViewById(R.id.errorAddAcc);
        dataBaseManager = new DataBaseManager(getApplicationContext());


        nRegisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                nom = nFullName.getText().toString();
                prenom = nLastName.getText().toString();
                mail = nEmail.getText().toString();
                password = nPassWord.getText().toString();
                dateN = nDate.getText().toString();

                if (TextUtils.isEmpty(nom)){
                    nFullName.setError("name is Required");
                    return;
                }
                if (TextUtils.isEmpty(prenom)){
                    nLastName.setError("last name is Required");
                    return;
                }
                if (TextUtils.isEmpty(mail)){
                    nEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    nPassWord.setError("Password is Required");
                    return;
                }

                if (password.length() < 6){
                    nPassWord.setError("Password Must be >= 6 character");
                    return;
                }
                if (TextUtils.isEmpty(dateN)){
                    nDate.setError("birthday is Required");
                    return;
                }

                createAccount();
                // Lancer la requete pour connecter l'utilisateur
                progressBar.setVisibility(View.VISIBLE);



            }
        });

        nlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent Log_in = new Intent(getApplicationContext(), Login.class);
                startActivity(Log_in);
            }
        });

    }

    public void onApiResponse(JSONObject response){
        Boolean success = null;
        String error= "";

        try {
            success = response.getBoolean("success");
            if (success == true){
                Intent interfaceActivity = new Intent(getApplicationContext(), InterfaceActivity.class);
                interfaceActivity.putExtra("user email", mail);
                startActivity(interfaceActivity);
                finish();
            }else {
                error = response.getString("error");
                nErreur.setVisibility(View.VISIBLE);
                nErreur.setText(error);
            }
        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void createAccount(){
        String url = "http://10.0.2.2/projet/php/server/action/register.php";

        Map<String, String> params = new HashMap<>();
        params.put("nom", nom);
        params.put("prenom", prenom);
        params.put("email", mail);
        params.put("mdp", password);
        params.put("dateN", dateN);
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onApiResponse(response);
                Toast.makeText(getApplicationContext(), "OPERATION SUCCEED", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        dataBaseManager.queue.add(jsonObjectRequest);
    }
}