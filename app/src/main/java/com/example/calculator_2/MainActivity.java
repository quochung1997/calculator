package com.example.calculator_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // statics
    private static final int BRACKET_OPEN = 1;
    private static final int BRACKET_CLOSE = 2;

    // components
    private Button[] btnNumbers;
    private Button btnPlus, btnMinus, btnMulti, btnDivide, btnBracket, btnEqual, btnDel;
    private CheckBox cbHandModel;
    private TextView tvCalculation, tvResult;

    //variables
    private String calculation;
    private boolean isNewCalculation;
    private int bracketStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // request permission
        requestPermission();

        // components initialization
        initComponents();

        // calculation initialization
        calculation = "";
        isNewCalculation = true;
        bracketStatus = BRACKET_OPEN;

        // action performed
        initActionPerformed();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "ACCESSED", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(MainActivity.this,
                    "Please provide this permission to use powerful voice command function",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void initComponents() {
        initNumberButtons();

        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);
        btnMulti = findViewById(R.id.btn_multi);
        btnDivide = findViewById(R.id.btn_divide);
        btnBracket = findViewById(R.id.btn_bracket);
        btnEqual = findViewById(R.id.btn_equal);
        btnDel = findViewById(R.id.btn_del);

        cbHandModel = findViewById(R.id.cb_handmodel);

        tvCalculation = findViewById(R.id.tv_userInput);
        tvResult = findViewById(R.id.tv_userResult);
    }

    private void initNumberButtons() {
        btnNumbers = new Button[10];

        btnNumbers[0] = findViewById(R.id.btn_zero);
        btnNumbers[1] = findViewById(R.id.btn_one);
        btnNumbers[2] = findViewById(R.id.btn_two);
        btnNumbers[3] = findViewById(R.id.btn_three);
        btnNumbers[4] = findViewById(R.id.btn_four);
        btnNumbers[5] = findViewById(R.id.btn_five);
        btnNumbers[6] = findViewById(R.id.btn_six);
        btnNumbers[7] = findViewById(R.id.btn_seven);
        btnNumbers[8] = findViewById(R.id.btn_eight);
        btnNumbers[9] = findViewById(R.id.btn_nine);
    }

    private void initActionPerformed() {
        initNumberButtonsActionPerformed();
        initOperationActionPerformed();
        initDeleteButtonActionPerform();
        initEqualButtonActionPerformed();
        initBracketButtonActionPerformed();
    }

    private void initNumberButtonsActionPerformed() {
        for (int i = 0; i < btnNumbers.length; i++) {
            final int number = i;

            btnNumbers[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToCalculation(number+"");
                }
            });
        }
    }

    private void initOperationActionPerformed() {
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCalculation("+");
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCalculation("-");
            }
        });
        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCalculation("x");
            }
        });
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCalculation("/");
            }
        });
    }

    private void initDeleteButtonActionPerform() {
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculation.length() > 0) {
                    int calLength = calculation.length();
                    calculation = calculation.substring(0, calLength-1);
                    tvCalculation.setText(calculation);
                }
            }
        });
        btnDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                calculation = "";
                tvCalculation.setText(calculation);
                isNewCalculation = true;
                bracketStatus = BRACKET_OPEN;
                return false;
            }
        });
    }

    private void initEqualButtonActionPerformed() {
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNewCalculation) {
                    if (validateCalculation()) {
                        tvResult.setText("1000");
                        isNewCalculation = true;
                    } else
                        Toast.makeText(MainActivity.this,
                                "Calculation is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initBracketButtonActionPerformed() {
        btnBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bracketStatus == BRACKET_OPEN) {
                    addToCalculation("(");
                    bracketStatus = BRACKET_CLOSE;
                } else {
                    addToCalculation(")");
                    bracketStatus = BRACKET_OPEN;
                }
            }
        });
    }

    private void addToCalculation(String element) {
        //Log.d("cal_len", calculation.length()+"");
        if (isNewCalculation) {
            calculation = "";
            tvResult.setText("");
            isNewCalculation = false;
        }
        if (calculation.length() == 42) {
            Toast.makeText(MainActivity.this,
                    "Calculation space is full", Toast.LENGTH_SHORT).show();
            return;
        }
        calculation += element;
        tvCalculation.setText(calculation);
    }

    private boolean addBracketToCalculation() {
        if (!isNewCalculation) {
            int calLength = calculation.length();

            for (int i = calLength-1; i >= 0; i--) {

            }
        }

        return false;
    }

    private boolean validateCalculation() {
        return true;
    }

    private boolean checkFullCalculationSpace() {
        Layout l = tvCalculation.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines-1) > 0)
                    return true;
        }

        return false;
    }

}
