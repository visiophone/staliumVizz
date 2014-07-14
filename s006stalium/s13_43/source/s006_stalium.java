import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import mpe.Process; 
import mpe.Configuration; 
import ddf.minim.analysis.*; 
import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class s006_stalium extends PApplet {

///MP3 STUFF
// MPE includes


// MPE Process thread
Process process;
int w, h;
// MPE Configuration object
Configuration tileConfig;

///
// 2D Array of objects
Cell[][] grid;

// Number of columns and rows in the grid
int cols = 16;
int rows = 5;


public void setup() {

 // size(1600,500);
  
  ////////////////////////////////////////////////////////////////////////////////////////////////////////  
  ///MPE STUFF
  // create a new configuration object and specify the path to the configuration file
  tileConfig = new Configuration(dataPath("configuration_stallion_all_noxinerama_nobezel.xml"), this);
  // set the size of the sketch based on the configuration file
  size(tileConfig.getLWidth(), tileConfig.getLHeight(), P3D);
  if (!tileConfig.isLeader()) {
    width=tileConfig.getMWidth();
    height=tileConfig.getMHeight();
  }
  // create a new process
  process = new Process(tileConfig);
  // disable camera placement by MPE, because it interferes with PeasyCam
 //  process.disableCameraReset();
   

   
  if (tileConfig.isLeader())
    strokeWeight(.1f);
  // start the MPE process
  process.start();

  ///////////////////////////////////////////////////////////////////////////////////////////////////////// 

//GRID
int count=0;
grid = new Cell[cols][rows];
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      // Initialize each object
     // grid[i][j] = new Cell(i*2560,j*1600,2560,1600,i+j,count);
      grid[i][j] = new Cell(i*(width/16),j*(height/5),width/16,height/5,i+j,count);
      count++;
    }
  }

//audio
initAudio();

}
public void draw() {

  
    if(process.messageReceived())
  {
    // set the animation time to 0, otherwise we get weird behavior
    println("Message received "+process.getMessage());
    //mode = Integer.parseInt((String) process.getMessage());
    if(mode>0) mode--;
    else mode=maxMode;
    println("Setting mode = " + mode);
    //mode= modeInteger.intValue();
  } 
  
  
background(0);

audioAnalize();

    // The counter variables i and j are also the column and row numbers and 
  // are used as arguments to the constructor for each object in the grid.  
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      // Oscillate and display each object
      //grid[i][j].oscillate();
      grid[i][j].display();
    }
  }  
 

}





public void mouseDragged()
{
  //float nr=mouseX;
 // process.broadcast(1);
  //process.broadcast(mouseY);
  
}


// A Cell object
class Cell {
  // A cell object knows about its location in the grid as well as its size with the variables x,y,w,h.
  float x, y;   // x,y location
  float ww, hh;   // width and height
  float angle; // angle for oscillating brightness
  int id;

  float freq;

  // Cell Constructor
  Cell(float tempX, float tempY, float tempW, float tempH, float tempAngle, int tempId) {
    x = tempX;
    y = tempY;
    ww = tempW;
    hh = tempH;
    angle = tempAngle;
    id=tempId;
    freq=0.0f;
  } 

  // Oscillation means increase angle
  public void oscillate() {
    angle += 0.02f;
  }

