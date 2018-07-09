package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    EditText etHeight;
    EditText etWeight;
    TextView tvDate;
    TextView tvBMI;
    TextView tvDesc;
    Button btnCalc;
    Button btnReset;

    @Override
    protected void onPause() {
        super.onPause();
        String date = tvDate.getText().toString();
        String bmi = tvBMI.getText().toString();
        String description = tvDesc.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefedit = prefs.edit();

        prefedit.putString("Date",date);
        prefedit.putString("BMI",bmi);
        prefedit.putString("Description",description);

        prefedit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String Date = prefs.getString("Date","");
        String BMI = prefs.getString("BMI","");
        String Description = prefs.getString("Description","");
        tvBMI.setText(BMI);
        tvDate.setText(Date);
        tvDesc.setText(Description);
        // Commit Test

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etHeight = findViewById(R.id.editTextHeight);
        etWeight = findViewById(R.id.editTextWeight);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvDesc = findViewById(R.id.textViewDesc);
        btnCalc = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float height = Float.parseFloat(etHeight.getText().toString());
                float weight = Float.parseFloat(etWeight.getText().toString());
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time


                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                float BMI = weight/(height*height);

                String result;
                if(BMI < 18.5){
                    result = "You are underweight";
                }
                else if (BMI < 24.9){
                    result = "Your BMI is normal";
                }
                else if (BMI < 29.9){
                    result = "You are overweight";
                }
                else{
                    result = "You are obese";
                }


                etHeight.setText(null);
                etWeight.setText(null);
                tvBMI.setText(getString(R.string.tvbmi) + " " + Float.toString(BMI));
                tvDate.setText(getString(R.string.tvdate)+ " " + datetime);
                tvDesc.setText(result);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBMI.setText(getString(R.string.tvbmi));
                tvDate.setText(getString(R.string.tvdate));
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().clear().commit();
            }
        });



    }


}
