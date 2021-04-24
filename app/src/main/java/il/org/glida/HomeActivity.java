package il.org.glida;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    public static int totalPrice;
    static int numOfDynSpinner;

    ArrayList<BallItem> ballsList;

    String price;
    LinearLayout flavourLayout;

    EditText flavourNumET;
    TextView priceTV;
    EditText addressET;
    EditText fullNameET;
    EditText phoneNumberET;

    RadioGroup radioBtnGroupweight;
    RadioGroup radioBtnGroupDelivery;
    RadioButton delivery_Rbtn;
    RadioButton self_Rbtn;

    Button okbtn;
    Button submit_btn;
    CheckBox bagelCB;
    CheckBox chocolateCB;
    CheckBox whippedCB;
    CheckBox candiesCB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        totalPrice=0;
        numOfDynSpinner=0;
        initBallsItems();

        // ------------LinearLayout for Dynamic spinners------------------//
        flavourLayout = findViewById(R.id.spinnerLayout);

        //---------------------RadioButtons-------------------------------//
        radioBtnGroupDelivery = findViewById(R.id.delivery_pickup_RB);
        radioBtnGroupweight = findViewById(R.id.groupradio);

        delivery_Rbtn = findViewById(R.id.deliveryRB);
        self_Rbtn = findViewById(R.id.selfpickupRB);

        //------------------------Buttons----------------------------//
        okbtn = findViewById(R.id.okBtn); //button for dynamic spinners
        submit_btn = findViewById((R.id.submit_btn)); //button for 'save' the order

        radioBtnGroupDelivery.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.deliveryRB:
                        addressET.setVisibility(View.VISIBLE);
                        fullNameET.setVisibility(View.VISIBLE);
                        phoneNumberET.setVisibility(View.VISIBLE);
                        break;
                    case R.id.selfpickupRB:
                        addressET.setVisibility(View.GONE);
                        fullNameET.setVisibility(View.VISIBLE);
                        phoneNumberET.setVisibility(View.VISIBLE);

                        break;
                }
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((numOfDynSpinner) < 1) { //no flavour chosen
                    Toast.makeText(HomeActivity.this, R.string.error_balls_num_hint, Toast.LENGTH_SHORT).show();
                }
                else if (!delivery_Rbtn.isChecked() && !self_Rbtn.isChecked()) { //no type order selected
                    Toast.makeText(HomeActivity.this, R.string.Error_delivery_hint, Toast.LENGTH_SHORT).show();
                }
                else if (delivery_Rbtn.isChecked() && addressET.getText().toString().isEmpty() || phoneNumberET.getText().toString().isEmpty() || fullNameET.getText().toString().isEmpty()) {
                        Toast.makeText(HomeActivity.this, R.string.Error_sent_order, Toast.LENGTH_SHORT).show();
                    }
                else if (self_Rbtn.isChecked() && phoneNumberET.getText().toString().isEmpty() || fullNameET.getText().toString().isEmpty()) {
                            Toast.makeText(HomeActivity.this, R.string.Error_sent_order, Toast.LENGTH_SHORT).show();
                        }
                else if(phoneNumberET.length()!= 10) {
                    Toast.makeText(HomeActivity.this,R.string.error_length_phone_hint,Toast.LENGTH_SHORT).show();
                }

                else {// if type order chosen & all the fields are fill
                        AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
                        alert.setTitle("         :)             ");
                        alert.setMessage(R.string.alertMessageHint);
                        alert.setPositiveButton(R.string.finishAlertBtnHint, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intnet1 = new Intent(HomeActivity.this, HomeActivity.class);
                                startActivity(intnet1); //'restart' the home page
                                finish();
                            }
                        });
                        alert.setNegativeButton(R.string.moveToOrderHint, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(HomeActivity.this, LastActivity.class);
                                intent.putExtra("Full name", fullNameET.getText().toString());

                                if(delivery_Rbtn.isChecked()) { //for delivery the customer need to fill address
                                    intent.putExtra("Address", addressET.getText().toString());
                                }
                                else {
                                    addressET.setText(R.string.no_address_hint);
                                    intent.putExtra("Address", addressET.getText().toString());
                                }
                                intent.putExtra("phone number", phoneNumberET.getText().toString());
                                intent.putExtra("Total price", priceTV.getText().toString());
                                startActivity(intent); //move to next page
                                finish();
                            }
                        });
                        // create and show the alert dialog
                        AlertDialog dialog = alert.create();
                        alert.show();
                    }
                }



        });


        //----------------------Edit Text---------------------------------//
        flavourNumET = findViewById(R.id.numOfflavourEt);

        fullNameET = findViewById(R.id.fullNameET);
        addressET = findViewById(R.id.addressET);
        phoneNumberET= findViewById(R.id.phoneNumberET);

        //--------------------Dynamic spinners-----------------------------//
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numOfFlavours = flavourNumET.getText().toString();
                if(radioBtnGroupweight.getCheckedRadioButtonId() == -1)
                    Toast.makeText(HomeActivity.this, R.string.Error_weight_hint, Toast.LENGTH_SHORT).show();
                else if (!numOfFlavours.isEmpty()) {
                    // Closing keyboard //
                    try {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    numOfDynSpinner = Integer.parseInt(numOfFlavours);
                    flavourLayout.removeAllViews();
                    if(numOfDynSpinner>=9999)
                    {
                        Toast.makeText(HomeActivity.this,R.string.too_much_flavour_hint,Toast.LENGTH_SHORT).show();
                    }
                    else{
                    for (int i = 0; i < numOfDynSpinner; i++) {
                        //new spinner
                        Spinner spinner = new Spinner(HomeActivity.this);
                        spinner.setBackgroundResource(R.drawable.spinner_outline_background);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(20, 10, 20, 0);
                        spinner.setLayoutParams(params);
                        spinner.setPadding(10, 10, 10, 10);
                        BallsAdapter ballsAdapter = new BallsAdapter(HomeActivity.this, ballsList);
                        spinner.setPopupBackgroundResource(R.drawable.spinner_outline);
                        ballsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(ballsAdapter);

                        flavourLayout.addView(spinner);
                    }
                }
                } else{
                    Toast.makeText(HomeActivity.this, R.string.Error_fill_flavour, Toast.LENGTH_SHORT).show(); //localization
                }

            }
        });

        //--TextView--//
        priceTV = findViewById(R.id.orderTotalPrice);


        //--Checkboxes--//
        candiesCB = findViewById(R.id.candy_cb);
        chocolateCB = findViewById(R.id.chocolate_extra);
        bagelCB = findViewById(R.id.saltyBagle_cb);
        whippedCB = findViewById(R.id.whippedCream_cb);

        //------Weight of ice cream chooser------//
        radioBtnGroupweight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            final String price = priceTV.getText().toString();
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.halfkg_rb:
                        if(totalPrice >= 75){
                            totalPrice -= 75;
                            totalPrice+= Integer.parseInt(price) + 45;
                        } else {
                            totalPrice+= Integer.parseInt(price) + 45;
                        }
                        break;

                    case R.id.onekg_rb:
                        if(totalPrice >= 45){
                            totalPrice -= 45;
                            totalPrice+= Integer.parseInt(price) + 75;
                        } else {
                            totalPrice+= Integer.parseInt(price) + 75;
                        }

                        break;
                }
                priceTV.setText(totalPrice+"");
            }
        });

        //------Delivery or self pickUp (Visible/Gone)------//



        candiesCB.setOnCheckedChangeListener(this);
        chocolateCB.setOnCheckedChangeListener(this);
        bagelCB.setOnCheckedChangeListener(this);
        whippedCB.setOnCheckedChangeListener(this);
    }

    //-------------Update price by checking which checkbox was selected--------------------//
    public void selectItem(View v) {
        price = priceTV.getText().toString();
        //is the view now checked?
        boolean checked = ((CheckBox) v).isChecked();
        //check which checkbox was clicked
        switch (v.getId()) {

            case R.id.candy_cb:
                if (checked)
                    totalPrice += 3;
                else
                    totalPrice -= 3;
                price = Integer.toString(totalPrice);
                priceTV.setText(price+"");

                break;
            case R.id.saltyBagle_cb:
                if (checked)
                    totalPrice += 4;
                else
                    totalPrice -= 4;
                price = Integer.toString(totalPrice);
                priceTV.setText(price+"");

                break;
            case R.id.whippedCream_cb:
                if (checked)
                    totalPrice += 2;
                else
                    totalPrice -= 2;
                price = Integer.toString(totalPrice);
                priceTV.setText(price+"");

                break;
            case R.id.chocolate_extra:
                if (checked)
                    totalPrice += 3;
                else
                    totalPrice = totalPrice - 3;
                price = Integer.toString(totalPrice);
                priceTV.setText(price+"");

                break;
        }
    }


    @Override
    public void onClick(View v) {

    }


    private void initBallsItems(){
        ballsList = new ArrayList<>();
        ballsList.add(new BallItem(getString(R.string.chocolate_hint),R.drawable.ic_chocolate_ball));
        ballsList.add(new BallItem(getString(R.string.vanilla),R.drawable.ic_vanile_ball));
        ballsList.add(new BallItem(getString(R.string.mango_hint),R.drawable.ic_mango_ball));
        ballsList.add(new BallItem(getString(R.string.strawberry_hint),R.drawable.ic_strawberry_ball));
        ballsList.add(new BallItem(getString(R.string.vanillaCookies_hint),R.drawable.ic_cookies_ball));
        ballsList.add(new BallItem(getString(R.string.pistachio_hint),R.drawable.ic_fistuk_ball));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}




