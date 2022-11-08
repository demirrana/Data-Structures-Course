public class Watcher {
	
	private int timeStamp;
	private double latitude;
	private double longitude;
	private String name;
	private String request;
	
	public Watcher(int timeStamp , double latitude , double longitude , String name , String request) {
		
		this.timeStamp = timeStamp;
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.request = request;
	}
	
	public Watcher() {
		
		timeStamp = 0;
		latitude = 0;
		longitude = 0;
		name = "";
		request = "";
	}
	
	public int getTime() {
		
		return timeStamp;
	}
	
	public String getName() {
		
		return name;
	}
	
	public double getLatitude() {
		
		return latitude;
	}
	
	public double getLongitude() {
		
		return longitude;
	}
	
	public String getRequest() {
		
		return request;
	}
	
}