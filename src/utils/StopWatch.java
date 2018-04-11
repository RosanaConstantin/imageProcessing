package utils;

public class StopWatch {

	  private long startTime = 0;
	  private long stopTime = 0;
	  private boolean running = false;


	  public void start() {
	    this.startTime = System.nanoTime();
	    this.running = true;
	  }


	  public void stop() {
	    this.stopTime = System.nanoTime();
	    this.running = false;
	  }


	  //elaspsed time in microseconds
	  public long getElapsedTime() {
	    long elapsed;
	    if (running) {
	      elapsed = (System.nanoTime() - startTime) / 1000;
	    }
	    else {
	      elapsed = (stopTime - startTime) / 1000;
	    }
	    //System.out.println(elapsed);
	    return elapsed;
	  }


	  //elaspsed time in milliseconds
	  public double getElapsedTimeMillisec() {
	    float elapsed;
	    if (running) {
	      elapsed = ((System.nanoTime() - startTime) / 1000000000);
	    }
	    else {
	      elapsed = ((stopTime - startTime) / 1000000000);
	    }
	    return elapsed;
	  }
	}