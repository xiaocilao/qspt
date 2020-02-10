import java.sql.SQLException;

import org.setu.framework.constant.SetuEnvConstant;
import org.setu.framework.jdbc.develop.util.CodeGenerator;
import org.setu.framework.jdbc.enums.DbDialect;
import org.setu.framework.jdbc.handler.BaseDbConnResolve;

import com.zxkj.hc.qspt.setu.system.DbConnStructure;

public class CodeUtil {
	public static void main(String[] args) throws SQLException{
		//generate("表名") 修改了表结构或者新加入表的时候需要运行
//		generate("wxgzh_user");
//		generate("yinhangka");
//		generate("youjixinxi");
		generate("shuidan");
//		generate("apply_flow");
//		generate("apply_record");
//		generate("apply_user");
//		generate("uploadTable");
//		generate("flow_info");
//		generate("flow_data");
//		generate("flow_file");
//		generate("resource");
//		generate("role");
//		generate("role_res");
//		generate("role_user");
//		generate("user");
//		generate("role");
//		generate("news");
//		generate("news_column");
//		generate("news_picture");
	}
	public static void generate(String tableName) throws SQLException{
		CodeGenerator codeGenerator=new CodeGenerator(
				"D:\\work\\hc-qspt",false
				,"10.110.110.13","32000","root","zxkj@2018"
				,DbConnStructure.DC$HCQSPT, DbConnStructure.hcqspt,DbConnStructure.class,DbDialect.MySQLDialect,BaseDbConnResolve.class,SetuEnvConstant.SETU_CREATOR);
		codeGenerator.init(false, "com.zxkj.hc.qspt.orm",tableName, null,true);
	    codeGenerator.check();
		codeGenerator.printPara();
		codeGenerator.createDescribe();
		codeGenerator.createPo();
	}
}