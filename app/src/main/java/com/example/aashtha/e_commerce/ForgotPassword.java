package com.example.aashtha.e_commerce;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ForgotPassword extends AppCompatActivity {
    String messages;
    String Mail;
    EditText EMAIL;
    Button mailto;
    Boolean sentmail;
    String[] attach= new String[]{"C:\\xampp\\htdocs"};
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        EMAIL = (EditText) findViewById(R.id.getmail);
        mailto = (Button) findViewById(R.id.sendmail);
        Mail= EMAIL.toString();
        messages=" Please click on the attachment below to reset your password";

        mailto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                     sentmail=sendEmail(Mail,"CHANGE YOUR PASSWORD",messages,attach);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(),"Mail Sent",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ForgotPassword.this, AppOpen.class);
                startActivity(intent);

            }


        });
    }
    public static boolean sendEmail(String to,  String subject,
                                    String message,String[] attachements) throws Exception {
        Mail mail = new Mail("nidhisethia1997@gmail.com","aastha1234");
        if (subject != null && subject.length() > 0) {
            mail.setSubject(subject);
        } else {
            mail.setSubject("Subject");
        }

        if (message != null && message.length() > 0) {
            mail.setBody(message);
        } else {
            mail.setBody("Message");
        }

        mail.setTo(new String[] {to});

        if (attachements != null) {
            for (String attachement : attachements) {
                mail.addAttachment(attachement);
            }
        }
        return mail.send();
    }
}
