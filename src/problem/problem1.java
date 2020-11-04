package problem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.*;

public class problem1 {
	static ArrayList<result> results = new ArrayList<result>();
	static ArrayList<line> lines = new ArrayList<line>();
	static ArrayList<Station> stations = new ArrayList<Station>();
	static Station startstation = new Station();
	static Station endstation = new Station();
	
	//����data��txt
	//����洢����վ����Ϣ����
	//�洢����·��,����վ��,����վ�������վ��
	public static void readdata() {
		File file=new File("data.txt"); 
		if(file.isFile() && file.exists()){   
			InputStreamReader read = null;
			try {
				read = new InputStreamReader(new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String line = "";
				int id = 0;
				int flag = 0;
				int flagnull = 0;
				Station beforestation = new Station();
				while((line = bufferedReader.readLine()) != null){
					line newline = new line();
					newline.setLineid(id);
					id++;
					String[] str = line.split(" ");
					newline.linename = str[0];
					for(int i = 1;i<str.length;i++) {
						String tempname = str[i];
						for(int j = 0;j<stations.size();j++) {
							if(stations.get(j).getStationname().equals(tempname)) {
								newline.AddStation(stations.get(j));
								flag = 1;
								if(flagnull != 0) {
									stations.get(j).nextstation.add(beforestation);
									stations.get(j).nextstationlineid.add(newline.getLineid());
									beforestation.nextstation.add(stations.get(j));
									beforestation.nextstationlineid.add(newline.getLineid());
								}
								beforestation = stations.get(j);
								flagnull++;
							}
						}
						if(flag == 0) {
							Station newstation = new Station();
							newstation.id = stations.size();
							newstation.setStationname(tempname);
							stations.add(newstation);
							newline.AddStation(newstation);
							if(flagnull !=0) {
								newstation.nextstation.add(beforestation);
								newstation.nextstationlineid.add(newline.getLineid());
								beforestation.nextstation.add(newstation);
								beforestation.nextstationlineid.add(newline.getLineid());
							}
							beforestation = newstation;
							flagnull++;
						}
						flag = 0;
					}
					for(int i = 1;i<newline.linestation.size();i++) {
						newline.linestation.get(i).lineset.add(newline);
					}
					lines.add(newline);
					flagnull = 0;
				}
				read.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
         }else{  
             System.out.println("�Ҳ���ָ�����ļ�");  
         } 
	}
	
	
	//��Ҫ����ʵ��
	public static void deal() {
		result tempresult = new result();
		results.add(tempresult);
		endstation.setDistance(0);//�յ�վ�����趨Ϊ0
		recursion(endstation,1);
		recursion1(tempresult,startstation,endstation);
	}
	
	//�ݹ�������յ�վ����
	public static void recursion(Station tempstation,int distance) {
		for(int i = 0;i<tempstation.nextstation.size();i++) {
			if(tempstation.nextstation.get(i).getDistance() > distance) {
				tempstation.nextstation.get(i).setDistance(distance);
				int tempdistance = distance + 1;
				recursion(tempstation.nextstation.get(i),tempdistance);
			}
		}
	}
	
	//�ݹ���Ѱ���վ����������
	public static void recursion1(result result,Station tempstation1,Station tempstation2) {
		int min = tempstation1.nextstation.get(0).getDistance();
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		for(int i = 0;i<tempstation1.nextstation.size();i++) {
			//���վ���յ�վΪ����վʱֱ���˳�
			if(tempstation1.nextstation.get(i).getDistance() == 0) {
				result.sts.add(tempstation1.nextstation.get(i));
				result.list.add(tempstation1.nextstationlineid.get(i));
				return ;
			}
			//��Ѱ�������ֵ
			if(tempstation1.nextstation.get(i).getDistance() < min) {
				min = tempstation1.nextstation.get(i).getDistance();
			}
		}
		//��ֹ�����·��ֹһ��
		for(int i = 0;i<tempstation1.nextstation.size();i++) {
			if(tempstation1.nextstation.get(i).getDistance() == min) {
				index.add(i);
			}
		}
		//�����·��һ�����
		if(index.size() == 1) {
			result.sts.add(tempstation1.nextstation.get(index.get(0)));
			result.list.add(tempstation1.nextstationlineid.get(index.get(0)));
			recursion1(result,tempstation1.nextstation.get(index.get(0)),tempstation2);
		}
		//�����·��ֹһ�����
		else if(index.size() > 1) {
			for(int i = 1;i<index.size();i++) {
				result newresult = new result();
				newresult.sts.addAll(result.sts);
				newresult.list.addAll(result.list);
				newresult.sts.add(tempstation1.nextstation.get(index.get(i)));
				newresult.list.add(tempstation1.nextstationlineid.get(index.get(i)));
				results.add(newresult);
				recursion1(newresult,tempstation1.nextstation.get(index.get(i)),tempstation2);
			}
			result.sts.add(tempstation1.nextstation.get(index.get(0)));
			result.list.add(tempstation1.nextstationlineid.get(index.get(0)));
			recursion1(result,tempstation1.nextstation.get(index.get(0)),tempstation2);
		}
	}
	
	//����������
	public static void output() {
		System.out.println(results.size() + "����·�ɹ�ѡ��");
		for(int i = 0;i<results.size();i++) {
			int lineid = results.get(i).list.get(0);
			System.out.print(lines.get(lineid).getLinename());
			System.out.print(" " + startstation.getStationname());
			for(int j = 0;j<results.get(i).sts.size();j++) {
				//���軻��,�����ʾ"ת--��"
				if(results.get(i).list.get(j) != lineid) {
					lineid = results.get(i).list.get(j);
					System.out.print(" ת" + lines.get(lineid).getLinename());
				}
				System.out.print(" " + results.get(i).sts.get(j).getStationname());
			}
			System.out.println();
		}
	}
	
	//�����������
	public static void main(String[] args) {
		readdata();
		System.out.println("����������ʼվ����վ����");
		Scanner input = new Scanner(System.in);
		String ststation = "";
		ststation = input.nextLine();
		System.out.println("���������յ�վ����վ����");
		String enstation = "";
		enstation = input.nextLine();
		
		//��������������
		int flag1 = 0;
		int flag2 = 0;
		for(int i = 0;i<lines.size();i++) {
			int size = lines.get(i).linestation.size();
			for(int j = 0;j<size;j++) {
				String sname = lines.get(i).linestation.get(j).stationname;
				if(sname.equals(ststation)) {
					startstation = lines.get(i).linestation.get(j);
					flag1 = 1;
				}
				if(sname.equals(enstation)) {
					endstation = lines.get(i).linestation.get(j);
					flag2 = 1;
				}
			}
		}
		if(flag1 == 0&&flag2 == 1) {
			System.out.println("��㲻����");
		}
		else if(flag2 == 0 && flag1 == 1) {
			System.out.println("�յ㲻����");
		}
		else if(flag1 == 0 && flag2 == 0) {
			System.out.println("�����յ��������");
		}
		else {
			if(ststation.equals(enstation)) {
				System.out.println("����յ���ͬ����ȷ�Ϻ��ѯ");
			}
			else {
				deal();
				output();
			}
		}
	}
}
