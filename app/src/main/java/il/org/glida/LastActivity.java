package il.org.glida;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LastActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

         TextView nameTv = findViewById(R.id.orderName);
         TextView addressTv = findViewById(R.id.orderAdress);
         TextView priceTv = findViewById(R.id.orderToalPrice);
         TextView PhoneNumberTv = findViewById(R.id.phoneNumber);

        addressTv.setText(getString(R.string.address_hint)+" : "+getIntent().getStringExtra("Address"));
        nameTv.setText(getString(R.string.fullName_hint)+" : "+getIntent().getStringExtra("Full name"));
        PhoneNumberTv.setText(getString(R.string.PhoneNumberHint)+" : "+getIntent().getStringExtra("phone number"));
        priceTv.setText(getString(R.string.current_price)+" : "+getIntent().getStringExtra("Total price")+" ₪ ");

        Button exitBtn= findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LastActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
