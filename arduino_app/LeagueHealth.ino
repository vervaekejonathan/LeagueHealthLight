void setup(){
  Serial.begin(9600);
}

int receivedByte;
void loop(){
  if(Serial.available() > 0)
  {
    receivedByte = Serial.read();
    if(receivedByte > 100)
    {
      digitalWrite(8, HIGH);
      digitalWrite(7, LOW);
      digitalWrite(6, LOW);
    }
    else
    {
      digitalWrite(8, LOW);
      digitalWrite(7, HIGH);
      digitalWrite(6, HIGH);
      
    }
  }
}

