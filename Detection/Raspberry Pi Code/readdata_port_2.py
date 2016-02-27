#!/usr/bin/python
import serial, requests, time, json, io
port1 = '/dev/cu.usbmodem1411'
port2 = '/dev/cu.usbmodem1421'
ser = serial.Serial(port2,  9600)
#url = 'http://detectionservices.herokuapp.com'
url = 'http://localhost:3000'

if ser.closed:
    ser.open()

while 1:
    print('test from outside')
    try:
        line = ser.readline().decode().strip('\r\n')
        print("line looks like this" + line)
        json = json.loads(line)
        print('test from inside')
        print(json)
        data = {}
        data['notification'] = json['notification']
        data['serial_no'] = json['serialNo']
        data['duration_in_sec'] = json['time']
        print(data)
        res = requests.post(url + '/detection', data=data)
        if (res.status_code == 200):
            print("Successfully posted")
            break
        else:
            print(res.content)
            print(res.reason)
    except (ValueError, AttributeError, io.BlockingIOError, serial.serialutil.SerialException)as e:
        print("Readline temporarily unavailable. Trying again. Exception: ", e)
