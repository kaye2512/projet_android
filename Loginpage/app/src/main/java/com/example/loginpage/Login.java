package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {

    private EditText nEmaill;
    private EditText nPassword;
    private TextView nCreateText;
    private TextView nError;
    private Button nLogBtn;

    private String email;
    private String password;

    private DataBaseManager dataBaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nEmaill=findViewById(R.id.mail);
        nPassword=findViewById(R.id.mdp);
        nLogBtn=findViewById(R.id.logBtn);
        nCreateText=findViewById(R.id.creattext);
        nError=findViewById(R.id.errorCreateAcc);

        dataBaseManager = new DataBaseManager(getApplicationContext());



        nLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = nEmaill.getText().toString();
                password = nPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    nEmaill.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    nPassword.setError("Password is Required");
                    return;
                }


                 connectUser();

            }
        });

        nCreateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Regis_ter = new Intent(getApplicationContext(), Register.class);
                startActivity(Regis_ter);
                finish();
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
                interfaceActivity.putExtra("user email", email);
                startActivity(interfaceActivity);
                finish();
            }else {
                error = response.getString("error");
                nError.setVisibility(View.VISIBLE);
                nError.setText(error);
            }
        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void connectUser(){
        //10.0.2.2 correspond au localhost sur google chrome
        String url = "http://10.0.2.2/projet/php/server/action/login.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("mdp", password);
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