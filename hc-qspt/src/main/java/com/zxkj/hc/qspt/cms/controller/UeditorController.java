package com.zxkj.hc.qspt.cms.controller;

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.hunter.FileManager;
import com.baidu.ueditor.upload.StorageManager;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.NewsDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.NewsPictureDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.NewsEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.NewsPictureEntity;
import org.setu.framework.http.filter.DefaultSetuFilter;
import org.setu.framework.utils.UuidHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/news")
public class UeditorController {
	private transient static final Logger logger = LoggerFactory.getLogger(UeditorController.class);

	@Value("${web.url}")
	private String webUrl;

	@RequestMapping(value = "/exec")
	@ResponseBody
	public String exec(HttpServletRequest request,final String newsId ) throws UnsupportedEncodingException {
		return new ActionEnter(request, "classpath:config.json", new StorageManager() {
			@Override
			public State saveBinaryFile(byte[] data, String name, String suffix, String path, Map<String, Object> conf) {
				try {
					NewsPictureEntity pictureEntity = new NewsPictureEntity();
					pictureEntity.setId(UuidHelper.getUUID());
					pictureEntity.setNewsId(newsId);
					pictureEntity.setName(name+"."+suffix);
					pictureEntity.setSize(String.valueOf(data.length));
					pictureEntity.setPictureContent(data);
					String pictureUrl=webUrl+"/api/news/"+pictureEntity.getId()+".png";
					pictureEntity.setUrl(pictureUrl);
					pictureEntity.createSelf();

					State state = new BaseState(true);
					state.putInfo( "size", data.length );
					state.putInfo( "title", name );
					state.putInfo("url", pictureUrl);
					return state;
				}catch (Exception e){
					logger.error("com.zxkj.hc.qspt.cms.controller",e);
				}
				return new BaseState(false, AppInfo.IO_ERROR);
			}

			@Override
			public State saveFileByInputStream(InputStream is, String name, String suffix, String path, long maxSize, Map<String, Object> conf) {
				try {
					ByteArrayOutputStream bos=new ByteArrayOutputStream();
					byte[] dataBuf = new byte[ 2048 ];
					int len;
					while ((len=is.read(dataBuf))!=-1){
						bos.write(dataBuf,0,len);
					}
					byte[] data=bos.toByteArray();
					bos.close();
					if(maxSize!=-1 && data.length>maxSize){
						return new BaseState(false, AppInfo.MAX_SIZE);
					}
					return saveBinaryFile(data,name,suffix,path,conf);
				} catch (IOException e) {
					logger.error("",e);
				}
				return new BaseState(false, AppInfo.IO_ERROR);
			}

			@Override
			public State saveFileByInputStream(InputStream is, String name, String suffix, String path, Map<String, Object> conf) {
				return saveFileByInputStream(is,name,suffix,path,-1,conf);
			}
		}, new FileManager() {
			@Override
			public State listFile(int index, Map<String, Object> conf) {
				int pageRowSize=(Integer)conf.get("count");

				NewsPictureEntity pictureEntity = new NewsPictureEntity();
				pictureEntity.setNewsId(newsId);
				MultiState state = null;
				int total=pictureEntity.countBySelf();
				if(total==0) {
					return new BaseState( false, AppInfo.NOT_EXIST );
				}
				state = new MultiState( true );
				if ( index >= 0&& index< total ) {
					List<NewsPictureEntity> list=pictureEntity.queryListBySelf(null, index,pageRowSize , NewsPictureDto.URL_C);
					for(int i=0;i<list.size();i++) {
						BaseState fileState = new BaseState( true );
						fileState.putInfo( "url", list.get(i).getUrl() );
						state.addState( fileState );
					}
					index=index+list.size();
				}
				state.putInfo( "start", index);
				state.putInfo( "total", total );
				return state;
			}
		}, null).exec();
	}


	@RequestMapping(value = "/{id}.png")
	@ResponseBody
	public byte[] addPicture(@PathVariable(value="id") String id) {
		NewsPictureEntity pictureEntity = new NewsPictureEntity();
		pictureEntity.setId(id);
		return pictureEntity.queryBlobBySelf(NewsPictureDto.PICTURE_CONTENT_C);
	}
	
	@RequestMapping(value = "/cover/{id}.png")
	@ResponseBody
	public byte[] addPicture2(@PathVariable(value="id") String id) {
		NewsEntity newsEntity = new NewsEntity();
		newsEntity.setId(id);
		return newsEntity.queryBlobBySelf(NewsDto.PICTURE_C);
	}

}