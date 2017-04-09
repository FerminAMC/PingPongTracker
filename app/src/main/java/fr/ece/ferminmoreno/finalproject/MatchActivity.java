package fr.ece.ferminmoreno.finalproject;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
        import java.util.List;

public class MatchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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

    EditText player1;
    EditText player2;

    private String matchKey;
    private DatabaseReference mDatabase;
    private String mUserId;
    private Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        matchKey = getIntent().getStringExtra("EXTRA_MATCH_ID");
        mUserId = getIntent().getStringExtra("EXTRA_USER_ID");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        set1 = (EditText) findViewById(R.id.setsValue1);
        set2 = (EditText) findViewById(R.id.setsValue2);
        score1 = (EditText) findViewById(R.id.scoreValue1);
        score2 = (EditText) findViewById(R.id.scoreValue2);

        player1 = (EditText) findViewById(R.id.playerName1);
        player2 = (EditText) findViewById(R.id.playerName2);

        // Attach a listener to read the data at our posts reference
        mDatabase.child("users").child(mUserId).child("matches").child(matchKey)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                match = dataSnapshot.getValue(Match.class);
                setValues(match);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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

        player1.addTextChangedListener ( new TextWatcher () {

            public void afterTextChanged ( Editable s ) {
                mDatabase.child("users").child(mUserId).child("matches").child(matchKey)
                        .child("player1").setValue(s.toString());
            }

            public void beforeTextChanged ( CharSequence s, int start, int count, int after ) {
            }

            public void onTextChanged ( CharSequence s, int start, int before, int count ) {

            }
        });

        player2.addTextChangedListener ( new TextWatcher () {

            public void afterTextChanged ( Editable s ) {
                mDatabase.child("users").child(mUserId).child("matches").child(matchKey)
                        .child("player2").setValue(s.toString());
                Log.d("TEXT_CHANGE", s.toString());
            }

            public void beforeTextChanged ( CharSequence s, int start, int count, int after ) {

            }

            public void onTextChanged ( CharSequence s, int start, int before, int count ) {

            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();


        mDatabase.child("users").child(mUserId).child("matches").child(matchKey)
                .child("style1").setValue(style1.getSelectedItemPosition());

        mDatabase.child("users").child(mUserId).child("matches").child(matchKey)
                .child("style2").setValue(style2.getSelectedItemPosition());

        mDatabase.child("users").child(mUserId).child("matches").child(matchKey)
                .child("red1").setValue(red1.getSelectedItemPosition());

        mDatabase.child("users").child(mUserId).child("matches").child(matchKey)
                .child("red2").setValue(red2.getSelectedItemPosition());

        mDatabase.child("users").child(mUserId).child("matches").child(matchKey)
                .child("black1").setValue(black1.getSelectedItemPosition());

        mDatabase.child("users").child(mUserId).child("matches").child(matchKey)
                .child("black2").setValue(black2.getSelectedItemPosition());



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
        match.setSet1(setValue1);

        set2.setText(String.valueOf(setValue2));
        match.setSet2(setValue2);

        score1.setText(String.valueOf(scoreValue1));
        match.setScore1(scoreValue1);

        score2.setText(String.valueOf(scoreValue2));
        match.setScore2(scoreValue2);

        mDatabase.child("users").child(mUserId).child("matches").child(matchKey).setValue(match);
    }

    public void setValues(Match match){
        setValue1 = match.getSet1();
        set1.setText(String.valueOf(setValue1));

        setValue2 = match.getSet2();
        set2.setText(String.valueOf(setValue2));

        scoreValue1 = match.getScore1();
        score1.setText(String.valueOf(scoreValue1));

        scoreValue2 = match.getScore2();
        score2.setText(String.valueOf(scoreValue2));

        if((!String.valueOf(player1.getText()).equals(match.getPlayer1())) ||
                (!String.valueOf(player2.getText()).equals(match.getPlayer2()))) {
            player1.setText(match.getPlayer1());
            player2.setText(match.getPlayer2());
        }

        style1.setSelection(match.getStyle1());
        style2.setSelection(match.getStyle2());
        red1.setSelection(match.getRed1());
        red2.setSelection(match.getRed2());
        black1.setSelection(match.getBlack1());
        black2.setSelection(match.getBlack2());


    }

}