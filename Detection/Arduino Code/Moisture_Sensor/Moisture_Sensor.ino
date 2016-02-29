/* Flame Sensor analog example.
Code by Reichenstein7 (thejamerson.comd
For use with a Rain Sensor with an analog out!

To test view the output, point a serial monitor such as Putty at your Arduino. 

  - If the Sensor Board is completely soaked; "case 0" will be activated and " Flood " will be sent to the serial monitor.
  - If the Sensor Board has water droplets on it; "case 1" will be activated and " Rain Warning " will be sent to the serial monitor.
  - If the Sensor Board is dry; "case 2" will be activated and " Not Raining " will be sent to the serial monitor. 

*/

// lowest and highest sensor readings:
const int sensorMin = 0;     // sensor minimum
const int sensorMax = 1024;  // sensor maximum
const String serialNo = "X000LP8W23"; 
const String sensorType = "water";

int duration = 0; 
int old_range = 2; 
int last_posted = -1; 


void setup() {
  Serial.begin(9600);  
}

void loop() {
  // read the sensor on analog A0:
  int sensorReading = analogRead(A0);
  // map the sensor range (four options):
  // ex: 'long int map(long int, long int, long int, long int, long int)'
  int range = map(sensorReading, sensorMin, sensorMax, 0, 3);

   if (range == old_range) {
      duration = duration + 1; 
   } else {
      duration = 0;
   }

   String notification; 
   boolean to_send = false; 
    
   if (range < 2) {
      notification = (range == 1) ? "Moisture Warning" : "Moisture Flood";
      to_send = true; 
   } else if (old_range < 2 && range == 2) {
      notification = "Good news: Moisture all dried up";
      to_send = false; 
   } 
   old_range = range; 
   
   String json = "{\"sensor\":\"" + sensorType + "\",\"time\":\"" + String(duration) + "\",\"level\":\"" + String(range) + "\",\"serialNo\":\"" + serialNo + "\",\"sensorReading\":\"" + sensorReading + "\",\"notification\":\"" + notification + "\"}";

    // post to serial port if not posted in last 30 minutes 
   if (to_send) {
     Serial.println(json); 
     delay(1000); 
   }
  delay(1);  // delay between reads
}


