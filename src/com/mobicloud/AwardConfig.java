package com.mobicloud;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;

import com.jfinal.render.ViewType;

public class AwardConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		loadPropertyFile("config.txt");
		me.setDevMode(false);
		me.setViewType(ViewType.JSP);
		AwardConfigManager.getInstance().setPath(getProperty("filepath"));
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/",IndexController.class);
		me.add("/award", AwardController.class);
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 8000, "/choujiang", 5);
	}
}

