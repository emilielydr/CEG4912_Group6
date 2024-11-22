{\rtf1\ansi\ansicpg1252\cocoartf2761
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fnil\fcharset0 Menlo-Bold;\f1\fnil\fcharset0 Menlo-Regular;}
{\colortbl;\red255\green255\blue255;\red56\green185\blue199;\red0\green0\blue0;\red20\green153\blue2;
\red202\green51\blue35;\red219\green39\blue218;\red57\green192\blue38;}
{\*\expandedcolortbl;;\cssrgb\c25546\c77007\c82023;\csgray\c0;\cssrgb\c0\c65000\c0;
\cssrgb\c83899\c28663\c18026;\cssrgb\c89513\c29736\c88485;\cssrgb\c25706\c77963\c19557;}
\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardirnatural\partightenfactor0

\f0\b\fs22 \cf2 \CocoaLigature0 import
\f1\b0 \cf3  time\cb4  \cb1 \

\f0\b \cf2 import
\f1\b0 \cf3  board\

\f0\b \cf2 import
\f1\b0 \cf3  busio\

\f0\b \cf2 import
\f1\b0 \cf3  adafruit_ads1x15.ads1115 
\f0\b \cf2 as
\f1\b0 \cf3  ADS\

\f0\b \cf2 from
\f1\b0 \cf3  adafruit_ads1x15.analog_in 
\f0\b \cf2 import
\f1\b0 \cf3  AnalogIn\
\
i2c = busio.I2C(board.SCL, board.SDA)\
\

\f0\b \cf5 #create the ads object and specify the gain
\f1\b0 \cf3 \
ads = ADS.ADS1115(i2c)\
ads.gain =1\cb4  \cb1 \
chan = AnalogIn(ads, ADS.P0)\
\

\f0\b \cf5 #Countinuously print the values
\f1\b0 \cf3 \

\f0\b \cf2 while
\f1\b0 \cf3  
\f0\b \cf6 True
\f1\b0 \cf3 :\
        print(f
\f0\b \cf7 "MQ-135 Voltage: \{chan.voltage\}V"
\f1\b0 \cf3 )\
        time.sleep(1)\
\
\
\
\
\
\
\
}