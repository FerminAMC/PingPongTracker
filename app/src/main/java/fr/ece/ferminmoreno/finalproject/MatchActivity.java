package fr.ece.ferminmoreno.finalproject;

import android.graphics.Color;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;

public class MatchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner style1;
    Spinner style2;
    Spinner red1;
    Spinner red2;
    Spinner black1;
    Spinner black2;

    EditText set1;
    EditText set2;
    EditText score1;
    EditText score2;

    int setValue1;
    int setValue2;
    int scoreValue1;
    int scoreValue2;

    Button setMinus1;
    Button setMinus2;
    Button scoreMinus1;
    Button scoreMinus2;
    Button setPlus1;
    Button setPlus2;
    Button scorePlus1;
    Button scorePlus2;

    private String matchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        matchKey = getIntent().getStringExtra("EXTRA_MATCH_ID");

        set1 = (EditText) findViewById(R.id.setsValue1);
        set2 = (EditText) findViewById(R.id.setsValue2);
        score1 = (EditText) findViewById(R.id.scoreValue1);
        score2 = (EditText) findViewById(R.id.scoreValue2);

        setValue1 = Integer.parseInt(set1.getText().toString());
        setValue2 = Integer.parseInt(set2.getText().toString());
        scoreValue1 = Integer.parseInt(score1.getText().toString());
        scoreValue2 = Integer.parseInt(score2.getText().toString());

        setMinus1 = (Button) findViewById(R.id.setMinus1);
        setMinus2 = (Button) findViewById(R.id.setMinus2);
        scoreMinus1 = (Button) findViewById(R.id.scoreMinus1);
        scoreMinus2 = (Button) findViewById(R.id.scoreMinus2);
        setPlus1 = (Button) findViewById(R.id.setPlus1);
        setPlus2 = (Button) findViewById(R.id.setPlus2);
        scorePlus1 = (Button) findViewById(R.id.scorePlus1);
        scorePlus2 = (Button) findViewById(R.id.scorePlus2);

        setMinus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point(0,-1);
            }
        });
        setMinus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point(1,-1);
            }
        });
        scoreMinus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point(2,-1);
            }
        });
        scoreMinus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point(3,-1);
            }
        });

        setPlus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point(0,0);
            }
        });
        setPlus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point(1,0);
            }
        });
        scorePlus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point(2,0);
            }
        });
        scorePlus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point(3,0);
            }
        });

        style1 = (Spinner) findViewById(R.id.styleValue1);
        style2 = (Spinner) findViewById(R.id.styleValue2);
        red1 = (Spinner) findViewById(R.id.redValue1);
        red2 = (Spinner) findViewById(R.id.redValue2);
        black1 = (Spinner) findViewById(R.id.blackValue1);
        black2 = (Spinner) findViewById(R.id.blackValue2);

        style1.setOnItemSelectedListener(this);
        style2.setOnItemSelectedListener(this);
        red1.setOnItemSelectedListener(this);
        red2.setOnItemSelectedListener(this);
        black1.setOnItemSelectedListener(this);
        black2.setOnItemSelectedListener(this);

        List<String> styles = new ArrayList<>();
        styles.add("Shakehand");
        styles.add("Penhold");
        styles.add("Seemiller");
        styles.add("V Grip");

        List<String> rubberBlack = new ArrayList<>();
        rubberBlack.add("Black");
        rubberBlack.add("Smooth");
        rubberBlack.add("Short pips");
        rubberBlack.add("Long pips");
        rubberBlack.add("Anti topspin");

        List<String> rubberRed = new ArrayList<>();
        rubberRed.add("Red");
        rubberRed.add("Smooth");
        rubberRed.add("Short pips");
        rubberRed.add("Long pips");
        rubberRed.add("Anti topspin");

        // Creating adapters for spinners
        ArrayAdapter<String> stylesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, styles);
        ArrayAdapter<String> rubberBlackAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, rubberBlack);
        ArrayAdapter<String> rubberRedAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, rubberRed);

        // Drop down layout style - list view with radio button
        stylesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rubberBlackAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rubberRedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinners
        style1.setAdapter(stylesAdapter);
        style2.setAdapter(stylesAdapter);
        red1.setAdapter(rubberRedAdapter);
        red2.setAdapter(rubberRedAdapter);
        black1.setAdapter(rubberBlackAdapter);
        black2.setAdapter(rubberBlackAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        String checkRed = parent.getItemAtPosition(0).toString();
        if (checkRed.equals("Red"))
            ((TextView) view).setTextColor(Color.RED);

        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void point(int i,int j){



        if(j==0){
            switch (i) {
                case 0:
                    setValue1++;
                    break;
                case 1:
                    setValue2++;
                    break;
                case 2:
                    scoreValue1++;
                    break;
                case 3:
                    scoreValue2++;
                    break;
                default:
                    break;
            }
            updateValues();
        }
        else {
            switch (i) {
                case 0:
                    if(setValue1 > 0)
                        setValue1--;
                    break;
                case 1:
                    if(setValue2 > 0)
                        setValue2--;
                    break;
                case 2:
                    if(scoreValue1 > 0)
                        scoreValue1--;
                    break;
                case 3:
                    if(scoreValue2 > 0)
                        scoreValue2--;
                    break;
                default:
                    break;
            }
            updateValues();
        }
    }

    public void updateValues() {
        set1.setText(String.valueOf(setValue1));
        set2.setText(String.valueOf(setValue2));
        score1.setText(String.valueOf(scoreValue1));
        score2.setText(String.valueOf(scoreValue2));
    }
}