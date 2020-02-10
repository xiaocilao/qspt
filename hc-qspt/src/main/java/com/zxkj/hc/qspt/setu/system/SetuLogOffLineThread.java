package com.zxkj.hc.qspt.setu.system;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.setu.framework.authorization.bean.impl.SetuResource;
import org.setu.framework.core.log.SetuLog;
import org.setu.framework.core.properties.SetuSystemUtil;
import org.setu.framework.jdbc.datasource.SetuConnection;
import org.setu.framework.jdbc.datasource.druid.filter.SetuDruidSqlLogBean;
import org.setu.framework.log.SetuLogMonitor;
import org.setu.framework.monitor.bean.MonitorElement;
import org.setu.framework.monitor.thread.MonitorThreadLocalHandler;
import org.setu.framework.orm.thread.OrmThreadLocalHandler;
import org.setu.framework.session.thread.SessionThreadLocalHandler;
import org.setu.framework.session.util.SetuSessionUtil;
import org.setu.framework.thread.SetuThreadLocalHandler;
import org.setu.framework.thread.offline.task.AbstractSetuOffLineTaskExecutor;
import org.setu.framework.utils.JsonHelper;
import org.setu.monitor.action.SetuActionMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Component
public  class SetuLogOffLineThread extends AbstractSetuOffLineTaskExecutor{
	SetuSession setuSession;
	Map<String, String[]> requestParams;
	Map<String, String> params;
	String action;
	Object result;
	long startTime;
	long endTime;
	Exception exception;
	Stack<SetuDruidSqlLogBean> sqlBeans;
	Stack<MonitorElement> monitorElements;
	Method method;
	List<SetuLog> logList;
	SetuResource setuResource;
	List<SetuConnection> setuConnections;
	protected transient static final Logger logger = LoggerFactory.getLogger(SetuLogOffLineThread.class.getName());

	@Override
	public void run() {
	
		SetuSessionUtil.clearSessionKey(setuSession);
		if(isInitialized&&SetuSystemUtil.logHandler){
			List<String> sqls=new ArrayList<>();
			List<String> wheres=new ArrayList<>();
			if(sqlBeans!=null){
				for(SetuDruidSqlLogBean sqlBean:sqlBeans){
					try{
						String sql=sqlBean.dealSQLBean();
						sqls.add(sql);
						if(sql.contains("where")){
							wheres.add(sql.split("where")[1]);
						}else if(sql.contains("WHERE")){
							wheres.add(sql.split("WHERE")[1]);
						}
					}catch(Exception e){
					}
				}
			}
			StringBuffer buffer=new StringBuffer();
			buffer.append("DEBUGER(").append(setuSession.requestKey).append(") :").append(SetuSystemUtil.applicationName).append("-> ").append(action).append("\n");
			if(setuSession!=null&&setuSession.sessionContenxt!=null){
				buffer.append("\tisRpc:").append(setuSession.sessionContenxt.isRpc)
				.append("\tip:").append(setuSession.sessionContenxt.remoteIp)
				.append("\tcallSource:").append(setuSession.sessionContenxt.callSource);
				if(setuSession.sessionContenxt.tokenId!=null){
					buffer.append("\ttokenId:").append(setuSession.sessionContenxt.tokenId);
				}
				buffer.append("\n");
			}
			if(requestParams!=null){
				for(String key:requestParams.keySet()){
					
					buffer.append("\t参数【REQ】：").append(key).append(" 值：");
					for(String str:requestParams.get(key)){
						buffer.append(str).append(" ");
					}
					buffer.append("\n");
				}
			}else{
				for(String key:params.keySet()){
					buffer.append("\t参数：").append(key).append(" 值：").append(params.get(key)).append("\n");
				}
			}
			if(setuConnections!=null&&setuConnections.size()>0){
				buffer.append("\tDataSource(").append(setuConnections.size()).append(")");
				for(SetuConnection connection:setuConnections){
					if(connection.isErrorClose||!connection.isClosed){
						buffer.append("\t\turl：").append(connection.declareStr);
						buffer.append("\t\t异常关闭：").append(connection.isErrorClose||!connection.isClosed);
						if(connection.isErrorCommit){
							buffer.append("\t\t异常提交：").append(connection.isErrorCommit);
						}
					}
				}
				buffer.append("\n");
			}
			if(sqls.size()>0){
				Long endTime=null;
				buffer.append("\tSQL\n");
				for(SetuDruidSqlLogBean sqlBean:sqlBeans){
					String sql=sqlBean.dealSQLBean();
					buffer.append("\t");
					buffer.append("\t用时：").append(sqlBean.endTime-sqlBean.startTime).append("ms");
					if(endTime!=null){
						buffer.append("\t间隔用时：").append(sqlBean.startTime-endTime).append("ms");
					}
					if(sqlBean.lines!=null){
						buffer.append("\t影响行数：【");
						for(int i:sqlBean.lines){
							buffer.append(i).append(",");
						}
						buffer.setLength(buffer.length()-1);
						buffer.append("】");
					}
					if(sqlBean.setuTransactionBean!=null){
						buffer.append("\t事务Id：").append(sqlBean.setuTransactionBean.transactionId)
							  .append("\t是否读事务：").append(sqlBean.setuTransactionBean.isAutoCommit)
							  .append("\t事务提交：").append(sqlBean.setuTransactionBean.physical)
							  .append("\t事务层级：").append(sqlBean.nestingFlag)
							  .append("\t事务类型：").append(sqlBean.setuTransactionBean.transactionDefinition);
					}
					if(sqlBean.throwable!=null){
						buffer.append("\n\t\t\t错误信息：");
						buffer.append(sqlBean.throwable.getLocalizedMessage().replace("\n", ""));
					}
					endTime=sqlBean.endTime;
					buffer.append("\n\t\t\t").append(sql).append("\n");
				}
			}
			for(MonitorElement ele:monitorElements){
				buffer.append("\tClientKey:"+setuSession.getClientKey()+" RequestKey:"+ele.getRequestKey()+"  Type:"+ele.getType()+"  config:"+ele.getConfig()+"  耗时 "+(ele.getEndTime()-ele.getBeginTime())).append("\n");
				if(ele.getException()!=null){
					StringWriter sw = new StringWriter();
			        PrintWriter pw = new PrintWriter(sw);
			        if(ele.getException() instanceof InvocationTargetException){
						InvocationTargetException targetException=(InvocationTargetException) ele.getException();
						targetException.getTargetException().printStackTrace(pw);
					}else{
						ele.getException().printStackTrace(pw);
					}
					buffer.append("\t\tERROR:"+sw.toString().replaceAll("\n", "\t")).append("\n");
				}
			}
			buffer.append("\tClientKey:"+setuSession.getClientKey()+" RequestKey:"+setuSession.getRequestKey()+"  Type:WEB  config:"+action+"  耗时 "+(endTime-startTime)+"\n");
			
			if(exception!=null){
				StringWriter sw = new StringWriter();
		        PrintWriter pw = new PrintWriter(sw);
				if(exception instanceof InvocationTargetException){
					InvocationTargetException targetException=(InvocationTargetException) exception;
					targetException.getTargetException().printStackTrace(pw);
				}else{
					exception.printStackTrace(pw);
				}
				buffer.append(sw.toString());
			}
			if(logger.isWarnEnabled()&&(action.contains("dubbo:")||action.startsWith("/"))){
				System.out.println(buffer.toString());
			}else if(logger.isDebugEnabled()||logger.isInfoEnabled()){
				System.out.println(buffer.toString());
			}
			try{
				if(setuSession.getClientKey()!=null){
					SetuSessionUtil.debugPush(setuSession, buffer.toString());
				}
			}catch(Exception e){
			}
			//记录指定日志
			if(logList.size()>0){
				for(SetuLog log:logList){
					SetuLogMonitor.put(log);
				}
			}
			
		}
	}
	@Override
	public void execute(String action,Object result, long startTime, long endTime, Exception exception) {
		this.action=action;
		this.startTime=startTime;
		this.endTime=endTime;
		this.exception=exception;
		this.setuConnections=OrmThreadLocalHandler.connectionLocal.get();
		if(exception==null){
			this.exception=SetuThreadLocalHandler.EXCEPTION.get();	
		}
		this.sqlBeans=OrmThreadLocalHandler.threadAnalysisExecuteSqls.get();
		long threadId=Thread.currentThread().getId();
		String monitorAction=SetuActionMonitor.actionLocal.get();
		if(monitorAction!=null){
			SetuActionMonitor.decreaseCounter(monitorAction, exception, sqlBeans.size(), setuConnections.size(), threadId, endTime-startTime);
		}
		requestParams=SetuThreadLocalHandler.getRequestParams();
		params=SetuThreadLocalHandler.getParams();
		setuSession=(SetuSession) SessionThreadLocalHandler.localSession.get();
		setuSession=(SetuSession) SessionThreadLocalHandler.localSession.get();
		this.result=result;
		this.isInitialized=true;
		this.logList=SetuThreadLocalHandler.SETU_LOG_LIST.get();
		this.monitorElements=MonitorThreadLocalHandler.THREAD_MONITOR_ELEMENTS.get();
	}
	
