package com.zxkj.hc.qspt.orm.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.mapper.NewsColumnMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.NewsColumnDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="news_column")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class NewsColumnEntity extends EntityBean<NewsColumnEntity,NewsColumnDto,NewsColumnMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@Column(name="column_name",length=50,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String columnName;

	public NewsColumnEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setColumnName(String columnName){
		this.columnName=columnName;
	}

	public String getColumnName(){
		return this.columnName;
	}

}
