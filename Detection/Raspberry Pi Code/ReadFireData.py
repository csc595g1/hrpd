import serial, requests, time

ser = serial.Serial('/dev/cu.usbmodem1411', 9600, timeout=10)
webServiceUrl = 'http://requestb.in/um1pa7um'
if ser.closed:
    ser.open()

while 1:
    try:
        print("Checking fire status...")
 
        line = ser.readline().decode().strip('\r\n')
        if line == "Fire Alert!":
            print("Sending to server " + line)            
            requests.post(webServiceUrl, data={"flame-sensor-reading":line})
            time.sleep(2)
        else:
            print(line)
    except (BlockingIOError, serial.serialutil.SerialException)as e:
        print("Readline temporarily unavailable. Trying again. Exception: ", e)
