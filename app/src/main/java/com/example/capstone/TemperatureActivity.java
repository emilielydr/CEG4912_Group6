package com.example.capstone;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class TemperatureActivity extends AppCompatActivity {

    private TextView tempTextView, humidityTextView, pressureTextView;
    private BluetoothSocket btSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Handler handler;

    private static final String DEVICE_NAME = "BME280Sensor"; // Name of Raspberry Pi Bluetooth
    private static final UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temperature);

        // Apply insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize TextViews
        tempTextView = findViewById(R.id.tempTextView);
        humidityTextView = findViewById(R.id.humidityTextView);
        pressureTextView = findViewById(R.id.pressureTextView);

        handler = new Handler(Looper.getMainLooper());

        // Start Bluetooth connection
        new Thread(this::connectToBluetooth).start();
    }

    private void connectToBluetooth() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                showToast("Bluetooth not supported!");
                return;
            }

            if (!bluetoothAdapter.isEnabled()) {
                showToast("Please enable Bluetooth!");
                return;
            }

            // Search for paired devices
            BluetoothDevice device = null;
            for (BluetoothDevice pairedDevice : bluetoothAdapter.getBondedDevices()) {
                if (pairedDevice.getName().equals(DEVICE_NAME)) {
                    device = pairedDevice;
                    break;
                }
            }

            if (device == null) {
                showToast("Device not found!");
                return;
            }

            // Connect to the device
            btSocket = device.createRfcommSocketToServiceRecord(BT_UUID);
            btSocket.connect();
            inputStream = btSocket.getInputStream();
            outputStream = btSocket.getOutputStream();

            showToast("Connected to Raspberry Pi!");

            // Start reading data
            readBluetoothData();

        } catch (Exception e) {
            showToast("Bluetooth connection failed!");
            e.printStackTrace();
        }
    }

    private void readBluetoothData() {
        byte[] buffer = new byte[1024];
        int bytes;

        while (true) {
            try {
                bytes = inputStream.read(buffer);
                String data = new String(buffer, 0, bytes);
                processReceivedData(data);
            } catch (Exception e) {
                showToast("Connection lost!");
                break;
            }
        }
    }

    private void processReceivedData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            double temperature = jsonObject.getDouble("temperature");
            double humidity = jsonObject.getDouble("humidity");
            double pressure = jsonObject.getDouble("pressure");

            handler.post(() -> {
                tempTextView.setText("Temperature: " + temperature + " °C");
                humidityTextView.setText("Humidity: " + humidity + " %");
                pressureTextView.setText("Pressure: " + pressure + " hPa");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        handler.post(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (btSocket != null) btSocket.close();
        } catch (Exception ignored) {}
    }
}
