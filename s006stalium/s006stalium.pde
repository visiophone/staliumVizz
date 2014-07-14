// MPE includes
import mpe.Process;
import mpe.Configuration;
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


void setup() {

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
    strokeWeight(.1);
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
void draw() {

  
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


void keyPressed() {



  //process.broadcast(Character.toString(key));
  process.broadcast(new String("1"));
  //if (key == CODED) {
  //    if (keyCode == UP) posMPrev.y=posM.y-35;      
  //    if (keyCode == DOWN) posMPrev.y=posMPrev.y+35;  
  //    if (keyCode == RIGHT) posMPrev.x=posMPrev.x+35;      
  //    if (keyCode == LEFT) posMPrev.x=posMPrev.x-35;      
  //}
}




void mouseDragged()
{
  //float nr=mouseX;
 // process.broadcast(1);
  //process.broadcast(mouseY);
  
}


