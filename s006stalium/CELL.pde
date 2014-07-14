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
    freq=0.0;
  } 

  // Oscillation means increase angle
  void oscillate() {
    angle += 0.02;
  }

  void display() {

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
      int lineH=int((hh/5));
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

      float depth=map(freq, 0, 10, 1, 0.05);
      depth=constrain(depth, 0, 1);
      println(depth);

      for (int i=0;i<10;i++) {
        float val=lerp(1, depth, i/10.0);
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
        float depth=map(freq, 0, 8, 1, 0.002);
        println(depth);
        depth=constrain(depth, 0.0, 1.0);


        for (int i=0;i<20;i++) {
          float val=lerp(1, depth, i/20.0);
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
       
        float vall=map(freq,0,10,1,0.05);
        strokeWeight(5);
        stroke(255);
     
     for (int i=0;i<50;i++) {
        float val=lerp(0, vall, i/50.0);
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
        float val=lerp(0, vall, i/50.0);
        
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
  void update ( float freqTemp ) {  
    freq+=(freqTemp-freq)*0.1;
  }
}

