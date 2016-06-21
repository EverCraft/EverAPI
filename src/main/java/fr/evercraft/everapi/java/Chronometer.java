package fr.evercraft.everapi.java;

public class Chronometer {
	private final long start;
	
	private Long stop;
	 
    public Chronometer(){
    	this.start = System.nanoTime();
    }
 
    public void stop(){
    	if(this.stop == null) {
    		this.stop = System.nanoTime();
    	}
    }
 
    public Long getTime() {
    	stop();
        return this.stop - this.start;
    }
 
    public Double getMilliseconds() {
    	stop();
        return (this.stop - this.start) / 1000000.0;
    }
}
