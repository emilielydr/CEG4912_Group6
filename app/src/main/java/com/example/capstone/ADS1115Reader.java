package com.example.capstone;
import com.google.android.things.pio.I2cDevice;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

public class ADS1115Reader {
    private static final String I2C_NAME = "I2C1"; // Replace with your I2C bus name
    private static final int I2C_ADDRESS = 0x48;   // Default I2C address of ADS1115
    private static final int POINTER_CONVERSION = 0x00; // Pointer for conversion register
    private static final int POINTER_CONFIG = 0x01;     // Pointer for configuration register
    private static final short CONFIG_SINGLE_SHOT = (short) 0xC183; // Config for single-ended input
    private I2cDevice device;

    // Initialize the I2C device
    public void initialize() {
        try {
            PeripheralManager manager = PeripheralManager.getInstance();
            device = manager.openI2cDevice(I2C_NAME, I2C_ADDRESS);
            configureADS1115();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Configure the ADS1115 for single-ended input on channel 0
    private void configureADS1115() throws IOException {
        byte[] configBytes = {
                (byte) (CONFIG_SINGLE_SHOT >> 8), // High byte
                (byte) (CONFIG_SINGLE_SHOT & 0xFF) // Low byte
        };
        device.writeRegBuffer(POINTER_CONFIG, configBytes, configBytes.length);
    }

    // Read a single-ended value from channel 0
    public int readADCChannel0() {
        try {
            byte[] result = new byte[2]; // Two bytes for the 16-bit result
            device.writeRegByte(POINTER_CONFIG, (byte) 0xC1); // Configure for channel 0
            Thread.sleep(10); // Wait for conversion to complete
            device.readRegBuffer(POINTER_CONVERSION, result, result.length);

            // Combine bytes to form a 16-bit result
            int value = ((result[0] & 0xFF) << 8) | (result[1] & 0xFF);
            return value;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Close the I2C connection
    public void close() {
        try {
            if (device != null) {
                device.close();
                device = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
