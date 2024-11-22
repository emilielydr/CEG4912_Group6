{\rtf1\ansi\ansicpg1252\cocoartf2761
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fnil\fcharset0 Menlo-Bold;\f1\fnil\fcharset0 Menlo-Regular;}
{\colortbl;\red255\green255\blue255;\red56\green185\blue199;\red0\green0\blue0;\red219\green39\blue218;
\red20\green153\blue2;\red57\green192\blue38;}
{\*\expandedcolortbl;;\cssrgb\c25546\c77007\c82023;\csgray\c0;\cssrgb\c89513\c29736\c88485;
\cssrgb\c0\c65000\c0;\cssrgb\c25706\c77963\c19557;}
\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardirnatural\partightenfactor0

\f0\b\fs22 \cf2 \CocoaLigature0 from
\f1\b0 \cf3  picamzero 
\f0\b \cf2 import
\f1\b0 \cf3  Camera\

\f0\b \cf2 from
\f1\b0 \cf3  time 
\f0\b \cf2 import
\f1\b0 \cf3  sleep\
\
cam =Camera()\
cam.start_preview()\

\f0\b \cf2 try
\f1\b0 \cf3 :\
        
\f0\b \cf2 while
\f1\b0 \cf3  
\f0\b \cf4 True
\f1\b0 \cf3 :\cb5      \cb1 \
                sleep(5)\

\f0\b \cf2 except
\f1\b0 \cf3  KeyboardInterrupt:\
        print(
\f0\b \cf6 "interupted"
\f1\b0 \cf3 )\

\f0\b \cf2 finally
\f1\b0 \cf3 :\
        cam.stop_preview()\
\
\
\
\
\
}