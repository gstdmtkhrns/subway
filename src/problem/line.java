package problem;

import java.util.ArrayList;

public class line {
	int lineid;//������id
	String linename;//��������
	ArrayList<Station> linestation = new ArrayList<Station>();//�����߸�վ
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
	}//����վ����
}
