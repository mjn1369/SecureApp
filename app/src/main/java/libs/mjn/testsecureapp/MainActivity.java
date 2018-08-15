package libs.mjn.testsecureapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import libs.mjn.secureapp.SecureApp;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv_result = (TextView) findViewById(R.id.tv_result);

        findViewById(R.id.btn_signature).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SecureApp.validSignatureIntegrity(MainActivity.this,getString(R.string.signature))){
                    tv_result.setText("Valid Signature Integrity");
                    tv_result.setTextColor(Color.GREEN);
                }
                else {
                    tv_result.setText("Invalid Signature Integrity");
                    tv_result.setTextColor(Color.RED);
                }
            }
        });

        findViewById(R.id.btn_classes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SecureApp.validClassesIntegrity(MainActivity.this,false,getString(R.string.crc_classes))){
                    tv_result.setText("Valid Classes Integrity");
                    tv_result.setTextColor(Color.GREEN);
                }
                else {
                    tv_result.setText("Invalid Classes Integrity");
                    tv_result.setTextColor(Color.RED);
                }
            }
        });

        findViewById(R.id.btn_manifest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SecureApp.validManifestIntegrity(MainActivity.this,getString(R.string.crc_manifest))){
                    tv_result.setText("Valid Manifest Integrity");
                    tv_result.setTextColor(Color.GREEN);
                }
                else {
                    tv_result.setText("Invalid Manifest Integrity");
                    tv_result.setTextColor(Color.RED);
                }
            }
        });

        findViewById(R.id.btn_debuggable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SecureApp.isDebuggable(MainActivity.this)){
                    tv_result.setText("App is in Release Mode");
                    tv_result.setTextColor(Color.GREEN);
                }
                else {
                    tv_result.setText("App is in Debug Mode");
                    tv_result.setTextColor(Color.RED);
                }
            }
        });

        findViewById(R.id.btn_tools).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> detectedTools = SecureApp.detectReverseEngineeringTools(MainActivity.this);
                if(detectedTools.isEmpty()){
                    tv_result.setText("No Tools Detected on Device");
                    tv_result.setTextColor(Color.GREEN);
                }
                else {
                    tv_result.setText("Tools Detected on Device: "+detectedTools.toString());
                    tv_result.setTextColor(Color.RED);
                }
            }
        });
    }
}
