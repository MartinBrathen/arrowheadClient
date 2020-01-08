package eu.arrowhead.client.provider;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
import java.io.*;


public class TemperatureReader{
  
  public double readTemperature(){
      
      /*
       * The coming try-catch tries to set up the w1-folder in /sys/bus/ automatically. However, if you are not running under the default user on your pi (root)
       * this wont work since you will be prompted for a password when running 'sudo commands'. Hence, in that case, you will have to run the commands manually
      */
      
      try{
        Runtime.getRuntime().exec("sudo modprobe w1-gpio");
        Runtime.getRuntime().exec("sudo modprobe w1-therm");
      }catch(Exception e){
        System.out.println("You need to run: 'sudo modprobe w1-gpio' AND 'sudo modprobe w1-therm' manually before running startUp.sh");
      }
   
      W1Master w1Master = new W1Master();
      
      /*
       * Check for the current devices connected to the pins on the raspberry pi. The devices can be found in the /sys/bus/w1/devices. A temperature sensors ID/name
       *  will always begin with '28-xxxxxxxxxxxx' and because of that we only need to check if there is any device that follows this type of pattern. The temperature
       * sensor that is found (if any is found) will then return the sensored temperature.
      */
      
      for(TemperatureSensor device : w1Master.getDevices(TemperatureSensor.class)){	      
          
          if(device.getName().contains("28-00000931128a")){  //Makes sure the device is the temperature sensor
              return device.getTemperature(TemperatureScale.CELSIUS);
          }
          
      }
      
      return 0;
  }

}
