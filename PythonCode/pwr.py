{\rtf1\ansi\ansicpg1252\cocoartf2761
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fnil\fcharset0 Menlo-Regular;\f1\fnil\fcharset0 Menlo-Bold;}
{\colortbl;\red255\green255\blue255;\red0\green0\blue0;\red57\green192\blue38;\red86\green32\blue244;
\red255\green255\blue255;\red56\green185\blue199;\red202\green51\blue35;\red219\green39\blue218;\red178\green178\blue178;
\red133\green0\blue2;}
{\*\expandedcolortbl;;\csgray\c0;\cssrgb\c25706\c77963\c19557;\cssrgb\c41681\c25958\c96648;
\csgray\c100000;\cssrgb\c25546\c77007\c82023;\cssrgb\c83899\c28663\c18026;\cssrgb\c89513\c29736\c88485;\cspthree\c75000\c75000\c75000;
\cspthree\c54876\c10355\c6515;}
\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardirnatural\partightenfactor0

\f0\fs22 \cf2 \CocoaLigature0 Last login: Fri Nov 22 09:01:04 on ttys000\
alexanderwilson@Jastes-Mac ~ % ssh alex@172.20.10.7\
alex@172.20.10.7's password: \
Linux raspberrypi 6.6.51+rpt-rpi-2712 #1 SMP PREEMPT Debian 1:6.6.51-1+rpt3 (2024-10-08) aarch64\
\
The programs included with the Debian GNU/Linux system are free software;\
the exact distribution terms for each program are described in the\
individual files in /usr/share/doc/*/copyright.\
\
Debian GNU/Linux comes with ABSOLUTELY NO WARRANTY, to the extent\
permitted by applicable law.\
Last login: Fri Nov 22 09:01:20 2024 from 172.20.10.5\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~ $
\f0\b0 \cf2  su\
Password: \
root@raspberrypi:/home/alex# cd Desktop\
root@raspberrypi:/home/alex/Desktop# ls\
acc.py	Adafruit_CircuitPython_BME280  ads.py  cam.py  lib.py  pwr.py  __pycache__  run_all.py	sens.py  temp.py  test.py\
root@raspberrypi:/home/alex/Desktop# sudo python3 temp.py\
Traceback (most recent call last):\
  File "/home/alex/Desktop/temp.py", line 4, in <module>\
    from adafruit_bme280 import Adafruit_BME280_I2C\
ImportError: cannot import name 'Adafruit_BME280_I2C' from 'adafruit_bme280' (/usr/local/lib/python3.11/dist-packages/adafruit_bme280/__init__.py)\
root@raspberrypi:/home/alex/Desktop# sudo nano temp.py\
root@raspberrypi:/home/alex/Desktop# sudo python3 temp.py\
Traceback (most recent call last):\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_bus_device/i2c_device.py", line 175, in __probe_for_device\
    self.i2c.writeto(self.device_address, b"")\
  File "/usr/local/lib/python3.11/dist-packages/busio.py", line 215, in writeto\
    return self._i2c.writeto(address, buffer, stop=True)\
           ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_blinka/microcontroller/generic_linux/i2c.py", line 60, in writeto\
    self._i2c_bus.write_bytes(address, buffer[start:end])\
  File "/usr/local/lib/python3.11/dist-packages/Adafruit_PureIO/smbus.py", line 303, in write_bytes\
    self._device.write(buf)\
OSError: [Errno 121] Remote I/O error\
\
During handling of the above exception, another exception occurred:\
\
Traceback (most recent call last):\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_bus_device/i2c_device.py", line 181, in __probe_for_device\
    self.i2c.readfrom_into(self.device_address, result)\
  File "/usr/local/lib/python3.11/dist-packages/busio.py", line 205, in readfrom_into\
    return self._i2c.readfrom_into(address, buffer, stop=True)\
           ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_blinka/microcontroller/generic_linux/i2c.py", line 67, in readfrom_into\
    readin = self._i2c_bus.read_bytes(address, end - start)\
             ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\
  File "/usr/local/lib/python3.11/dist-packages/Adafruit_PureIO/smbus.py", line 170, in read_bytes\
    return self._device.read(number)\
           ^^^^^^^^^^^^^^^^^^^^^^^^^\
OSError: [Errno 121] Remote I/O error\
\
During handling of the above exception, another exception occurred:\
\
Traceback (most recent call last):\
  File "/home/alex/Desktop/temp.py", line 10, in <module>\
    bme280 = adafruit_bme280.Adafruit_BME280_I2C(i2c)\
             ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_bme280/basic.py", line 368, in __init__\
    super().__init__(I2C_Impl(i2c, address))\
                     ^^^^^^^^^^^^^^^^^^^^^^\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_bme280/protocol.py", line 18, in __init__\
    self._i2c = i2c_device.I2CDevice(i2c, address)\
                ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_bus_device/i2c_device.py", line 62, in __init__\
    self.__probe_for_device()\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_bus_device/i2c_device.py", line 184, in __probe_for_device\
    raise ValueError("No I2C device at address: 0x%x" % self.device_address)\
ValueError: No I2C device at address: 0x77\
root@raspberrypi:/home/alex/Desktop# i2cdetect -y 1\
     0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f\