  public void display() {

    if (mode==0) {

      if (id>=0 && id<=4) {
        rectMode(CORNER);
        noStroke();
        float side=freq*400;

        fill(255); 
        rect(x, y, 2+side*16, height/5);
        //  println(id+ " " + freq);
      }
    }

    //mode 1 barras laterais  
    if (mode==1) {
      rectMode(CORNER);
      noStroke();
      float side=freq*400;

      fill(255); 
      rect(x, y, 2+side, height/5);
      //  println(id+ " " + freq);
    }

    //quadrados todos pintados
    if (mode==2) {
      rectMode(CORNER);
      noStroke();
      ww=(width/16);
      fill(map(freq, 0, 10, 0, 255)); 
      rect(x, y, ww, height/5);
      //if(id==0) rect(x,y,ww,height);
      // else rect(x,y,ww,height/5);
      //   println(id+ " " + freq);
    }
    //cada quadrado dividido em barras  
    if (mode==3) {
      rectMode(CORNER);
      ww=(width/16);
      fill(map(freq, 0, 10, 0, 255)); 
      int lineH=PApplet.parseInt((hh/5));
      //println(hh+" "+lineH);
      // int lineH=20;
      strokeWeight(5);
      stroke(0);
      rect(x, y, ww, lineH/4);
      fill(map(freq, 0, 10, 0, 255)-10); 
      rect(x, y+lineH, ww, lineH/4);

      fill(map(freq, 0, 10, 0, 255)-20); 
      rect(x, y+(lineH*2), ww, lineH/4);

      fill(map(freq, 0, 10, 0, 255)-30); 
      rect(x, y+(lineH*3), ww, lineH/4);

      fill(map(freq, 0, 10, 0, 255)-40); 
      rect(x, y+(lineH*4), ww, lineH/4);
    }


    //cada quadrado dividido em barras  
    if (mode==4) {

      strokeWeight(5);
      float side=map(freq, 0, 10, -ww, ww);
      stroke(255);
      line(x+ww/2, y+hh/2, x+side, y+(hh/2));
      fill(map(freq, 0, 10, 0, 255)); 
      ellipse(x+ww/2, y+hh/2, 400, 400);
    }  



    if (mode==5) {

      strokeWeight(5);
      float side=map(freq, 0, 10, 100, 900);
      stroke(255);
     line(x-ww/2, y+hh/2, x+ww/2, y+(hh/2));
     // fill(map(freq, 0, 10, 0, 255)); 
     fill(255);
      ellipse(x+ww/2, y+hh/2, side, side);
    }  

    if (mode==6)
    {
      rectMode(CENTER);
      strokeWeight(5);
      noFill();
      stroke(255);
      rect(x+ww/2, y+hh/2, ww, hh);

      //float depth=0.1;

      float depth=map(freq, 0, 10, 1, 0.05f);
      depth=constrain(depth, 0, 1);
      println(depth);

      for (int i=0;i<10;i++) {
        float val=lerp(1, depth, i/10.0f);
        rect(x+ww/2, y+hh/2, ww*val, hh*val);
      }
    }

    if (mode==7)
    {
      rectMode(CENTER);
      strokeWeight(15);
      noFill();
      stroke(255);


      //float depth=0.1;
      if (id==37) {

        rect(x+ww/2, y+hh/2, width, height);
        float depth=map(freq, 0, 8, 1, 0.002f);
        println(depth);
        depth=constrain(depth, 0.0f, 1.0f);


        for (int i=0;i<20;i++) {
          float val=lerp(1, depth, i/20.0f);
          rect(x+ww/2, y+hh/2, width*val, height*val);
        }
      }
      // rect(x+ww/2,y+hh/2,ww*0.8,hh*0.8);
      //rect(x+200,y+200,ww,hh);
    }
    
    if (mode==8)
    {
      
      if(id==35)
      {
       
        float vall=map(freq,0,10,1,0.05f);
        strokeWeight(5);
        stroke(255);
     
     for (int i=0;i<50;i++) {
        float val=lerp(0, vall, i/50.0f);
        line(x-width, height*val, x+width, height*val);
      }
        
      }
      
    }
    
       if (mode==9)
    {
      
      if(id>=35 && id<=39)
      {
       
        float vall=map(freq,0,10,0,width/2);
        vall=constrain(vall,0,width/2);
        strokeWeight(5);
        stroke(255);
     
   //  line(x,y+(hh/2),x-vall,y+(hh/2));
    // line(x+ww,y+(hh/2),x+vall,y+(hh/2));
     
     for (int i=0;i<40;i++) {
        float val=lerp(0, vall, i/50.0f);
        
        line(x,y+(hh/40)*i,x-vall,y+(hh/40)*i);
        
         line(x+ww,y+(hh/40)*i,x+ww+vall,y+(hh/40)*i);
        
        //line(x-width, height*val, x+width, height*val);
      }
        
      }
      
    }
    
     if (mode==10)
    {
      
      if(id>=35 && id<=39)
      {
       
        float vall=map(freq,0,10,0,1000);
        strokeWeight(vall);
        stroke(255);
       // line(x-width/2,y/2,width/2,y/2);
        line(x-width/2,y+hh/2,(x+ww)+width/2,y+hh/2);
        
      }
      
    }
    
    
  }







  //gets info from current frequency
  public void update ( float freqTemp ) {  
    freq+=(freqTemp-freq)*0.1f;
  }
}




Minim minim;  
AudioPlayer jingle;
FFT fftLin;

float spectrumScale = 2;

int mode=9;
int maxMode=10;

public void initAudio() {

  //Audio Stuff
  minim = new Minim(this);
  jingle = minim.loadFile("submersible.wav", 1024);
  // loop the file
  jingle.loop(); 
  //Create a buffer
  fftLin = new FFT( jingle.bufferSize(), jingle.sampleRate() );
  // calculate the averages by grouping frequency bands linearly. use 30 averages.
  fftLin.linAverages( 81 );
}


public void audioAnalize() {

  float centerFrequency = 0; 
  // perform a forward FFT on the samples in jingle's mix buffer
  fftLin.forward( jingle.mix );

  //println(fftLin.getAvg(0)+" "+fftLin.getAvg(1)+" "+fftLin.getAvg(2)+" "+fftLin.getAvg(3)); 



  int count=1;
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      grid[i][j].update((fftLin.getAvg(count)*spectrumScale));
      count++;
    }
  }
}



public void keyPressed() {



  //process.broadcast(Character.toString(key));
  process.broadcast(new String("1"));
  //if (key == CODED) {
  //    if (keyCode == UP) posMPrev.y=posM.y-35;      
  //    if (keyCode == DOWN) posMPrev.y=posMPrev.y+35;  
  //    if (keyCode == RIGHT) posMPrev.x=posMPrev.x+35;      
  //    if (keyCode == LEFT) posMPrev.x=posMPrev.x-35;      
  //}
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "s006_stalium" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
