package ivankovic.dorian.fer.hr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The activity is used to demonstrate the data-passing via intents.
 * @author  Dorian Ivanković
 */
public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        String result = null;

        if(getIntent()!=null && getIntent().getExtras()!=null){
            result = getIntent().getExtras().getString("result");
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }

        EditText input = findViewById(R.id.input_text);
        input.setText(result);

        Button btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(view-> {


        });
    }
}
