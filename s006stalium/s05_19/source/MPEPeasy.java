import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import peasy.*; 
import mpe.Process; 
import mpe.Configuration; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MPEPeasy extends PApplet {


PeasyCam cam;

// MPE includes



// MPE Process thread
Process process;

// MPE Configuration object
Configuration tileConfig;

public void setup() {
  
  // create a new configuration object and specify the path to the configuration file
  tileConfig = new Configuration(dataPath("configuration_stallion_1_all_noxinerama.xml"), this);
  
  // set the size of the sketch based on the configuration file
  size(tileConfig.getLWidth(), tileConfig.getLHeight(), P3D);
  
  // create a new process
  process = new Process(tileConfig);
  
  // disable camera placement by MPE, because it interferes with PeasyCam
  process.disableCameraReset();
 
  // initialize the peasy cam
  cam = new PeasyCam(this, 3000);
  cam.setMinimumDistance(0);
  cam.setMaximumDistance(5000);
  
  if(tileConfig.isLeader())
      strokeWeight(.1f);
  
  // start the MPE process
  process.start();
}
public void draw() {
  
  // synchronize this process' camera with the headnode
  if(process.messageReceived())
  {
    // set the animation time to 0, otherwise we get weird behavior
    cam.setState((CameraState) process.getMessage(), 0);
  }  
  
  // draw a couple boxes
  scale(5);
  rotateX(-.5f);
  rotateY(-.5f);
  background(0);
  fill(255,0,0);
  box(200);
  pushMatrix();
  translate(0,0,200);
  fill(0,0,255);
  box(50);
  popMatrix();
}

// when the master process receives a mouse event, broadcast the update camera state to the other processes
public void mouseDragged()
{
  process.broadcast(cam.getState());
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MPEPeasy" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
