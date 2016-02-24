// Lowest and highest sensor readings:
const int sensorMin = 0;     // Sensor minimum
const int sensorMax = 1024;  // Sensor maximum

void setup() {
  // Initialize serial communication @ 9600 baud:
  Serial.begin(9600);  
}
void loop() {
  // read the sensor on analog A0:
	int sensorReading = analogRead(A0);
  // Map the sensor range (four options):
  // Ex: 'long int map(long int, long int, long int, long int, long int)'
	int range = map(sensorReading, sensorMin, sensorMax, 0, 3);
  
  // Range value:
  switch (range) {
  case 0:    // A fire closer than 1.5 feet away.
    Serial.println("Fire Alert!");
    break;
  case 1:    // A fire between 1-3 feet away.
    Serial.println("Fire Alert!");
    break;
  case 2:    // No fire detected.
    Serial.println("No Fire!");
    break;
  }
  delay(1000);  // Delay between reads.
}
