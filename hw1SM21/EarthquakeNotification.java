import java.io.*; //DÜZENLE
import java.util.Scanner;

public class EarthquakeNotification {
	
	private static int currentTime = 0;
	private static DoublyLinkedList<Watcher> watcherList = new DoublyLinkedList<>();
	private static ArrayQueue<Earthquake> earthquakeQueue = new ArrayQueue<>(1000); //
	private static ArrayList<Earthquake> magnitudeOrderedList = new ArrayList<>(1000); //
	
	public static void main(String[] args) {
		
		String watcherFileName = "";
		String earthquakeFileName = "";
		boolean all = false; //If the --all parameter is given or not
		
		if (args.length == 3) {
			
			watcherFileName = args[1];//.substring(1 , args[1].length() - 1);
			earthquakeFileName = args[2];//.substring(1 , args[2].length() - 1);
			all = true;
		}
		
		else {
			
			watcherFileName = args[0];//.substring(1 , args[0].length() - 1);
			earthquakeFileName = args[1];//.substring(1 , args[1].length() - 1);
		}
		
		Scanner watcherFile = null;
		Scanner earthquakeFile = null;
		
		try {
			
			watcherFile = new Scanner(new File(watcherFileName));
			earthquakeFile = new Scanner(new File(earthquakeFileName));
			
		} catch (FileNotFoundException e) {
			
			System.out.println("File cannot be found");
			System.exit(0);
		}
		
		String line = ""; //To read the file's lines.
		
		ArrayList<Earthquake> earthquakes = new ArrayList<>(); //All earthquakes will be in this firstly
		
		//Variables of earthquakes
		String earthquakeID = "";
		
		int earthquakeTime = 0;
		String earthquakeTimeStr = "";
		
		String earthquakePlace = "";
		
		String coordinates = "";
		String[] coordinateString = new String[3];
		double earthquakeLatitude = 0;
		double earthquakeLongitude = 0;
		double earthquakeDepth = 0;
		
		double earthquakeMagnitude = 0;
		String magnitudeStr = "";
		
		int queueIndex = 0; //To save the order of the earthquakes in them by the queue order
		boolean hasComeToEnd = false; //If there are files that includes empty lines, this boolean value is to control it.
		
		while (!hasComeToEnd && earthquakeFile.hasNextLine()) { //Filling the queue and arraylist with earthquakes.
			
			if (queueIndex == 0)
				earthquakeFile.nextLine(); //First line of each earthquake

			earthquakeID = earthquakeFile.nextLine();
			earthquakeTimeStr = earthquakeFile.nextLine();
			earthquakePlace = earthquakeFile.nextLine();
			coordinates = earthquakeFile.nextLine();
			magnitudeStr = earthquakeFile.nextLine();
			earthquakeFile.nextLine(); //Last line of each earthquake

			if (earthquakeFile.hasNextLine())
				line = earthquakeFile.nextLine();

			if (line.equals("")) {
				
				hasComeToEnd = true;
			}
			
			//Adjusting the ID, time and place of earthquakes
			earthquakeID = earthquakeID.substring(earthquakeID.indexOf("<id> ") + 5 , earthquakeID.indexOf(" </id>"));
			earthquakeTime = Integer.parseInt(earthquakeTimeStr.substring(earthquakeTimeStr.indexOf("<time> ") + 7 , earthquakeTimeStr.indexOf(" </time>")));
			earthquakePlace = earthquakePlace.substring(earthquakePlace.indexOf("<place> ") + 8 , earthquakePlace.indexOf(" </place>"));
			
			//Adjusting the magnitude of earthquakes
			magnitudeStr = magnitudeStr.substring(magnitudeStr.indexOf("<magnitude> ") + 12 , magnitudeStr.indexOf(" </magnitude>"));
			earthquakeMagnitude = Double.parseDouble(magnitudeStr);
			
			//Adjusting the magnitude of earthquakes
			coordinates = coordinates.substring(coordinates.indexOf("<coordinates> ") + 14 , coordinates.indexOf(" </coordinates>"));
			coordinateString = coordinates.split(", ");
			earthquakeLatitude = Double.parseDouble(coordinateString[0]);
			earthquakeLongitude = Double.parseDouble(coordinateString[1]);
			earthquakeDepth = Double.parseDouble(coordinateString[2]);
			
			Earthquake earthquake = new Earthquake(earthquakeID , earthquakeTime , earthquakePlace , earthquakeLatitude , earthquakeLongitude , earthquakeDepth , earthquakeMagnitude);
			earthquake.setQueueIndex(queueIndex);
			earthquakes.add(earthquake);
			queueIndex++;
		}
		
		earthquakeFile.close();
		
		ArrayList<Watcher> watcherArrayList = new ArrayList<>();
		
		int timeStamp = 0;
		String[] watcherStr = new String[0];
		Watcher watcher = new Watcher();
		
		while (watcherFile.hasNextLine()) { //Saves the watchers to the watcherArrayList
			
			line = watcherFile.nextLine();
			
			if (line.length() == 0) //If there are empty lines it continues 
				continue;
				
			watcherStr = line.split(" ");
			timeStamp = Integer.parseInt(watcherStr[0]);
			
			if (watcherStr.length == 5) { //add operation
			
				watcher = new Watcher(timeStamp , Double.parseDouble(watcherStr[2]) , Double.parseDouble(watcherStr[3]) , watcherStr[4] , "add");
				watcherArrayList.add(watcher);
			}
			
			else if (watcherStr.length == 3) { //delete operation
				
				watcher = new Watcher(timeStamp , Integer.MIN_VALUE , Integer.MIN_VALUE , watcherStr[2] , "delete");
				watcherArrayList.add(watcher);
			}
			
			else {
				
				watcher = new Watcher(timeStamp , Integer.MIN_VALUE , Integer.MIN_VALUE , "" , "query-largest");
				watcherArrayList.add(watcher);
			}
		}
		
		watcherFile.close();
		
		int currentTime = 0;
		
		while (currentTime < 1000) { //Iteration of each time period (for an upper bound of 1000 hours)
			
			Earthquake toBeDeleted = new Earthquake(); //For the earthquakes that happened more than 6 hours ago
			
			if (!magnitudeOrderedList.isEmpty()) { //If there is at least one earthquake that happened, it erases the ones that are more than 6 hours
				
				while (true) { 
					
					if (currentTime - earthquakeQueue.first().getTime() > 6) { //If the earthquaked happened more than 6 hours ago
						
						toBeDeleted = earthquakeQueue.dequeue();
						
						for (int i = toBeDeleted.getMagnitudeIndex() + 1; i < magnitudeOrderedList.size(); i++)
							magnitudeOrderedList.get(i).setMagnitudeIndex(i - 1);
						
						magnitudeOrderedList.remove(toBeDeleted.getMagnitudeIndex());
						
						break;
					}
					else {
						
						break;
					}
				}
			}
			
			for (int i = 0; i < watcherArrayList.size(); i++) { //Operations on watchers
				
				if (currentTime == watcherArrayList.get(i).getTime()) { //If it is the right time
					
					if (watcherArrayList.get(i).getRequest().equals("add")) { //add operation
						
						watcherList.addLast(watcherArrayList.get(i));
						System.out.println(watcherArrayList.get(i).getName() + " is added to the watcher-list\n");
						watcherArrayList.remove(i);
						i--;
					}
					else if (watcherArrayList.get(i).getRequest().equals("delete")) { //delete operation
						
						for (int j = 0; j < watcherList.size(); j++) { //finds the watcher's name and deletes it
							
							if (watcherList.get(j).getName().equals(watcherArrayList.get(i).getName())) {
								
								watcherList.remove(j);
								System.out.println(watcherArrayList.get(i).getName() + " is removed from the watcher-list\n");
								watcherArrayList.remove(i);
								i--;
								break;
							}
						}
					}
					else { //query-largest operation
						
						if (earthquakeQueue.isEmpty()) {
							
							System.out.println("No record on list\n");
						}
						else {
							
							System.out.println("Largest earthquake in the past 6 hours:\nMagnitude " + earthquakeQueue.first().getMagnitude() + " at " + 
							                   earthquakeQueue.first().getPlace() + "\n");
						}
						watcherArrayList.remove(i);
						i--;
					}
				}
				
				else if (currentTime > watcherArrayList.get(i).getTime()){ //If it is not the right time
					
					continue;
				}
				
				else if (currentTime < watcherArrayList.get(i).getTime()) { //If the time has passes already breaks the loop
					
					break;
				}
			}
			
			double distance = 0;
			
			for (int i = 0; i < earthquakes.size(); i++) { //Operations on earthquakes
			
				if (earthquakes.get(i).getTime() == currentTime) { //If it the time that earthquake occurs
					
					earthquakeQueue.enqueue(earthquakes.get(i));
					
					if (magnitudeOrderedList.isEmpty()) //If it is empty, places the earthquake directly
						magnitudeOrderedList.add(earthquakes.get(i));
					
					else { //If not empty, then places accordingly
						
						for (int j = 0; j < magnitudeOrderedList.size(); j++) { //Placing the earthquake to the magnitudeOrderedList
							
							if (earthquakes.get(i).getMagnitude() > magnitudeOrderedList.get(j).getMagnitude()) {
								
								magnitudeOrderedList.add(j , earthquakes.get(i));
								break;
							}
							
							else if (j == magnitudeOrderedList.size() - 1) {
								
								magnitudeOrderedList.add(j + 1 , earthquakes.get(i));
								break;
							}
						}
					}
					
					if (args[0].equals("[--all]"))
						System.out.println("Earthquake " + earthquakes.get(i).getPlace() + " is inserted into the magnitude-ordered-list\n");
						
					for (int j = 0; j < watcherList.size(); j++) {
						
						distance = Math.sqrt((watcherList.get(j).getLatitude() - earthquakes.get(i).getLatitude()) * (watcherList.get(j).getLatitude() - earthquakes.get(i).getLatitude()) 
					    + (watcherList.get(j).getLongitude() - earthquakes.get(i).getLongitude()) * (watcherList.get(j).getLongitude() - earthquakes.get(i).getLongitude()));
						
						if (distance < 2 * Math.pow(earthquakes.get(i).getMagnitude() , 3))
							System.out.println("Earthquake " + earthquakes.get(i).getPlace() + " is close to " + watcherList.get(j).getName() + "\n");
					}
					
					earthquakes.remove(i);
					i--;
				}
				
				else if (earthquakes.get(i).getTime() > currentTime) {
					
					break;
				}
			}
			
			currentTime++;
		}
		
		earthquakeFile.close();
	}
}