00:                         -- -- -- -- -- -- -- -- \
10: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- \
20: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- \
30: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- \
40: -- -- -- -- -- -- -- -- 48 -- -- -- -- -- -- -- \
50: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- \
60: -- -- -- -- -- -- -- -- 68 -- -- -- -- -- -- -- \
70: -- -- -- -- -- -- 76 --                         \
root@raspberrypi:/home/alex/Desktop# sudo nano temp.py\
root@raspberrypi:/home/alex/Desktop# sudo python3 temp.py\
Temperature: 23.53 \'b0C\
Humidity: 35.67 %\
Pressure: 991.76 hPa\
Temperature: 23.53 \'b0C\
Humidity: 35.68 %\
Pressure: 991.74 hPa\
Temperature: 23.53 \'b0C\
Humidity: 35.69 %\
Pressure: 991.70 hPa\
Temperature: 23.54 \'b0C\
Humidity: 35.65 %\
Pressure: 991.69 hPa\
Temperature: 23.55 \'b0C\
Humidity: 35.71 %\
Pressure: 991.69 hPa\
Temperature: 23.54 \'b0C\
Humidity: 35.65 %\
Pressure: 991.74 hPa\
Temperature: 23.53 \'b0C\
Humidity: 35.64 %\
Pressure: 991.73 hPa\
Temperature: 23.55 \'b0C\
Humidity: 35.80 %\
Pressure: 991.74 hPa\
Temperature: 23.55 \'b0C\
Humidity: 35.70 %\
Pressure: 991.78 hPa\
^CTraceback (most recent call last):\
  File "/home/alex/Desktop/temp.py", line 20, in <module>\
    time.sleep(2)\
KeyboardInterrupt\
\
root@raspberrypi:/home/alex/Desktop# nano run_all.py\
root@raspberrypi:/home/alex/Desktop# sudo python3 run_all.py\
Running acc.py...\
Running ads.py...\
Running sens.py...\
Accelerometer X: 38135\
Accelerometer Y: 1024\
Accelerometer Z: 51263\
MQ-135 Voltage: 0.64525V\
Distance: 1202.44 cm\
Accelerometer X: 60663\
Accelerometer Y: 63743\
Accelerometer Z: 17471\
MQ-135 Voltage: 0.5505V\
Distance: 1202.31 cm\
Accelerometer X: 54519\
Accelerometer Y: 14336\
Accelerometer Z: 36927\
MQ-135 Voltage: 0.664875V\
Distance: 1202.41 cm\
Accelerometer X: 55543\
Accelerometer Y: 9216\
Accelerometer Z: 42047\
MQ-135 Voltage: 0.549625V\
Distance: 1202.46 cm\
Accelerometer X: 55543\
Accelerometer Y: 10240\
Accelerometer Z: 59455\
MQ-135 Voltage: 0.65425V\
Distance: 1202.40 cm\
Accelerometer X: 44279\
Accelerometer Y: 22528\
Accelerometer Z: 43071\
MQ-135 Voltage: 0.567625V\
Distance: 1202.41 cm\
Accelerometer X: 36087\
Accelerometer Y: 13312\
Accelerometer Z: 48191\
MQ-135 Voltage: 0.607375V\
Distance: 1202.40 cm\
Accelerometer X: 51447\
Accelerometer Y: 6144\
Accelerometer Z: 32831\
MQ-135 Voltage: 0.651875V\
Distance: 8.43 cm\
Accelerometer X: 43255\
Accelerometer Y: 15360\
Accelerometer Z: 33855\
MQ-135 Voltage: 0.541375V\
Distance: 77.09 cm\
Accelerometer X: 42231\
Accelerometer Y: 60671\
Accelerometer Z: 44095\
MQ-135 Voltage: 0.671875V\
Distance: 5.33 cm\
Accelerometer X: 41207\
Accelerometer Y: 3072\
Accelerometer Z: 44095\
MQ-135 Voltage: 0.541875V\
Distance: 4.04 cm\
Accelerometer X: 58615\
Accelerometer Y: 14336\
Accelerometer Z: 20543\
MQ-135 Voltage: 0.6615V\
Distance: 3.59 cm\
Accelerometer X: 46327\
Accelerometer Y: 3072\
Accelerometer Z: 13375\
MQ-135 Voltage: 0.56475V\
Distance: 3.61 cm\
Accelerometer X: 53495\
Accelerometer Y: 2048\
Accelerometer Z: 23615\
MQ-135 Voltage: 0.5935V\
Distance: 1202.31 cm\
Accelerometer X: 50423\
Accelerometer Y: 59647\
Accelerometer Z: 31807\
MQ-135 Voltage: 0.65175V\
Distance: 1202.42 cm\
Accelerometer X: 53495\
Accelerometer Y: 62719\
Accelerometer Z: 27711\
MQ-135 Voltage: 0.550625V\
Distance: 1202.25 cm\
^CProgram interrupted by user.\
Traceback (most recent call last):\
Traceback (most recent call last):\
  File "/home/alex/Desktop/ads.py", line 17, in <module>\
  File "/home/alex/Desktop/acc.py", line 24, in <module>\
Program stopped.\
    time.sleep(1)\
    time.sleep(1)\
KeyboardInterrupt\
KeyboardInterrupt\
Running acc.py...\
Running ads.py...\
Accelerometer X: 57591\
Accelerometer Y: 4096\
Accelerometer Z: 57407\
MQ-135 Voltage: 0.559V\
Running sens.py...\
Distance: 1202.45 cm\
Accelerometer X: 40183\
Accelerometer Y: 9216\
Accelerometer Z: 63\
MQ-135 Voltage: 0.61725V\
Distance: 1202.36 cm\
^CProgram interrupted by user.\
Traceback (most recent call last):\
Traceback (most recent call last):\
  File "/home/alex/Desktop/acc.py", line 24, in <module>\
  File "/home/alex/Desktop/ads.py", line 17, in <module>\
Traceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 29, in <module>\
    time.sleep(1)\
    time.sleep(1)\
    time.sleep(1)  # Main thread does nothing, just waits\
KeyboardInterrupt\
KeyboardInterrupt\
    ^^^^^^^^^^^^^\
KeyboardInterrupt\
\
During handling of the above exception, another exception occurred:\
\
Traceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 34, in <module>\
    thread.join()\
  File "/usr/lib/python3.11/threading.py", line 1112, in join\
    self._wait_for_tstate_lock()\
  File "/usr/lib/python3.11/threading.py", line 1132, in _wait_for_tstate_lock\
    if lock.acquire(block, timeout):\
       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^\
KeyboardInterrupt\
Running acc.py...\
Running ads.py...\
Accelerometer X: 37111\
Accelerometer Y: 20480\
Accelerometer Z: 38975\
MQ-135 Voltage: 0.5535V\
Running sens.py...\
Distance: 107.62 cm\
Accelerometer X: 55543\
Accelerometer Y: 2048\
Accelerometer Z: 55359\
MQ-135 Voltage: 0.65075V\
Distance: 1202.40 cm\
^CProgram interrupted by user.\
Traceback (most recent call last):\
Traceback (most recent call last):\
  File "/home/alex/Desktop/ads.py", line 17, in <module>\
  File "/home/alex/Desktop/acc.py", line 24, in <module>\
Exception ignored in: <module 'threading' from '/usr/lib/python3.11/threading.py'>\
Traceback (most recent call last):\
  File "/usr/lib/python3.11/threading.py", line 1583, in _shutdown\
    time.sleep(1)\
    time.sleep(1)\
KeyboardInterrupt\
KeyboardInterrupt\
    lock.acquire()\
KeyboardInterrupt: \
\
root@raspberrypi:/home/alex/Desktop# sudo python3 run_all.py\
Running acc.py...\
Running ads.py...\
Running sens.py...\
Accelerometer X: 52471\
Accelerometer Y: 56575\
Accelerometer Z: 22591\
MQ-135 Voltage: 0.550125V\
Distance: 1202.39 cm\
Accelerometer X: 51447\
Accelerometer Y: 64767\
Accelerometer Z: 44095\
MQ-135 Voltage: 0.65175V\
Distance: 1202.31 cm\
Accelerometer X: 50423\
Accelerometer Y: 58623\
Accelerometer Z: 37951\
MQ-135 Voltage: 0.582875V\
^CProgram interrupted by user.\
Traceback (most recent call last):\
  File "/home/alex/Desktop/ads.py", line 17, in <module>\
Program stopped.\
Traceback (most recent call last):\
  File "/home/alex/Desktop/acc.py", line 24, in <module>\
    time.sleep(1)\
    time.sleep(1)\
KeyboardInterrupt\
KeyboardInterrupt\
^CTraceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 29, in <module>\
    time.sleep(1)  # Main thread does nothing, just waits\
    ^^^^^^^^^^^^^\
KeyboardInterrupt\
\
During handling of the above exception, another exception occurred:\
\
Traceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 34, in <module>\
    thread.join()\
  File "/usr/lib/python3.11/threading.py", line 1112, in join\
    self._wait_for_tstate_lock()\
  File "/usr/lib/python3.11/threading.py", line 1132, in _wait_for_tstate_lock\
    if lock.acquire(block, timeout):\
       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^\
KeyboardInterrupt\
Running acc.py...\
Running ads.py...\
Accelerometer X: 44279\
Accelerometer Y: 11264\
Accelerometer Z: 59454\
MQ-135 Voltage: 0.663V\
Running sens.py...\
Distance: 138.95 cm\
^CProgram interrupted by user.\
Traceback (most recent call last):\
Traceback (most recent call last):\
  File "/home/alex/Desktop/acc.py", line 24, in <module>\
  File "/home/alex/Desktop/ads.py", line 17, in <module>\
Exception ignored in: <module 'threading' from '/usr/lib/python3.11/threading.py'>\
Traceback (most recent call last):\
  File "/usr/lib/python3.11/threading.py", line 1583, in _shutdown\
    time.sleep(1)\
    time.sleep(1)\
KeyboardInterrupt\
KeyboardInterrupt\
    lock.acquire()\
KeyboardInterrupt: \
\
root@raspberrypi:/home/alex/Desktop# nano run_all.py\
root@raspberrypi:/home/alex/Desktop# sudo python3 run_all.py\
Running acc.py...\
Running ads.py...\
Running sens.py, temp.py...\
python3: can't open file '/home/alex/Desktop/sens.py, temp.py': [Errno 2] No such file or directory\
Accelerometer X: 38135\
Accelerometer Y: 8192\
Accelerometer Z: 36927\
MQ-135 Voltage: 0.6587500000000001V\
Accelerometer X: 51447\
Accelerometer Y: 4096\
Accelerometer Z: 32831\
Running sens.py, temp.py...\
python3: can't open file '/home/alex/Desktop/sens.py, temp.py': [Errno 2] No such file or directory\
MQ-135 Voltage: 0.560125V\
Accelerometer X: 58615\
Accelerometer Y: 63743\
Accelerometer Z: 18495\
Running sens.py, temp.py...\
python3: can't open file '/home/alex/Desktop/sens.py, temp.py': [Errno 2] No such file or directory\
MQ-135 Voltage: 0.603875V\
Accelerometer X: 53495\
Accelerometer Y: 5120\
Accelerometer Z: 31807\
Running sens.py, temp.py...\
python3: can't open file '/home/alex/Desktop/sens.py, temp.py': [Errno 2] No such file or directory\
MQ-135 Voltage: 0.6531250000000001V\
Accelerometer X: 44279\
Accelerometer Y: 7168\
Accelerometer Z: 13375\
Running sens.py, temp.py...\
python3: can't open file '/home/alex/Desktop/sens.py, temp.py': [Errno 2] No such file or directory\
MQ-135 Voltage: 0.5475V\
Accelerometer X: 58615\
Accelerometer Y: 7168\
Accelerometer Z: 51263\
Running sens.py, temp.py...\
MQ-135 Voltage: 0.663375V\
python3: can't open file '/home/alex/Desktop/sens.py, temp.py': [Errno 2] No such file or directory\
Accelerometer X: 5368\
Accelerometer Y: 19456\
Accelerometer Z: 62527\
MQ-135 Voltage: 0.553875V\
Running sens.py, temp.py...\
python3: can't open file '/home/alex/Desktop/sens.py, temp.py': [Errno 2] No such file or directory\
Accelerometer X: 39159\
Accelerometer Y: 51455\
Accelerometer Z: 51263\
MQ-135 Voltage: 0.6415V\
Running sens.py, temp.py...\
python3: can't open file '/home/alex/Desktop/sens.py, temp.py': [Errno 2] No such file or directory\
Accelerometer X: 51447\
Accelerometer Y: 7168\
Accelerometer Z: 38975\
MQ-135 Voltage: 0.624375V\
Running sens.py, temp.py...\
python3: can't open file '/home/alex/Desktop/sens.py, temp.py': [Errno 2] No such file or directory\
^CProgram stopped.\
Traceback (most recent call last):\
  File "/home/alex/Desktop/acc.py", line 24, in <module>\
