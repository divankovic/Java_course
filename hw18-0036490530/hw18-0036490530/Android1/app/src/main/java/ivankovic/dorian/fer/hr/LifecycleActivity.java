package ivankovic.dorian.fer.hr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main application activity.
 * Contains two input fields and performs the division operation on button click.
 * Also has buttons for transfering the  result to {@link LifecycleActivity},and
 * starting {@link ComposeMailActivity}.
 * @author Dorian Ivankovic
 */
public class LifecycleActivity extends AppCompatActivity {

    /**
     * First number editText input.
     */
    private EditText input1;


    /**
     * First number editText input.
     */
    private EditText input2;

    /**
     * Result text view.
     */
    private TextView resultView;

    /**
     * Operation button.
     */
    private Button opButton;

    /**
     * Send data button;
     */
    private Button sendButton;

    /**
     * Button used for coposing a new apstract.
     */
    private Button composeEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        input1 = findViewById(R.id.unos_1);
        input2 = findViewById(R.id.unos_2);
        resultView = findViewById(R.id.result);
        opButton = findViewById(R.id.op_button);


        opButton.setOnClickListener(view -> {
            String text1 = input1.getText().toString();
            String text2 = input2.getText().toString();

            double n1;
            double n2;

            try {
                n1 = Double.parseDouble(text1);
                n2 = Double.parseDouble(text2);
            }catch (NumberFormatException ex){
                Toast.makeText(getBaseContext(),R.string.not_decimal_error,Toast.LENGTH_SHORT).show();
                return;
            }

            if(n2==0){
                Toast.makeText(getBaseContext(), R.string.zero_division_error, Toast.LENGTH_SHORT).show();
                return;
            }

            double result = n1/n2;

            resultView.setText(String.valueOf(result));
        });

        sendButton = findViewById(R.id.btn_send);
        sendButton.setOnClickListener(view -> {
            Intent intent = new Intent(LifecycleActivity.this, ShowActivity.class);
            Bundle data = new Bundle();
            data.putString("result", resultView.getText().toString());
            intent.putExtras(data);
            startActivity(intent);
        });

        composeEmailButton = findViewById(R.id.compose_email);
        composeEmailButton.setOnClickListener(view->{
            Intent intent = new Intent(LifecycleActivity.this, ComposeMailActivity.class);
            startActivity(intent);
        });
    }
}
