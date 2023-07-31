package jp.suntech.s22009.bmicalculators009;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextAge, editTextHeight, editTextWeight;
    private TextView textViewCategory, textViewIdealWeight, textViewYourCategory, textViewYourWeight, textViewunit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAge = findViewById(R.id.etAge);
        editTextHeight = findViewById(R.id.etHeight);
        editTextWeight = findViewById(R.id.etWeight);

        textViewCategory = findViewById(R.id.textViewCategory);
        textViewIdealWeight = findViewById(R.id.textViewIdealWeight);
        textViewYourCategory = findViewById(R.id.textViewYourCategory);
        textViewYourWeight = findViewById(R.id.textViewYourIdealWeight);
        textViewunit = findViewById(R.id.textViewunit);

        Button buttonCalculate = findViewById(R.id.btCal);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        Button buttonClear = findViewById(R.id.btClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void calculate() {
        String ageString = editTextAge.getText().toString();
        String heightString = editTextHeight.getText().toString();
        String weightString = editTextWeight.getText().toString();

        if (ageString.isEmpty() || heightString.isEmpty() || weightString.isEmpty()) {
            Toast.makeText(this, "すべてのフィールドを入力してください", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageString);
        double height = Double.parseDouble(heightString);
        double weight = Double.parseDouble(weightString);

        if (age < 6) {
            //年齢が6歳未満の場合はカウプ指数を表示
            double kaupIndex = calculateKaupIndex(weight, height, age);
            displayResult("カウプ指数", kaupIndex);
            showAlertDialog();
        } else if (age < 16) {
            //年齢が6歳以上16歳未満の場合はローレル指数を表示
            double lorentzIndex = calculateLorentzIndex(weight, height, age);
            displayResult("ローレル指数", lorentzIndex);
            showAlertDialog();
        } else {
            //年齢が16歳以上の場合はBMIを表示
            double bmiValue = calculateBMIValue(height, weight);
            displayResult("BMI値", bmiValue);
        }
    }



    private double calculateKaupIndex(double weight, double height, int age) {
        return weight / Math.pow(height / 100.0, 3);
    }
    private double calculateLorentzIndex(double weight, double height, int age) {
        return (height - 100) - ((height - 150) / 2.5) + ((age - 5) / 4);
    }

    private double calculateBMIValue(double height, double weight) {
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }

    private void displayResult(String indexName, double indexValue) {
        double idealWeight = calculateIdealWeight();
        String category = getBMICategory(indexValue);

        textViewYourCategory.setText("あなたの肥満度は");
        textViewCategory.setText(category);

        textViewYourWeight.setText("あなたの適正体重は");
        String idealWeightText = String.format("%.1f", idealWeight);
        textViewIdealWeight.setText(idealWeightText);
        textViewunit.setText("kg");

        //肥満度に応じて色を変更
        if (category.equals("肥満(4度)") || category.equals("肥満(3度)")) {
            textViewCategory.setTextColor(Color.RED);
        } else if (category.equals("肥満(2度)")) {
            textViewCategory.setTextColor(Color.parseColor("#FF8C00"));  //オレンジ
        } else {
            textViewCategory.setTextColor(Color.BLACK);
        }

        if (category.equals("肥満(4度)") || category.equals("肥満(3度)")) {
            textViewYourCategory.setTextColor(Color.RED);
        } else if (category.equals("肥満(2度)")) {
            textViewYourCategory.setTextColor(Color.parseColor("#FF8C00"));  //オレンジ
        } else {
            textViewYourCategory.setTextColor(Color.BLACK);
        }

        if (category.equals("肥満(4度)") || category.equals("肥満(3度)")) {
            textViewYourWeight.setTextColor(Color.RED);
        } else if (category.equals("肥満(2度)")) {
            textViewYourWeight.setTextColor(Color.parseColor("#FF8C00"));  //オレンジ
        } else {
            textViewYourWeight.setTextColor(Color.BLACK);
        }

        if (category.equals("肥満(4度)") || category.equals("肥満(3度)")) {
            textViewIdealWeight.setTextColor(Color.RED);
        } else if (category.equals("肥満(2度)")) {
            textViewIdealWeight.setTextColor(Color.parseColor("#FF8C00"));  //オレンジ
        } else {
            textViewIdealWeight.setTextColor(Color.BLACK);
        }
    }

    private String getBMICategory(double bmiValue) {
        if (bmiValue < 18.5) {
            return "低体重(痩せ型)";
        } else if (bmiValue < 25) {
            return "普通体重";
        } else if (bmiValue < 30) {
            return "肥満(1度)";
        } else if (bmiValue < 35) {
            return "肥満(2度)";
        } else if (bmiValue < 40) {
            return "肥満(3度)";
        } else {
            return "肥満(4度)";
        }
    }

    private double calculateIdealWeight() {
        double height = Double.parseDouble(editTextHeight.getText().toString());
        return 22 * Math.pow(height / 100.0, 2);
    }

    private void clearFields() {
        editTextAge.setText("");
        editTextHeight.setText("");
        editTextWeight.setText("");
        textViewYourCategory.setText("");
        textViewCategory.setText("");
        textViewYourWeight.setText("");
        textViewIdealWeight.setText("");
        textViewunit.setText("");
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告")
                .setMessage("適切な肥満度を求めるには、6歳未満の場合はカウプ指数が、6歳から15歳まではローレル指数が使われます。本アプリはBMIのみに対応しています。")
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int age = Integer.parseInt(editTextAge.getText().toString());
                        double height = Double.parseDouble(editTextHeight.getText().toString());
                        double weight = Double.parseDouble(editTextWeight.getText().toString());
                        String category = "";

                    }
                });
        builder.create().show();
    }
}
