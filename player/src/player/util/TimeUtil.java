package player.util;

public class TimeUtil {

	private String hourTotal;
	private String secondTotal;
	private String minitueTotal;
	private String hourCurrent;
	private String secondCurrent;
	private String minitueCurrent;
	
	public void timeFormat(long totalTime,long currentTime){
		
		totalTime = totalTime / 1000;
		
		int hourTotalInt = (int) (totalTime / 3600);
		hourTotal = hourTotalInt < 10? "0" + hourTotalInt : "" + hourTotalInt;
		
		int minitueTotalInt = (int) (totalTime / 60);
		minitueTotal = minitueTotalInt - hourTotalInt * 60 < 10? "0" + (minitueTotalInt - hourTotalInt * 60) : "" + (minitueTotalInt - hourTotalInt * 60);
		
		int secondTotalInt = (int) (totalTime % 60);
		secondTotal = secondTotalInt < 10? "0" + secondTotalInt : "" + secondTotalInt;
		
		
		currentTime = currentTime / 1000;
		int hourCurrentInt = (int) (currentTime / 3600);
		hourCurrent = hourCurrentInt < 10? "0" + hourCurrentInt : "" + hourCurrentInt;
		
		int minitueCurrentInt = (int) (currentTime / 60);
		minitueCurrent = minitueCurrentInt - hourCurrentInt * 60 < 10? "0" + (minitueCurrentInt - hourCurrentInt * 60) : "" + (minitueCurrentInt - hourCurrentInt * 60);
		
		int secondCurrentInt = (int) (currentTime % 60);
		secondCurrent = secondCurrentInt < 10? "0" + secondCurrentInt : "" + secondCurrentInt;
	}
	
	public String getHourTotal() {
		return hourTotal;
	}

	public String getHourCurrent() {
		return hourCurrent;
	}

	public String getSecondTotal() {
		return secondTotal;
	}
	public String getMinitueTotal() {
		return minitueTotal;
	}

	public String getSecondCurrent() {
		return secondCurrent;
	}

	public String getMinitueCurrent() {
		return minitueCurrent;
	}
}
