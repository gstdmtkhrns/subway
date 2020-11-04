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
	
	//读入data。txt
	//处理存储地铁站点信息内容
	//存储地铁路线,地铁站点,地铁站点的相邻站点
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
             System.out.println("找不到指定的文件");  
         } 
	}
	
	
	//主要函数实现
	public static void deal() {
		result tempresult = new result();
		results.add(tempresult);
		endstation.setDistance(0);//终点站距离设定为0
		recursion(endstation,1);
		recursion1(tempresult,startstation,endstation);
	}
	
	//递归计算离终点站距离
	public static void recursion(Station tempstation,int distance) {
		for(int i = 0;i<tempstation.nextstation.size();i++) {
			if(tempstation.nextstation.get(i).getDistance() > distance) {
				tempstation.nextstation.get(i).setDistance(distance);
				int tempdistance = distance + 1;
				recursion(tempstation.nextstation.get(i),tempdistance);
			}
		}
	}
	
	//递归找寻起点站起的最近距离
	public static void recursion1(result result,Station tempstation1,Station tempstation2) {
		int min = tempstation1.nextstation.get(0).getDistance();
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		for(int i = 0;i<tempstation1.nextstation.size();i++) {
			//起点站与终点站为相邻站时直接退出
			if(tempstation1.nextstation.get(i).getDistance() == 0) {
				result.sts.add(tempstation1.nextstation.get(i));
				result.list.add(tempstation1.nextstationlineid.get(i));
				return ;
			}
			//找寻最近距离值
			if(tempstation1.nextstation.get(i).getDistance() < min) {
				min = tempstation1.nextstation.get(i).getDistance();
			}
		}
		//防止最佳线路不止一条
		for(int i = 0;i<tempstation1.nextstation.size();i++) {
			if(tempstation1.nextstation.get(i).getDistance() == min) {
				index.add(i);
			}
		}
		//最佳线路仅一条情况
		if(index.size() == 1) {
			result.sts.add(tempstation1.nextstation.get(index.get(0)));
			result.list.add(tempstation1.nextstationlineid.get(index.get(0)));
			recursion1(result,tempstation1.nextstation.get(index.get(0)),tempstation2);
		}
		//最佳线路不止一条情况
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
	
	//返回输出结果
	public static void output() {
		System.out.println(results.size() + "条线路可供选择：");
		for(int i = 0;i<results.size();i++) {
			int lineid = results.get(i).list.get(0);
			System.out.print(lines.get(lineid).getLinename());
			System.out.print(" " + startstation.getStationname());
			for(int j = 0;j<results.get(i).sts.size();j++) {
				//如需换乘,输出提示"转--线"
				if(results.get(i).list.get(j) != lineid) {
					lineid = results.get(i).list.get(j);
					System.out.print(" 转" + lines.get(lineid).getLinename());
				}
				System.out.print(" " + results.get(i).sts.get(j).getStationname());
			}
			System.out.println();
		}
	}
	
	//读入测试内容
	public static void main(String[] args) {
		readdata();
		System.out.println("输入任意起始站地铁站名：");
		Scanner input = new Scanner(System.in);
		String ststation = "";
		ststation = input.nextLine();
		System.out.println("输入任意终点站地铁站名：");
		String enstation = "";
		enstation = input.nextLine();
		
		//输入错误情况处理
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
			System.out.println("起点不存在");
		}
		else if(flag2 == 0 && flag1 == 1) {
			System.out.println("终点不存在");
		}
		else if(flag1 == 0 && flag2 == 0) {
			System.out.println("起点和终点均不存在");
		}
		else {
			if(ststation.equals(enstation)) {
				System.out.println("起点终点相同，请确认后查询");
			}
			else {
				deal();
				output();
			}
		}
	}
}
