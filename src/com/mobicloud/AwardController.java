package com.mobicloud;

import com.jfinal.core.Controller;

public class AwardController  extends Controller{

	public void index(){
		//render("index.html");
		render("hello");
	}
	
	//1等奖抽奖页面
	public void prise1(){
		render("/index1.html");
	}
	//2等奖抽奖页面
	public void prise2(){
		render("/index2.html");
	}
	//3等奖抽奖页面
	public void prise3(){
		render("/index3.html");
	}
	
	//清理抽奖计数，重新抽
    public void reset(){
    	try {
			int n1=getParaToInt(0);
			int n2=getParaToInt(1);
			String rt=AwardConfigManager.getInstance().resetChoujiangNum(n1, n2);
			renderJson(rt); 
		} catch (Exception e) {
			renderText("PARAM_INVALID");
		}
    }
    
    //拒绝用户测试性抽奖导致计数器增加，等待局方宣布开始才能将此值置为true，请先确认loadconfig()已经成功加载完毕配置文件
    public void gogogo(){
    	String cmd=getPara("cmd");
    	if("1".equals(cmd)){
    		AwardConfigManager.getInstance().setBeginFlag(true);
    	}else if("0".equals(cmd)){
    		AwardConfigManager.getInstance().setBeginFlag(false);
    	}
    	
    }
    
    //加载1,2,3等奖的中奖率配置文件，返回每个奖项读取后的结果集数量
    public void loadconfig(){
    	int n1=AwardConfigManager.getInstance().loadAwards(1);
    	int n2=AwardConfigManager.getInstance().loadAwards(2);
    	int n3=AwardConfigManager.getInstance().loadAwards(3);
    	String ret=""+n1+","+n2+","+n3;
    	renderJson("{\"ret\":\""+ret+"\"}");
    }
    
    //调用次函数进行抽奖
	public void showlucky(){
		String pid=getPara("p");//不同抽奖页面传入各自的奖项ID
		try {
			int priseId=3;//默认为抽3等奖
			try{
				priseId=Integer.parseInt(pid);
			}catch(Exception e){				
			}			
			Object obj[] = AwardConfigManager.getInstance().choujiang(priseId);
			renderJson("{\"success\":\"true\",\"angle\":\""+obj[0]+"\",\"prize\":\""+obj[1]+"\",\"msg\":\""+obj[2]+"\"}");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