Traceback (most recent call last):\
  File "/home/alex/Desktop/ads.py", line 17, in <module>\
    time.sleep(1)\
KeyboardInterrupt\
    time.sleep(1)\
KeyboardInterrupt\
Running sens.py, temp.py...\
python3: can't open file '/home/alex/Desktop/sens.py, temp.py': [Errno 2] No such file or directory\
^CTraceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 29, in <module>\
    time.sleep(1)  # Main thread does nothing, just waits\
    ^^^^^^^^^^^^^\
KeyboardInterrupt\
\
During handling of the above exception, another exception occurred:\
\
Traceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 34, in <module>\
    thread.join()\
  File "/usr/lib/python3.11/threading.py", line 1112, in join\
    self._wait_for_tstate_lock()\
  File "/usr/lib/python3.11/threading.py", line 1132, in _wait_for_tstate_lock\
    if lock.acquire(block, timeout):\
       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^\
KeyboardInterrupt\
^CException ignored in: <module 'threading' from '/usr/lib/python3.11/threading.py'>\
Traceback (most recent call last):\
  File "/usr/lib/python3.11/threading.py", line 1583, in _shutdown\
    lock.acquire()\
KeyboardInterrupt: \
\
root@raspberrypi:/home/alex/Desktop# nano run_all.py\
root@raspberrypi:/home/alex/Desktop# sudo python3 run_all.py\
Running acc.py...\
Running ads.py...\
Running sens.py...\
Running temp.py...\
Accelerometer X: 52471\
Accelerometer Y: 6144\
Accelerometer Z: 52287\
MQ-135 Voltage: 0.544V\
Temperature: 23.64 \'b0C\
Distance: 1202.11 cm\
Humidity: 35.47 %\
Pressure: 991.79 hPa\
Accelerometer X: 52471\
Accelerometer Y: 10240\
Accelerometer Z: 58431\
MQ-135 Voltage: 0.655375V\
Distance: 130.12 cm\
Accelerometer X: 48375\
Accelerometer Y: 15360\
Accelerometer Z: 22591\
MQ-135 Voltage: 0.577625V\
Distance: 131.12 cm\
Temperature: 23.63 \'b0C\
Humidity: 35.45 %\
Pressure: 991.82 hPa\
Accelerometer X: 57591\
Accelerometer Y: 63743\
Accelerometer Z: 20543\
MQ-135 Voltage: 0.55925V\
Distance: 141.21 cm\
Accelerometer X: 52471\
Accelerometer Y: 10240\
Accelerometer Z: 57407\
MQ-135 Voltage: 0.667375V\
Distance: 130.91 cm\
Temperature: 23.63 \'b0C\
Humidity: 35.42 %\
Pressure: 991.82 hPa\
Accelerometer X: 52471\
Accelerometer Y: 60671\
Accelerometer Z: 42047\
MQ-135 Voltage: 0.54875V\
Distance: 131.01 cm\
Accelerometer X: 47351\
Accelerometer Y: 64767\
Accelerometer Z: 4159\
MQ-135 Voltage: 0.654625V\
Distance: 132.77 cm\
Temperature: 23.64 \'b0C\
Humidity: 35.43 %\
Pressure: 991.75 hPa\
Accelerometer X: 54519\
Accelerometer Y: 17408\
Accelerometer Z: 25663\
MQ-135 Voltage: 0.5806250000000001V\
Distance: 132.97 cm\
Accelerometer X: 57591\
Accelerometer Y: 13312\
Accelerometer Z: 15423\
MQ-135 Voltage: 0.564125V\
Distance: 133.73 cm\
Temperature: 23.64 \'b0C\
Humidity: 35.45 %\
Pressure: 991.71 hPa\
Accelerometer X: 46327\
Accelerometer Y: 55551\
Accelerometer Z: 2112\
MQ-135 Voltage: 0.6687500000000001V\
Distance: 131.31 cm\
Accelerometer X: 41207\
Accelerometer Y: 59647\
Accelerometer Z: 63550\
MQ-135 Voltage: 0.545125V\
Distance: 1202.04 cm\
Temperature: 23.64 \'b0C\
Humidity: 35.34 %\
Pressure: 991.72 hPa\
Accelerometer X: 49399\
Accelerometer Y: 5120\
Accelerometer Z: 45119\
MQ-135 Voltage: 0.6587500000000001V\
Distance: 1202.09 cm\
Accelerometer X: 37111\
Accelerometer Y: 5120\
Accelerometer Z: 31807\
MQ-135 Voltage: 0.558875V\
Distance: 130.93 cm\
Temperature: 23.64 \'b0C\
Humidity: 35.43 %\
Pressure: 991.76 hPa\
Accelerometer X: 56567\
Accelerometer Y: 58623\
Accelerometer Z: 31807\
MQ-135 Voltage: 0.601V\
Distance: 130.48 cm\
Accelerometer X: 56567\
Accelerometer Y: 64767\
Accelerometer Z: 9279\
MQ-135 Voltage: 0.66325V\
Distance: 131.58 cm\
Temperature: 23.64 \'b0C\
Humidity: 35.44 %\
Pressure: 991.75 hPa\
Accelerometer X: 53495\
Accelerometer Y: 0\
Accelerometer Z: 22591\
MQ-135 Voltage: 0.544375V\
Distance: 132.83 cm\
Accelerometer X: 47351\
Accelerometer Y: 10240\
Accelerometer Z: 43071\
MQ-135 Voltage: 0.659625V\
Distance: 61.71 cm\
Accelerometer X: 35063\
Accelerometer Y: 0\
Accelerometer Z: 15423\
^CProgram interrupted by user.\
Traceback (most recent call last):\
  File "/home/alex/Desktop/acc.py", line 24, in <module>\
Traceback (most recent call last):\
Traceback (most recent call last):\
  File "/home/alex/Desktop/temp.py", line 17, in <module>\
  File "/home/alex/Desktop/ads.py", line 17, in <module>\
    time.sleep(1)\
    time.sleep(1)\
    print(f"Temperature: \{bme280.temperature:.2f\} \'b0C")\
KeyboardInterrupt\
KeyboardInterrupt\
                          ^^^^^^^^^^^^^^^^^^\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_bme280/basic.py", line 205, in temperature\
    self._read_temperature()\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_bme280/basic.py", line 120, in _read_temperature\
    sleep(0.002)\
KeyboardInterrupt\
Program stopped.\
^CTraceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 29, in <module>\
    time.sleep(1)  # Main thread does nothing, just waits\
    ^^^^^^^^^^^^^\
KeyboardInterrupt\
\
During handling of the above exception, another exception occurred:\
\
Traceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 34, in <module>\
    thread.join()\
  File "/usr/lib/python3.11/threading.py", line 1112, in join\
    self._wait_for_tstate_lock()\
  File "/usr/lib/python3.11/threading.py", line 1132, in _wait_for_tstate_lock\
    if lock.acquire(block, timeout):\
       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^\
KeyboardInterrupt\
Running acc.py...\
Running temp.py...\
Running ads.py...\
Accelerometer X: 41207\
Accelerometer Y: 52479\
Accelerometer Z: 52287\
MQ-135 Voltage: 0.549875V\
Running sens.py...\
Temperature: 23.66 \'b0C\
^CTraceback (most recent call last):\
Traceback (most recent call last):\
  File "/home/alex/Desktop/acc.py", line 24, in <module>\
  File "/home/alex/Desktop/ads.py", line 17, in <module>\
Exception ignored in: <module 'threading' from '/usr/lib/python3.11/threading.py'>\
Traceback (most recent call last):\
  File "/usr/lib/python3.11/threading.py", line 1583, in _shutdown\
Traceback (most recent call last):\
  File "/home/alex/Desktop/temp.py", line 18, in <module>\
    time.sleep(1)\
    time.sleep(1)\
KeyboardInterrupt\
KeyboardInterrupt\
    print(f"Humidity: \{bme280.humidity:.2f\} %")\
                       ^^^^^^^^^^^^^^^\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_bme280/basic.py", line 252, in humidity\
    lock.acquire()\
KeyboardInterrupt: \
    self._read_temperature()\
  File "/usr/local/lib/python3.11/dist-packages/adafruit_bme280/basic.py", line 120, in _read_temperature\
    sleep(0.002)\
KeyboardInterrupt\
Program interrupted by user.\
\
root@raspberrypi:/home/alex/Desktop# ls\
acc.py	Adafruit_CircuitPython_BME280  ads.py  cam.py  lib.py  pwr.py  __pycache__  run_all.py	sens.py  temp.py  test.py\
root@raspberrypi:/home/alex/Desktop# rm test.py\
root@raspberrypi:/home/alex/Desktop# ls\
acc.py	Adafruit_CircuitPython_BME280  ads.py  cam.py  lib.py  pwr.py  __pycache__  run_all.py	sens.py  temp.py\
root@raspberrypi:/home/alex/Desktop# rm -r __pycache__\
root@raspberrypi:/home/alex/Desktop# rm -r Adafruit_CircuitPython_BME280\
root@raspberrypi:/home/alex/Desktop# rm -r lib.py\
root@raspberrypi:/home/alex/Desktop# mkdir python\
root@raspberrypi:/home/alex/Desktop# reboot\
root@raspberrypi:/home/alex/Desktop# Connection to 172.20.10.7 closed by remote host.\
Connection to 172.20.10.7 closed.\
alexanderwilson@Jastes-Mac ~ % ssh alex@172.20.10.7\
alex@172.20.10.7's password: \
Linux raspberrypi 6.6.51+rpt-rpi-2712 #1 SMP PREEMPT Debian 1:6.6.51-1+rpt3 (2024-10-08) aarch64\
\
The programs included with the Debian GNU/Linux system are free software;\
the exact distribution terms for each program are described in the\
individual files in /usr/share/doc/*/copyright.\
\
Debian GNU/Linux comes with ABSOLUTELY NO WARRANTY, to the extent\
permitted by applicable law.\
Last login: Fri Nov 22 09:36:05 2024\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~ $
\f0\b0 \cf2  cd Desktop\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  ls\

\f1\b \cf3 acc.py
\f0\b0 \cf2   ads.py  
\f1\b \cf3 cam.py
\f0\b0 \cf2   pwr.py  
\f1\b \cf4 python
\f0\b0 \cf2   run_all.py  sens.py  temp.py\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  python3 run_all.py\
Running acc.py...\
Running ads.py...\
Running sens.py...\
Running temp.py...\
Accelerometer X: 40183\
Accelerometer Y: 2048\
Accelerometer Z: 31807\
MQ-135 Voltage: 0.557V\
Distance: 1202.38 cm\
Temperature: 23.65 \'b0C\
Humidity: 35.38 %\
Pressure: 991.83 hPa\
Accelerometer X: 56567\
Accelerometer Y: 6144\
Accelerometer Z: 25663\
MQ-135 Voltage: 0.6555V\
Distance: 1202.47 cm\
Accelerometer X: 51447\
Accelerometer Y: 1024\
Accelerometer Z: 41023\
MQ-135 Voltage: 0.556625V\
Distance: 1202.52 cm\
Temperature: 23.67 \'b0C\
Humidity: 35.40 %\
Pressure: 991.81 hPa\
Accelerometer X: 34039\
Accelerometer Y: 61695\
Accelerometer Z: 42047\
MQ-135 Voltage: 0.6485V\
Distance: 1202.33 cm\
Accelerometer X: 53495\
Accelerometer Y: 34816\
Accelerometer Z: 29759\
MQ-135 Voltage: 0.567125V\
Temperature: 23.68 \'b0C\
Distance: 1202.45 cm\
Humidity: 35.40 %\
Pressure: 991.82 hPa\
Accelerometer X: 55543\
Accelerometer Y: 8192\
Accelerometer Z: 41023\
MQ-135 Voltage: 0.613V\
Distance: 1202.41 cm\
Accelerometer X: 49399\
Accelerometer Y: 51455\
Accelerometer Z: 25663\
MQ-135 Voltage: 0.640125V\
Temperature: 23.68 \'b0C\
Humidity: 35.49 %\
Distance: 1202.34 cm\
Pressure: 991.82 hPa\
Accelerometer X: 60663\
Accelerometer Y: 6144\
Accelerometer Z: 22591\
MQ-135 Voltage: 0.5535V\
Distance: 1202.31 cm\
Accelerometer X: 52471\
Accelerometer Y: 15360\
Accelerometer Z: 43071\
MQ-135 Voltage: 0.6625V\
Temperature: 23.68 \'b0C\
Humidity: 35.54 %\
Distance: 1202.27 cm\
Pressure: 991.83 hPa\
Accelerometer X: 49399\
Accelerometer Y: 8192\
Accelerometer Z: 47167\
MQ-135 Voltage: 0.55V\
Distance: 1202.25 cm\
Accelerometer X: 46327\
Accelerometer Y: 57599\
Accelerometer Z: 8255\
MQ-135 Voltage: 0.646V\
Temperature: 23.68 \'b0C\
Humidity: 35.44 %\
Pressure: 991.78 hPa\
Distance: 1202.29 cm\
Accelerometer X: 57591\
Accelerometer Y: 45311\
Accelerometer Z: 38975\
MQ-135 Voltage: 0.572125V\
Distance: 1202.36 cm\
^CProgram interrupted by user.\
Traceback (most recent call last):\
  File "/home/alex/Desktop/ads.py", line 17, in <module>\
Traceback (most recent call last):\
Program stopped.\
  File "/home/alex/Desktop/acc.py", line 24, in <module>\
Traceback (most recent call last):\
  File "/home/alex/Desktop/temp.py", line 20, in <module>\
    time.sleep(2)\
    time.sleep(1)\
    time.sleep(1)\
KeyboardInterrupt\
KeyboardInterrupt\
KeyboardInterrupt\
^CTraceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 29, in <module>\
    time.sleep(1)  # Main thread does nothing, just waits\
    ^^^^^^^^^^^^^\
KeyboardInterrupt\
\
During handling of the above exception, another exception occurred:\
\
Traceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 34, in <module>\
    thread.join()\
  File "/usr/lib/python3.11/threading.py", line 1112, in join\
    self._wait_for_tstate_lock()\
  File "/usr/lib/python3.11/threading.py", line 1132, in _wait_for_tstate_lock\
    if lock.acquire(block, timeout):\
       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^\
KeyboardInterrupt\
^CException ignored in: <module 'threading' from '/usr/lib/python3.11/threading.py'>\
Traceback (most recent call last):\
  File "/usr/lib/python3.11/threading.py", line 1583, in _shutdown\
    lock.acquire()\
KeyboardInterrupt: \
\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  nano acc.py\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  python3 run_all.py\
Running acc.py...\
Running ads.py...\
Running sens.py...\
Running temp.py...\
Accelerometer X: 59639\
Accelerometer Y: 5120\
Accelerometer Z: 42047\
Distance: 78.27 cm\
MQ-135 Voltage: 0.571875V\
Temperature: 23.86 \'b0C\
Humidity: 36.26 %\
Pressure: 991.84 hPa\
Accelerometer X: 58615\
Accelerometer Y: 57599\
Accelerometer Z: 16447\
Distance: 78.28 cm\
MQ-135 Voltage: 0.676V\
Accelerometer X: 56567\
Accelerometer Y: 14336\
Accelerometer Z: 43071\
Distance: 76.98 cm\
MQ-135 Voltage: 0.558625V\
Temperature: 23.86 \'b0C\
Humidity: 36.37 %\
Pressure: 991.79 hPa\
Accelerometer X: 44279\
Accelerometer Y: 4096\
Accelerometer Z: 36927\
Distance: 76.95 cm\
MQ-135 Voltage: 0.619V\
Accelerometer X: 31991\
Accelerometer Y: 12288\
Accelerometer Z: 49215\
Distance: 76.96 cm\
MQ-135 Voltage: 0.65V\
Temperature: 23.87 \'b0C\
Humidity: 36.55 %\
Pressure: 991.82 hPa\
Accelerometer X: 48375\
Accelerometer Y: 0\
Accelerometer Z: 1088\
Distance: 76.96 cm\
MQ-135 Voltage: 0.5513750000000001V\
Accelerometer X: 41207\
Accelerometer Y: 10240\
Accelerometer Z: 27711\
Distance: 76.96 cm\
MQ-135 Voltage: 0.6535V\
Temperature: 23.87 \'b0C\
Humidity: 36.56 %\
Pressure: 991.82 hPa\
Accelerometer X: 50423\
Accelerometer Y: 33792\
Accelerometer Z: 48191\
Distance: 76.94 cm\
MQ-135 Voltage: 0.564625V\
Accelerometer X: 48375\
Accelerometer Y: 17408\
Accelerometer Z: 43071\
Distance: 76.96 cm\
MQ-135 Voltage: 0.59375V\
Temperature: 23.87 \'b0C\
Humidity: 36.35 %\
Pressure: 991.81 hPa\
Accelerometer X: 26871\
Accelerometer Y: 62719\
Accelerometer Z: 27711\
Distance: 76.97 cm\
MQ-135 Voltage: 0.6745V\
^CProgram interrupted by user.\
Traceback (most recent call last):\
Traceback (most recent call last):\
  File "/home/alex/Desktop/ads.py", line 17, in <module>\
  File "/home/alex/Desktop/temp.py", line 20, in <module>\
Program stopped.\
Traceback (most recent call last):\
  File "/home/alex/Desktop/acc.py", line 24, in <module>\
    time.sleep(1)\
    time.sleep(2)\
KeyboardInterrupt\
KeyboardInterrupt\
    time.sleep(1)\
KeyboardInterrupt\
^CTraceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 29, in <module>\
    time.sleep(1)  # Main thread does nothing, just waits\
    ^^^^^^^^^^^^^\
KeyboardInterrupt\
\
During handling of the above exception, another exception occurred:\
\
Traceback (most recent call last):\
  File "/home/alex/Desktop/run_all.py", line 34, in <module>\
    thread.join()\
  File "/usr/lib/python3.11/threading.py", line 1112, in join\
    self._wait_for_tstate_lock()\
  File "/usr/lib/python3.11/threading.py", line 1132, in _wait_for_tstate_lock\
    if lock.acquire(block, timeout):\
       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^\
KeyboardInterrupt\
^CException ignored in: <module 'threading' from '/usr/lib/python3.11/threading.py'>\
Traceback (most recent call last):\
  File "/usr/lib/python3.11/threading.py", line 1583, in _shutdown\
    lock.acquire()\
KeyboardInterrupt: \
\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  nano acc.py\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  nano ads.py\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  nano cam.py\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  nano temp.py\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  y\
-bash: y: command not found\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  nano sens.py\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  nano run_all.py\

\f1\b \cf3 alex@raspberrypi
\f0\b0 \cf2 :
\f1\b \cf4 ~/Desktop $
\f0\b0 \cf2  nano pwr.py\
\
\cf5 \cb2   GNU nano 7.2                                                                                         pwr.py                                                                                                   \cf2 \cb1 \

\f1\b \cf6 import
\f0\b0 \cf2  RPi.GPIO 
\f1\b \cf6 as
\f0\b0 \cf2  GPIO\

\f1\b \cf6 from
\f0\b0 \cf2  time 
\f1\b \cf6 import
\f0\b0 \cf2  sleep\
\

\f1\b \cf7 # Set up the GPIO pin for PWM
\f0\b0 \cf2 \
CONTROL_PIN = 18 
\f1\b \cf7  # GPIO pin connected to control signal
\f0\b0 \cf2 \
GPIO.setmode(GPIO.BCM)\
GPIO.setup(CONTROL_PIN, GPIO.OUT)\
\

\f1\b \cf7 # Initialize PWM at 50Hz (typical for servo control)
\f0\b0 \cf2 \
pwm = GPIO.PWM(CONTROL_PIN, 50)\
pwm.start(0) 
\f1\b \cf7  # Start PWM with 0% duty cycle (no throttle)
\f0\b0 \cf2 \
\

\f1\b \cf6 try
\f0\b0 \cf2 :\
    
\f1\b \cf6 while
\f0\b0 \cf2  
\f1\b \cf8 True
\f0\b0 \cf2 :\
       
\f1\b \cf7  # Set throttle to 0% to 100% (duty cycle varies depending on ESC or servo requirements)
\f0\b0 \cf2 \
        
\f1\b \cf6 for
\f0\b0 \cf2  duty_cycle 
\f1\b \cf6 in
\f0\b0 \cf2  range(0, 101, 10):\
            pwm.ChangeDutyCycle(duty_cycle)\
            print(f
\f1\b \cf3 "Throttle at \{duty_cycle\}%"
\f0\b0 \cf2 )\
            sleep(0.5)\
\
       
\f1\b \cf7  # Reverse throttle from 100% to 0%
\f0\b0 \cf2 \
        
\f1\b \cf6 for
\f0\b0 \cf2  duty_cycle 
\f1\b \cf6 in
\f0\b0 \cf2  range(100, -1, -10):\
            pwm.ChangeDutyCycle(duty_cycle)\
            print(f
\f1\b \cf3 "Throttle at \{duty_cycle\}%"
\f0\b0 \cf2 )\
            sleep(0.5)\
\

\f1\b \cf6 except
\f0\b0 \cf2  KeyboardInterrupt:\
    
\f1\b \cf6 pass
\f0\b0 \cf2  
\f1\b \cf7  # Gracefully exit on Ctrl+C
\f0\b0 \cf2 \
\

\f1\b \cf6 finally
\f0\b0 \cf2 :\
    pwm.stop() 
\f1\b \cf7  # Stop PWM
\f0\b0 \cf2 \
    GPIO.cleanup() 
\f1\b \cf7  # Clean up all GPIO
\f0\b0 \cf2 \

\f1\b \cf6 import
\f0\b0 \cf2  RPi.GPIO 
\f1\b \cf6 as
\f0\b0 \cf2  GPIO\

\f1\b \cf6 from
\f0\b0 \cf2  time 
\f1\b \cf6 import
\f0\b0 \cf2  sleep\
\

\f1\b \cf7 # Set up the GPIO pin for PWM
\f0\b0 \cf2 \
CONTROL_PIN = 18 
\f1\b \cf7  # GPIO pin connected to control signal
\f0\b0 \cf2 \
GPIO.setmode(GPIO.BCM)\
GPIO.setup(CONTROL_PIN, GPIO.OUT)\
\

\f1\b \cf7 # Initialize PWM at 50Hz (typical for servo control)
\f0\b0 \cf2 \
pwm = GPIO.PWM(CONTROL_PIN, 50)\
pwm.start(0) 
\f1\b \cf7  # Start PWM with 0% duty cycle (no throttle)
\f0\b0 \cf2 \
\

\f1\b \cf6 try
\f0\b0 \cf2 :\
    
\f1\b \cf6 while
\f0\b0 \cf2  
\f1\b \cf8 True
\f0\b0 \cf2 :\
       
\f1\b \cf7  # Set throttle to 0% to 100% (duty cycle varies depending on ESC or servo requirements)
\f0\b0 \cf2 \
        
\f1\b \cf6 for
\f0\b0 \cf2  duty_cycle 
\f1\b \cf6 in
\f0\b0 \cf2  range(0, 101, 10):\
            pwm.ChangeDutyCycle(duty_cycle)\
            print(f
\f1\b \cf3 "Throttle at \{duty_cycle\}%"
\f0\b0 \cf2 )\
            sleep(0.5)\
\
       
\f1\b \cf7  # Reverse throttle from 100% to 0%
\f0\b0 \cf2 \
        
\f1\b \cf6 for
\f0\b0 \cf2  duty_cycle 
\f1\b \cf6 in
\f0\b0 \cf2  range(100, -1, -10):\
            pwm.ChangeDutyCycle(duty_cycle)\
            print(f
\f1\b \cf3 "Throttle at \{duty_cycle\}%"
\f0\b0 \cf2 )\
            sleep(0.5)\
\

\f1\b \cf6 except
\f0\b0 \cf2  KeyboardInterrupt:\
    
\f1\b \cf6 pass
\f0\b0 \cf2  
\f1\b \cf7  # Gracefully exit on Ctrl+C
\f0\b0 \cf2 \
\
                                                                                        
\f1\b \cf9 \cb10 [ File 'pwr.py' is unwritable ]
\f0\b0 \cf2 \cb1 \
\cf5 \cb2 ^G\cf2 \cb1  Help          \cf5 \cb2 ^O\cf2 \cb1  Write Out     \cf5 \cb2 ^W\cf2 \cb1  Where Is      \cf5 \cb2 ^K\cf2 \cb1  Cut           \cf5 \cb2 ^T\cf2 \cb1  Execute       \cf5 \cb2 ^C\cf2 \cb1  Location      \cf5 \cb2 M-U\cf2 \cb1  Undo         \cf5 \cb2 M-A\cf2 \cb1  Set Mark     \cf5 \cb2 M-]\cf2 \cb1  To Bracket   \cf5 \cb2 M-Q\cf2 \cb1  Previous     \cf5 \cb2 ^B\cf2 \cb1  Back          \cf5 \cb2 ^\uc0\u9666 \cf2 \cb1  Prev Word\
\cf5 \cb2 ^X\cf2 \cb1  Exit          \cf5 \cb2 ^R\cf2 \cb1  Read File     \cf5 \cb2 ^\\\cf2 \cb1  Replace       \cf5 \cb2 ^U\cf2 \cb1  Paste         \cf5 \cb2 ^J\cf2 \cb1  Justify       \cf5 \cb2 ^/\cf2 \cb1  Go To Line    \cf5 \cb2 M-E\cf2 \cb1  Redo         \cf5 \cb2 M-6\cf2 \cb1  Copy         \cf5 \cb2 ^Q\cf2 \cb1  Where Was     \cf5 \cb2 M-W\cf2 \cb1  Next         \cf5 \cb2 ^F\cf2 \cb1  Forward       \cf5 \cb2 ^\uc0\u9656 \cf2 \cb1  Next Word\
}