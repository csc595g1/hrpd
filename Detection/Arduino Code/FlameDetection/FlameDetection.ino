// Lowest and highest sensor readings:
const int sensorMin = 0;     // Sensor minimum
const int sensorMax = 1024;  // Sensor maximum
const String serialNo = "X000QCX44H"; 
const String sensorType = "fire";


int duration = 0; 
int old_range = 2; 

void setup() {
  // Initialize serial communication @ 9600 baud:
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
      notification = "Fire Alert";
      to_send = true; 
   } else if (old_range < 2 && range == 2) {
      notification = "Good news: Fire Has Died Down";
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
