package problem;

import java.util.ArrayList;

public class Station {
	int id;
	String stationname;//վ����
	ArrayList<line> lineset = new ArrayList<line>();//վ�����ڵ�����
	ArrayList<Station> nextstation = new ArrayList<Station>(); //վ��ǰ��վ��
	ArrayList<Integer> nextstationlineid = new ArrayList<Integer>();//ǰ��վ������������
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
