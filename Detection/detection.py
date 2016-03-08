#!/usr/bin/python
import serial, requests, time, json, io
port = '/dev/ttyACM1'
port2 = '/dev/ttyACM0'
ser = serial.Serial(port,  9600, timeout=0.50)
ser2 = serial.Serial(port2, 9600, timeout=0.50)
url = 'http://detectionservices.herokuapp.com'
#url = 'http://localhost:3000'

if ser.closed:
    ser.open()


def sendScript( line ):
    try:
        myjson = json.loads(line)
        print(myjson)
        data = {}
        data['notification'] = myjson['notification']
        data['serial_no'] = myjson['serialNo']
        data['duration_in_sec'] = myjson['time']
        print(data)
        res = requests.post(url + '/detection', data=data)
        if (res.status_code == 200):
            print("Successfully posted")
        else:
            print(res.content)
            print(res.reason)
    except (ValueError, AttributeError, io.BlockingIOError, serial.serialutil.SerialException)as e:
        print("Error: ", e)
        return False
    return True

while 1:
    print('We are currently outside')
    try:
        line = ser.readline().decode().strip('\r\n')
        print("What serial 1 output looks like: " + line) 
        line2 = ser2.readline().decode().strip('\r\n')
        print("what serial 2 output looks like: " + line2)
        success = False
        success2 = False
        if line != '':
            success = sendScript(line)
        if line2 != '':
            success2 = sendScript(line2)
        if success:
            ser.flushInput()
        if success2:
            ser2.flushInput()
# time.sleep(900) #if both success, sleep for 15 minutes
    except (ValueError, io.BlockingIOError, serial.serialutil.SerialException)as e:
        print("Readline temporarily unavailable. Trying again. Exception: ", e)
