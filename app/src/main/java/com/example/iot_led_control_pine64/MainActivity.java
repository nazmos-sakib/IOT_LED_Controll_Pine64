package com.example.iot_led_control_pine64;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import com.example.iot_led_control_pine64.databinding.ActivityMainBinding;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private String[] PERMISSIONS;

    final String ledStatusUrl = "http://192.168.169.1/led_state.json";
    final String ledChangeStateUrl = "http://192.168.169.1/set_led";
    final String ledAccessPointUrl = "http://192.168.169.1/led.html";
    final String led404Url = "http://192.168.169.1/404.html";

    ActivityMainBinding binding;


    private boolean isConnectedToBL602 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // inflating our xml layout in our activity main binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //step 1: check if app is always connected to the board

        initViewsOnClickListener();





    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            checkConnectionWithBl602();

        } catch (Exception e){
            binding.tvError.setText(e.getMessage());
        }

        //check led status
        checkLedStatus();
    }

    void checkConnectionWithBl602() throws MalformedURLException, JSONException, ExecutionException, InterruptedException, DisconnectedException {
        isConnectedToBL602 =
                Led.checkLedStatus(ledStatusUrl) != null;


        binding.btnConnectionStatus.setText(
                isConnectedToBL602? "Connected" : "Not Connected"
        );
    }

    @SuppressLint("SetTextI18n")
    void initViewsOnClickListener(){
        binding.btnReload.setOnClickListener(View->{
            recreate();
        });

        binding.btnStatus.setOnClickListener(View->{
                checkLedStatus();
        });

        binding.swRed.setOnClickListener(View->{
                CommunicateWIthBl602.toggleBl602Led(
                        ledChangeStateUrl,
                        "red",
                        binding.swRed.isChecked(),
                        getApplicationContext(),
                        this::checkLedStatus //lambda and interface
                );
        });
        binding.swGreen.setOnClickListener(View->{
                CommunicateWIthBl602.toggleBl602Led(
                        ledChangeStateUrl,
                        "green",
                        binding.swGreen.isChecked(),
                        getApplicationContext(),
                        this::checkLedStatus //lambda and interface
                );
        });
        binding.swBlue.setOnClickListener(View->{
                CommunicateWIthBl602.toggleBl602Led(
                        ledChangeStateUrl,
                        "blue",
                        binding.swBlue.isChecked(),
                        getApplicationContext(),
                        this::checkLedStatus //lambda and interface
                );
        });


    }

    void checkLedStatus(){
        try {
            binding.tvError.setText("");
            Led.setLedAccordingToStatus(
                    Led.checkLedStatus(ledStatusUrl),
                    binding);
            binding.btnConnectionStatus.setText("Connected");

        } catch (MalformedURLException | JSONException | NullPointerException | ExecutionException | InterruptedException | DisconnectedException e) {
            //exception for
            e.printStackTrace();
            if (e instanceof DisconnectedException){
                binding.btnConnectionStatus.setText(e.getMessage());
            }
            binding.tvError.setText("");
            binding.tvError.setText(e.getMessage());
        }
    }



}