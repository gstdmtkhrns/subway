package problem;

import java.util.ArrayList;

public class Station {
	int id;
	String stationname;//站点名
	ArrayList<line> lineset = new ArrayList<line>();//站点所在地铁线
	ArrayList<Station> nextstation = new ArrayList<Station>(); //站点前后站点
	ArrayList<Integer> nextstationlineid = new ArrayList<Integer>();//前后站点所属地铁线
	int distance = 100000;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getStationname() {
		return stationname;
	}
	public void setStationname(String stationname) {
		this.stationname = stationname;
	}
	public ArrayList<line> getLineset() {
		return lineset;
	}
	public void setLineset(ArrayList<line> lineset) {
		this.lineset = lineset;
	}
	public ArrayList<Station> getNextstation() {
		return nextstation;
	}
	public void setNextstation(ArrayList<Station> nextstation) {
		this.nextstation = nextstation;
	}
}
