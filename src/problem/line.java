package problem;

import java.util.ArrayList;

public class line {
	int lineid;//地铁线id
	String linename;//地铁线名
	ArrayList<Station> linestation = new ArrayList<Station>();//地铁线各站
	public int getLineid() {
		return lineid;
	}
	public void setLineid(int lineid) {
		this.lineid = lineid;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public ArrayList<Station> getLinestation() {
		return linestation;
	}
	public void setLinestation(ArrayList<Station> linestation) {
		this.linestation = linestation;
	}
	public void AddStation(Station station) {
		linestation.add(station);
	}//加入站点名
}
