{\rtf1\ansi\ansicpg1252\cocoartf2761
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fnil\fcharset0 Menlo-Bold;\f1\fnil\fcharset0 Menlo-Regular;}
{\colortbl;\red255\green255\blue255;\red56\green185\blue199;\red0\green0\blue0;\red202\green51\blue35;
\red219\green39\blue218;\red57\green192\blue38;}
{\*\expandedcolortbl;;\cssrgb\c25546\c77007\c82023;\csgray\c0;\cssrgb\c83899\c28663\c18026;
\cssrgb\c89513\c29736\c88485;\cssrgb\c25706\c77963\c19557;}
\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardirnatural\partightenfactor0

\f0\b\fs22 \cf2 \CocoaLigature0 mport
\f1\b0 \cf3  time\

\f0\b \cf2 import
\f1\b0 \cf3  lgpio\
\

\f0\b \cf4 # Set up the GPIO pins
\f1\b0 \cf3 \
TRIG = 23\
ECHO = 24\
handle = lgpio.gpiochip_open(0)\
\

\f0\b \cf4 # Set trigger pin as output
\f1\b0 \cf3 \
lgpio.gpio_claim_output(handle, TRIG)\
lgpio.gpio_claim_input(handle, ECHO)\
\

\f0\b \cf2 try
\f1\b0 \cf3 :\
    
\f0\b \cf2 while
\f1\b0 \cf3  
\f0\b \cf5 True
\f1\b0 \cf3 :\
       
\f0\b \cf4  # Send pulse to trigger
\f1\b0 \cf3 \
        lgpio.gpio_write(handle, TRIG, 1)\
        time.sleep(0.00001)\
        lgpio.gpio_write(handle, TRIG, 0)\
\
       
\f0\b \cf4  # Wait for echo response
\f1\b0 \cf3 \
        
\f0\b \cf2 while
\f1\b0 \cf3  lgpio.gpio_read(handle, ECHO) == 0:\
            pulse_start = time.time()\
        
\f0\b \cf2 while
\f1\b0 \cf3  lgpio.gpio_read(handle, ECHO) == 1:\
            pulse_end = time.time()\
\
       
\f0\b \cf4  # Calculate distance
\f1\b0 \cf3 \
        pulse_duration = pulse_end - pulse_start\
        distance = pulse_duration * 17150 
\f0\b \cf4  # cm
\f1\b0 \cf3 \
        print(f
\f0\b \cf6 "Distance: \{distance:.2f\} cm"
\f1\b0 \cf3 )\
        time.sleep(1)\
\

\f0\b \cf2 except
\f1\b0 \cf3  KeyboardInterrupt:\
    print(
\f0\b \cf6 "Program interrupted by user."
\f1\b0 \cf3 )\
\

\f0\b \cf2 finally
\f1\b0 \cf3 :\
    lgpio.gpiochip_close(handle)\
\
\
\
\
\
\
}