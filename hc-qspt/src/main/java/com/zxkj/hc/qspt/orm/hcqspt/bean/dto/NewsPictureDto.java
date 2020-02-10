package com.zxkj.hc.qspt.orm.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.NewsPictureEntity;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
/**
 * 
 * @author: hfl 
 */
public class NewsPictureDto extends BaseDto<NewsPictureEntity>{

	private static  final long serialVersionUID = 1L;

	public NewsPictureDto(){
	}

	public static final String TABLE_NAME= "news_picture";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String NEWS_ID_D = "news_id";
	public static final String NEWS_ID_J = "newsId";
	public static final Column NEWS_ID_C= new Column(NEWS_ID_D);

	public static final String NAME= "name";
	public static final Column NAME_C= new Column(NAME);

	public static final String SIZE= "size";
	public static final Column SIZE_C= new Column(SIZE);

	public static final String URL= "url";
	public static final Column URL_C= new Column(URL);

	public static final String PICTURE_CONTENT_D = "picture_content";
	public static final String PICTURE_CONTENT_J = "pictureContent";
	public static final Column PICTURE_CONTENT_C= new Column(PICTURE_CONTENT_D);



	public String getId(){
		return po.getId();
	}

	public void setId(String id){
		 po.setId(id);
	}

	public String getNewsId(){
		return po.getNewsId();
	}

	public void setNewsId(String newsId){
		 po.setNewsId(newsId);
	}

	public String getName(){
		return po.getName();
	}

	public void setName(String name){
		 po.setName(name);
	}

	public String getSize(){
		return po.getSize();
	}

	public void setSize(String size){
		 po.setSize(size);
	}

	public String getUrl(){
		return po.getUrl();
	}

	public void setUrl(String url){
		 po.setUrl(url);
	}

	public byte[] getPictureContent(){
		return po.getPictureContent();
	}

	public void setPictureContent(byte[] pictureContent){
		 po.setPictureContent(pictureContent);
	}

}
