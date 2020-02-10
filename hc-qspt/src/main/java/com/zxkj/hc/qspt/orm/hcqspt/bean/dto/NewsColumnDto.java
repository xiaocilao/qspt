package com.zxkj.hc.qspt.orm.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.NewsColumnEntity;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
/**
 * 
 * @author: hfl 
 */
public class NewsColumnDto extends BaseDto<NewsColumnEntity>{

	private static  final long serialVersionUID = 1L;

	public NewsColumnDto(){
	}

	public static final String TABLE_NAME= "news_column";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String COLUMN_NAME_D = "column_name";
	public static final String COLUMN_NAME_J = "columnName";
	public static final Column COLUMN_NAME_C= new Column(COLUMN_NAME_D);



	public String getId(){
		return po.getId();
	}

	public void setId(String id){
		 po.setId(id);
	}

	public String getColumnName(){
		return po.getColumnName();
	}

	public void setColumnName(String columnName){
		 po.setColumnName(columnName);
	}

}
