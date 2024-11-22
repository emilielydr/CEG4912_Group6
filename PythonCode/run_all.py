{\rtf1\ansi\ansicpg1252\cocoartf2761
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fnil\fcharset0 Menlo-Bold;\f1\fnil\fcharset0 Menlo-Regular;}
{\colortbl;\red255\green255\blue255;\red56\green185\blue199;\red0\green0\blue0;\red202\green51\blue35;
\red86\green32\blue244;\red219\green39\blue218;\red57\green192\blue38;}
{\*\expandedcolortbl;;\cssrgb\c25546\c77007\c82023;\csgray\c0;\cssrgb\c83899\c28663\c18026;
\cssrgb\c41681\c25958\c96648;\cssrgb\c89513\c29736\c88485;\cssrgb\c25706\c77963\c19557;}
\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardirnatural\partightenfactor0

\f0\b\fs22 \cf2 \CocoaLigature0 import
\f1\b0 \cf3  threading\

\f0\b \cf2 import
\f1\b0 \cf3  time\

\f0\b \cf2 import
\f1\b0 \cf3  subprocess\
\

\f0\b \cf4 # Function to run each script
\f1\b0 \cf3 \

\f0\b \cf2 def\cf5  run_script
\f1\b0 \cf3 (script_name):\
    
\f0\b \cf2 try
\f1\b0 \cf3 :\
        
\f0\b \cf2 while
\f1\b0 \cf3  
\f0\b \cf6 True
\f1\b0 \cf3 :\
            print(f
\f0\b \cf7 "Running \{script_name\}..."
\f1\b0 \cf3 )\
            subprocess.run([
\f0\b \cf7 "python3"
\f1\b0 \cf3 , script_name])\
            time.sleep(1)\
    
\f0\b \cf2 except
\f1\b0 \cf3  Exception 
\f0\b \cf2 as
\f1\b0 \cf3  e:\
        print(f
\f0\b \cf7 "Error in \{script_name\}: \{e\}"
\f1\b0 \cf3 )\
\

\f0\b \cf4 # List of scripts to run
\f1\b0 \cf3 \
scripts = [
\f0\b \cf7 "acc.py"
\f1\b0 \cf3 , 
\f0\b \cf7 "ads.py"
\f1\b0 \cf3 , 
\f0\b \cf7 "sens.py"
\f1\b0 \cf3 , 
\f0\b \cf7 "temp.py"
\f1\b0 \cf3 ]\
\

\f0\b \cf4 # Create a thread for each script
\f1\b0 \cf3 \
threads = []\
\

\f0\b \cf2 for
\f1\b0 \cf3  script 
\f0\b \cf2 in
\f1\b0 \cf3  scripts:\
    thread = threading.Thread(target=run_script, args=(script,))\
    threads.append(thread)\
    thread.start()\
\

\f0\b \cf4 # Keep the main program running
\f1\b0 \cf3 \

\f0\b \cf2 try
\f1\b0 \cf3 :\
    
\f0\b \cf2 while
\f1\b0 \cf3  
\f0\b \cf6 True
\f1\b0 \cf3 :\
        time.sleep(1) 
\f0\b \cf4  # Main thread does nothing, just waits
\f1\b0 \cf3 \

\f0\b \cf2 except
\f1\b0 \cf3  KeyboardInterrupt:\
    print(
\f0\b \cf7 "Program stopped."
\f1\b0 \cf3 )\
   
\f0\b \cf4  # Gracefully stop all threads
\f1\b0 \cf3 \
    
\f0\b \cf2 for
\f1\b0 \cf3  thread 
\f0\b \cf2 in
\f1\b0 \cf3  threads:\
        thread.join()\
    print(
\f0\b \cf7 "All threads stopped."
\f1\b0 \cf3 )\
\
\
\
\
\
\
\
}