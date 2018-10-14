package ivankovic.dorian.fer.hr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The activity is used for composing a new mail using all its relevant information - receiver, title and content,
 * and sending it to the specified receiver by choosing the appropriate application via intent.
 *
 * @author  Dorian Ivankovic
 */
public class ComposeMailActivity extends AppCompatActivity {

    /**
     * Button used for sending the e-mail.
     */
    private Button sendButton;

    /**
     * Email input field.
     */
    private EditText emailInput;


    /**
     * Email input field.
     */
    private EditText titleInput;


    /**
     * Email input field.
     */
    private EditText messageInput;


    /**
     * Pattern used for email checkinh.
     */
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Cupic cc email address.
     */
    private static final String CC_CUPIC = "marcupic@gmail.com";

    /**
     * Cupic cc email address.
     */
    private static final String CC_BAOTIC = "ana@baotic.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_mail);


        sendButton = findViewById(R.id.send_btn);
        emailInput = findViewById(R.id.email_input);
        titleInput = findViewById(R.id.title_input);
        messageInput = findViewById(R.id.message_input);

        sendButton.setOnClickListener(view -> sendEmail());
    }

    /**
     * The method sends the email if everything is in correct format.
     */
    private void sendEmail() {
        String email = emailInput.getText().toString();
        String title = titleInput.getText().toString();
        String message = messageInput.getText().toString();

        if(email.isEmpty() ||title.isEmpty() ||message.isEmpty()){
            Toast.makeText(this, R.string.field_not_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        if(!checkEmailPattern(email)){
            Toast.makeText(this,R.string.invalid_email,Toast.LENGTH_SHORT).show();
            return;
        }


        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{CC_CUPIC, CC_BAOTIC});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        emailIntent.setType("message/rfc822");

        try{
            startActivity(Intent.createChooser(emailIntent,""));
            finish();
        }catch(ActivityNotFoundException ex){
            Toast.makeText(this,R.string.cant_send,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * The method checks if <code>email</code> mathches the
     * email patternn
     * @param email - email to check
     * @return - true if email matches the pattern, false otherwise
     */
    private boolean checkEmailPattern(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
