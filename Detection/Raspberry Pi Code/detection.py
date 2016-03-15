#!/usr/bin/python
import serial, requests, time, json, io, threading
port = '/dev/ttyACM1'
port2 = '/dev/ttyACM0'
ser = serial.Serial(port,  9600, timeout=0.50)
ser2 = serial.Serial(port2, 9600, timeout=0.50)
url = 'http://detectionservices.herokuapp.com'
#url = 'http://localhost:3000'
port1Notified = False
port2Notified = False

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
    except (KeyError, ValueError, AttributeError, io.BlockingIOError, serial.serialutil.SerialException,requests.exceptions.ConnectionError )as e:
        print("Error: ", e)
        return False
    return True

def setToFalseIn30 (variable):
    print "About to set " + variable + "back to False"
    globals()[variable] = False

def printGlobals():
    print "Port1Notified is: " + str(port1Notified)
    print "Port2Notified is: " + str(port2Notified)

def flushInput():
    ser.flushInput()
    ser2.flushInput()

while 1:
    try:
        line = ser.readline().decode().strip('\r\n')
        print("serial 1: " + line) 
        line2 = ser2.readline().decode().strip('\r\n')
        print("serial 2: " + line2)
        if line != '' and not port1Notified:
            global port1Notified
            port1Notified = sendScript(line)
            threading.Timer(30.0, setToFalseIn30, ["port1Notified"]).start()
        if line2 != '' and not port2Notified :
            global port2Notified
            port2Notified = sendScript(line2)
            threading.Timer(30.0, setToFalseIn30, ["port2Notified"]).start()
        flushInput()
        printGlobals()    
    except (io.BlockingIOError, serial.serialutil.SerialException)as e:
        print("Readline temporarily unavailable. Trying again. Exception: ", e)
