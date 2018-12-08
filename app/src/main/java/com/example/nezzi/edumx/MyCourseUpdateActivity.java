package com.example.nezzi.edumx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nezzi.edumx.models.Course;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyCourseUpdateActivity extends AppCompatActivity {

    Course course;
    EditText updateTxtName, updateTxtDesc, updateTxtPrice, updateTxtDuration, updateTxtTopics, updateTxtRequirements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycourse_update);

        updateTxtName= findViewById(R.id.updateTxtName);
        updateTxtDesc= findViewById(R.id.updateTxtDesc);
        updateTxtPrice = findViewById(R.id.updateTxtPrice);
        updateTxtDuration  = findViewById(R.id.updateTxtDuration);
        updateTxtTopics = findViewById(R.id.updateTxtTopics);
        updateTxtRequirements = findViewById(R.id.updateTxtRequirements);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            course = (Course) intent.getSerializableExtra("course");
        }

        updateTxtName.setText(course.getName());
        updateTxtDesc.setText(course.getDescription());
        updateTxtPrice.setText(String.valueOf(course.getPrice()));
        updateTxtDuration.setText(String.valueOf(course.getDuration()));
        updateTxtRequirements.setText(course.getRequirements());
        updateTxtTopics.setText(course.getTopics());

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        MyCourseUpdateActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
        setTitle("Editar Curso");
    }

    public void onClick(View view) {
        updateCourse();
    }

    public void updateCourse() {
        String name = updateTxtName.getText().toString();
        String desc = updateTxtDesc.getText().toString();
        double price = Double.parseDouble(updateTxtPrice.getText().toString());
        int duration = Integer.parseInt(updateTxtDuration.getText().toString());
        String requirements = updateTxtRequirements.getText().toString();
        String topics = updateTxtTopics.getText().toString();
        final String accessToken = APIUtility.getAccesToken(this);

        Course course = new Course(name, desc, price, duration, requirements, topics);

        final ProgressDialog progressDialog = new ProgressDialog(MyCourseUpdateActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String jsonString =
                "{" +
                    "\"name\":\"" + name + "\", " +
                    "\"description\":\"" + desc + "\"," +
                    "\"price\":\"" + price + "\"," +
                    "\"duration\":\"" + duration + "\"," +
                    "\"requirements\":\"" + requirements + "\"," +
                    "\"topics\":\"" + topics + "\"" +
                "}";

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    APIUtility.COURSE_URL + this.course.getId(),
                    new JSONObject(jsonString),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            setResult(RESULT_OK, null);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LoginActivity", error.toString());
                            progressDialog.dismiss();
                            Toast.makeText(getBaseContext(), "Update failed", Toast.LENGTH_LONG).show();
                        }
                    }
            ) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();

                    params.put("Content-Type", "application/json");

                    if (accessToken != null) {
                        params.put("Authorization", "Bearer " + accessToken);
                    }

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException error) {
            Log.e("MyCourseUpdateActivity", error.toString());
            progressDialog.dismiss();
            Toast.makeText(getBaseContext(), "Update failed", Toast.LENGTH_LONG).show();
        }
    }
}