	public static void main(String[] args) {
//		Map<String,Object> param=new HashMap<String, Object>();
//		Map<String,String> value=new HashMap<>();
//		value.put("text", "234234");
//		param.put("name", "324234");
//		param.put("json", "{\"str\":\"23432\"}");
//		param.put("value",value );
//		
//		
//		
//		
//		EvaluationContext context = new StandardEvaluationContext();  // 表达式的上下文,
//		context.setVariable("param", param);  
//		
//		String json="[{'condition':'#param[\\\"value\\\"][\\\"text\\\"]==\\\"234234\\\"','type':'1','name':'新增'}]";
//		
//		System.out.println(json=json.replaceAll("'", "\""));
//		Object obj=JsonUtil.JsonNodeToMap(json);
//		if(obj instanceof List){
//			List<Map<String,String>> list=(List<Map<String, String>>) obj;
//			for(Map<String,String> el:list){
//				ExpressionParser parser = new SpelExpressionParser();
//				if(parser.parseExpression(el.get("condition")).getValue(context, Boolean.class)){
//					System.out.println(el.get("name"));
//				}
//			}
//		}else{
//			Map<String,String> el=(Map<String, String>) obj;
//			ExpressionParser parser = new SpelExpressionParser();
//			if(parser.parseExpression(el.get("condition")).getValue(context, Boolean.class)){
//				System.out.println(el.get("name"));
//			}
//		}
		
		String jsonStr="{\"str\":\"23432\",\"obj\":{\"text\":\"sdfdsf\"}}";
		EvaluationContext context = new StandardEvaluationContext();  // 表达式的上下文,
		context.setVariable("json", JsonHelper.JsonNodeToMap(jsonStr));  
		ExpressionParser parser = new SpelExpressionParser();
		System.out.println(parser.parseExpression("#json[\"obj\"][\"text\"]").getValue(context, String.class));
	}
}
