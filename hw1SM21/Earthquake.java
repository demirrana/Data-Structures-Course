public class Earthquake {
	
	private String id;
	private int time;
	private String place;
	private double latitude;
	private double longitude;
	private double depth;
	private double magnitude;
	private int magnitudeIndex;
	private int queueIndex;
	
	public Earthquake(String id , int time , String place , double latitude , double longitude , double depth , double magnitude) {
		
		this.id = id;
		this.time = time;
		this.place = place;
		this.latitude = latitude;
		this.longitude = longitude;
		this.depth = depth;
		this.magnitude = magnitude;
		magnitudeIndex = 0;
		queueIndex = 0;
	}
	
	public Earthquake() {
		
		id = "";
		time = 0;
		place = "";
		latitude = 0;
		longitude = 0;
		depth = 0;
		magnitude = 0;
		magnitudeIndex = 0;
		queueIndex = 0;
	}
	
	public double getMagnitude() {
		
		return magnitude;
	}
	
	public double getLatitude() {
		
		return latitude;
	}
	
	public double getLongitude() {
		
		return longitude;
	}
	
	public double getDepth() {
		
		return depth;
	}
	
	public int getTime() {
		
		return time;
	}
	
	public String getPlace() {
		
		return place;
	}
	
	public void setMagnitudeIndex(int index) {

		magnitudeIndex = index;
	}
	
	public int getMagnitudeIndex() {
		
		return magnitudeIndex;
	}
	
	public int getQueueIndex() {
		
		return queueIndex;
	}
	
	public void setQueueIndex(int index) {
		
		queueIndex = index;
	}
}