package com.mobicloud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AwardConfigManager {
	private static List<Award> awards3=new ArrayList<Award>();
	private static List<Award> awards2=new ArrayList<Award>();
	private static List<Award> awards1=new ArrayList<Award>();
	private static boolean[] hasLoadConfig={false,false,false};
	private static String[] propfile={"award1.txt","award2.txt","award3.txt"};
	private static int[] priseNum={1,2,3};//一等奖 二等奖 三等奖奖品个数
	private static int[] prisedUser={0,0,0};//被抽中一等奖，二等奖，三等奖的个数
	private static int[] choujiangNum={0,0,0};//一等奖二等奖三等奖目前被点击的次数
	private static Object[] ret={317,12,"谢谢参与"};//默认抽奖结果
	private static Object[] wait={317,12,"亲,请耐心等待活动开始"};//默认抽奖结果
	private static boolean canBegin=false;
	private AwardConfigManager(){
		
	}	
	public void setBeginFlag(boolean flag){
		canBegin=flag;
	}
	public String resetChoujiangNum(int n1,int n2){
		choujiangNum[n1-1]=n2;
		prisedUser[n1-1]=n2;
		return "{\"choujiangNum\":"+choujiangNum[n1-1]+",\"user\":"+prisedUser[n1-1]+"}";
	}
	public void setPath(String  filepath){
		String[] files=filepath.split(",");
		for(int i=0;i<3;i++){
			propfile[i]=files[i];
		}
		
		System.out.println("load config file:"+filepath);
	}
	public void setNumber(int p1,int p2,int p3){
		priseNum[0]=p1;
		priseNum[1]=p2;
		priseNum[2]=p3;
	}
	private static class AwardManagerBuilder{		
		private static AwardConfigManager _instance=new AwardConfigManager();		
	}
	
	public static AwardConfigManager getInstance(){
		return AwardManagerBuilder._instance;
	}
	
	public void reload(){
		for(int i=0;i<hasLoadConfig.length;i++){
			hasLoadConfig[i]=false;
		}		
	}
	
	
	public List<Award> getAwards(int i){
		if(!hasLoadConfig[i-1]) loadAwards(i);
		return getAwardsList(i);
	}
	
	private void clear(int i){
		getAwardsList(i).clear();
	}
	private List<Award> getAwardsList(int i){
		if(i==1){
			return awards1;
		}else if(i==2){
			return awards2;
		}else if(i==3){
			return awards3;
		}
		return awards3;
	}
	public int loadAwards(int ii){
		File f=new File(propfile[ii-1]);
		//System.out.println(f.getAbsolutePath());
		BufferedReader ir=null;
		if(f.isFile()){
			clear(ii);
			try {
				FileInputStream fis=new FileInputStream(f);
				ir=new BufferedReader(new InputStreamReader(fis));
				String tmp;
				int i=0;
				while((tmp=ir.readLine())!=null){
					i++;
					String[] line=tmp.split(",");
					//if(line.length<4) continue;
					Award award=new Award();
					award.setId(i);
					award.setFromAngle(Integer.parseInt(line[0]));
					award.setToAngle(Integer.parseInt(line[1]));
					award.setPrise(line[2]);
					award.setVote(Integer.parseInt(line[3]));
					award.setFlag(Integer.parseInt(line[4]));
					getAwardsList(ii).add(award);
					
				}
				hasLoadConfig[ii-1]=true;
				return getAwardsList(ii).size();
				
			} catch (Exception e) {				
				e.printStackTrace();
			}finally{
				try{
				   if(ir!=null)	ir.close();
				}catch(Exception ee){}
			}
			
		}
		return -1;
	}
    private synchronized void increaseVisit(int i){
    	choujiangNum[i-1] +=1;
    }
    private synchronized  int getChoujingNum(int i){
    	return choujiangNum[i-1];
    }
    private void log(String msg){
    	System.out.println(msg);
    }
    private synchronized Object[] isbingo(int i){
    	Object[] obj={317,12,"谢谢参与"};
    	if(i==3 && choujiangNum[i-1]==10 && prisedUser[i-1]<3){
    		obj[2]="三等奖";
    		obj[0]=350;
    		obj[1]=1;
    		prisedUser[i-1] +=1;
    		log("第1个三等奖产生");
    	}else if(i==3 && choujiangNum[i-1]==101 && prisedUser[i-1]<3){
    		obj[2]="三等奖";
    		obj[0]=350;
    		obj[1]=1;
    		prisedUser[i-1] +=1;
    		log("第2个三等奖产生");
    	}else if(i==3 && choujiangNum[i-1]==193 && prisedUser[i-1]<3){
    		obj[2]="三等奖";
    		obj[0]=350;
    		obj[1]=1;
    		prisedUser[i-1] +=1;
    		log("第3个三等奖产生");
    	}else if(i==2 && choujiangNum[i-1]==48 && prisedUser[i-1]<2){
    		obj[2]="二等奖";
    		obj[0]=350;
    		obj[1]=1;
    		prisedUser[i-1] +=1;
    		log("第1个二等奖产生");
    	}else if(i==2 && choujiangNum[i-1]==133 && prisedUser[i-1]<2){
    		obj[2]="二等奖";
    		obj[0]=350;
    		obj[1]=1;
    		prisedUser[i-1] +=1;
    		log("第2个二等奖产生");
    	}else if(i==1 && choujiangNum[i-1]==133 && prisedUser[i-1]<1){
    		obj[2]="一等奖";
    		obj[0]=350;
    		obj[1]=1;
    		prisedUser[i-1] +=1;
    		log("第1个一等奖产生");
    	}
    	return obj;
    }
	public Object[] choujiang(int awd_seq) {
		if(!canBegin) return wait;
		increaseVisit(awd_seq);
		int arrsize=awards1.size();
		if(awd_seq==2){
			arrsize=awards2.size();
		}else if(awd_seq==3){
			arrsize=awards3.size();
		}
		int currentVisitor=getChoujingNum(awd_seq);
		log("第"+currentVisitor+"用户 正在抽  "+awd_seq+" 等奖...");
		Integer obj[] = new Integer[arrsize];
		for (int i = 0; i < arrsize; i++) {
			obj[i] = (Integer) getAwardsList(awd_seq).get(i).getVote();
		}
		Integer prizeId = getRand(obj); // 根据概率获取奖项id
		int angle = new Random().nextInt(getAwardsList(awd_seq).get(prizeId).getToAngle() - getAwardsList(awd_seq).get(prizeId).getFromAngle()) + getAwardsList(awd_seq).get(prizeId).getFromAngle();
		String msg = getAwardsList(awd_seq).get(prizeId).getPrise();// 提示信息
		if(getAwardsList(awd_seq).get(prizeId).getFlag()==1){
			log("第"+currentVisitor+"用户正常抽到了  "+msg);
			if(prisedUser[awd_seq-1] < priseNum[awd_seq-1]){
				prisedUser[awd_seq-1] +=1;
			}else{	
				log("虽然第"+currentVisitor+"用户正常抽到了  "+msg+", 但是已经满额了，提示他未中奖");
				return ret;
			}
		}else{
			Object[] o=isbingo(awd_seq);
			if((Integer)o[0]==350) {
				log("第"+currentVisitor+"用户被强制抽到了  "+(String)o[2]);
				return o;
			}
			
		}
		return new Object[] { angle, prizeId, msg };
	}
	
	
	private Integer getRand(Integer obj[]){
		Integer result = null;
		try {
			int  sum = 0;//概率数组的总概率精度 
			for(int i=0;i<obj.length;i++){
				sum+=obj[i];
			}
			for(int i=0;i<obj.length;i++){//概率数组循环 
				int randomNum = new Random().nextInt(sum);//随机生成1到sum的整数
				if(randomNum<obj[i]){//中奖
					result = i;
					break;
				}else{
					sum -=obj[i];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
