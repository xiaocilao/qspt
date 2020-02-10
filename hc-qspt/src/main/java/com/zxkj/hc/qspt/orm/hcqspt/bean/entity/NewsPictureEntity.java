package com.zxkj.hc.qspt.orm.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.mapper.NewsPictureMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.NewsPictureDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="news_picture")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class NewsPictureEntity extends EntityBean<NewsPictureEntity,NewsPictureDto,NewsPictureMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@Column(name="news_id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="新闻id")
	private String newsId;

	@Column(name="name",length=50,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="名称")
	private String name;

	@Column(name="size",length=15,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="图片大小")
	private String size;

	@Column(name="url",length=100,columnDefinition="varchar")
	@SetuDescription(describe="图片路径")
	private String url;

	@Column(name="picture_content",length=0,columnDefinition="blob")
	@SetuDescription(describe="图片")
	@Basic(fetch=FetchType.LAZY)
	private transient  byte[] pictureContent;

	public NewsPictureEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setNewsId(String newsId){
		this.newsId=newsId;
	}

	public String getNewsId(){
		return this.newsId;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return this.name;
	}

	public void setSize(String size){
		this.size=size;
	}

	public String getSize(){
		return this.size;
	}

	public void setUrl(String url){
		this.url=url;
	}

	public String getUrl(){
		return this.url;
	}

	public void setPictureContent(byte[] pictureContent){
		this.pictureContent=pictureContent;
	}

	public byte[] getPictureContent(){
		return this.pictureContent;
	}

}
