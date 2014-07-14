import ddf.minim.analysis.*;
import ddf.minim.*;

Minim minim;  
AudioPlayer jingle;
FFT fftLin;

float spectrumScale = 2;

int mode=9;
int maxMode=10;

void initAudio() {

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


void audioAnalize() {

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




