package com.zxkj.hc.qspt.cms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.setu.framework.http.filter.DefaultSetuFilter;
import org.setu.framework.jdbc.core.Column;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.setu.framework.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zxkj.hc.qspt.cms.consts.CmsConsts;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.NewsDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.NewsPictureDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.NewsEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.NewsPictureEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.vo.NewSVo;
import com.zxkj.hc.qspt.orm.hcqspt.bean.vo.NewsPictureVo;
import com.zxkj.hc.qspt.setu.system.BeanUtil;
import com.zxkj.hc.qspt.setu.system.SetuResult;
import com.zxkj.hc.qspt.setu.system.SetuSession;

/**
 * 
 * @author 甘晓龙 2018年12月4日
 */
@RestController
@RequestMapping("/api/news")
public class NewController extends SetuSessionController {
	private transient static final Logger logger = LoggerFactory.getLogger(NewController.class);

	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Value("${web.url}")
	private String webUrl;

	/* 新建新闻 */
	@PostMapping("/addNews")
	public String addNews(String title, String id, String tagLabel, String selectValue, String shortTitle,
			String dataSource, String writerName) {
		NewsEntity news = new NewsEntity();
		SetuSession session = (SetuSession) getSetuSession();
		if (id != null && id.length() == 32) {
			if (dataSource != null) {
				news.setDataSource(dataSource);
			}
			if (tagLabel != null) {
				news.setTagLabel(tagLabel);
			}
			news.setId(id);
			news.setTitle(title);
			news.setWriteName(writerName);
			news.setColumns(selectValue);
			news.setShortTitle(shortTitle);			
			int result = news.updateBySelfId();
			return String.valueOf(result);
		}
		if (dataSource != null) {
			news.setDataSource(dataSource);
		}
		if (tagLabel != null) {
			news.setTagLabel(tagLabel);
		}
		news.setWriteName(writerName);
		news.setColumns(selectValue);
		news.setShortTitle(shortTitle);
		news.setTitle(title);
		news.setXtLrsj(date.format(new Date()));
		news.setXtZhxgsj(date.format(new Date()));
		news.setXtLrrid(session.getUserId());
		news.setXtLrrxm(session.getUserName());
		news.setXtZhxgrxm(session.getUserName());
		return news.insertSelf();
	}

	/* 上传内容与修改状态 */
	@PostMapping("/addContent")
	public int addContent(String id, String content, String state) {
		NewsEntity news = new NewsEntity();
		if (state != null && state.equals("1")) {
			news.setId(id);
			news.setContent(content);
			news.setState(state);
			return news.updateBySelfId();
		}
		news.setId(id);
		news.setContent(content);
		return news.updateBySelfId();
	}

	/* 更新新闻信息 */
	@PostMapping("/updateNews")
	public String updateNews(String title, String content, String id) {
		SetuSession session = (SetuSession) getSetuSession();
		NewsEntity news = new NewsEntity();
		if (id != null) {
			news.setId(id);
			news.setTitle(title);
			news.setContent(content);
			news.setXtZhxgsj(date.format(new Date()));
			news.setEditName((session.getUserName()));
			int newId = news.updateBySelfId();
			if (newId == 1) {
				return id;
			}
		}
		return "保存失败";
	}

	/* 上传图片 */
	@PostMapping("/addPicture")
	public String addPicture(@RequestParam("file") MultipartFile picture, String id) {
		NewsPictureEntity newPicture = new NewsPictureEntity();
		try {
			newPicture.setNewsId(id);
			byte[] img = picture.getBytes();
			newPicture.setPictureContent(img);
		} catch (IOException e) {
			return "新闻图片转换错误";
		}
		String pictureId = newPicture.insertSelf();
		newPicture.setId(pictureId);
		newPicture.setUrl(webUrl+"/api/news/"+pictureId+".png");
		return String.valueOf(newPicture.updateBySelfId());
	}
	/* 更新图片 imgState:1,置顶 ,2封面,0删除*/
	@PostMapping("/updatePicture")
	public int updatePicture(String id,String imgState) {
		NewsPictureEntity picture = new NewsPictureEntity();
		NewsEntity news = new NewsEntity();
 		if(imgState.equals("1")) {
 			picture.setId(id);
 			news.setStickPicture(picture.queryBlobBySelf(NewsPictureDto.PICTURE_CONTENT_C));
 			picture.queryBySelfId(NewsPictureDto.NEWS_ID_C);
 			news.setId(picture.getNewsId());
 			return news.updateBySelfId();
		}else if(imgState.equals("2")) {
			picture.setId(id);
 			news.setPicture(picture.queryBlobBySelf(NewsPictureDto.PICTURE_CONTENT_C));
 			news.setStickPicture(picture.queryBlobBySelf(NewsPictureDto.PICTURE_CONTENT_C));
 			picture.queryBySelfId(NewsPictureDto.NEWS_ID_C);
 			news.setId(picture.getNewsId());
 			return news.updateBySelfId();
		}
		else if(imgState.equals("0")) {
			picture.setId(id);
 			return picture.deleteBySelfId();
		}
		return -1;
	}
	/* 更新图片 cropperState:0只保存图片,1置顶 ,2封面,*/
	@PostMapping("/cropperUpPicture")
	public int cropperUpPicture(@RequestParam("file") MultipartFile img,String id,String newsId,String cropperState) {
		NewsPictureEntity picture = new NewsPictureEntity();
		NewsEntity news = new NewsEntity();
		byte[] imgbyte = null;
		picture.setId(id);
		picture.setNewsId(newsId);
		try {
			imgbyte = img.getBytes();
			picture.setPictureContent(imgbyte);
		} catch (IOException e) {
			logger.error("图片转换异常{}",e.getMessage());
		}
 		if(cropperState.equals("0")) {
 			return picture.updateBySelfId();
		}else if(cropperState.equals("1")) {
			picture.updateBySelfId();
			news.setStickPicture(imgbyte);
 			news.setId(newsId);
 			return news.updateBySelfId();
		}
		else if(cropperState.equals("2")) {
			picture.updateBySelfId();
			news.setPicture(imgbyte);
 			news.setId(newsId);
 			return news.updateBySelfId();
		}
		return -1;
	}

	/* 删除图片 */
	@PostMapping("/deletePicture")
	public int deletePicture(String id) {
		NewsEntity news = new NewsEntity();
		news.setId(id);
		byte[] img = new byte[1];
		news.setPicture(img);
		return news.updateBySelfId();
	}

	/* 获取图片 */
	@RequestMapping("/getPicture.png")
	@ResponseBody
	public byte[] getPicture(String id) {
		if (id != null) {
			NewsEntity news = new NewsEntity();
			news.setId(id);
			return news.queryBlobBySelf(NewsDto.PICTURE_C);
		}
		return null;
	}

	/* 删除新闻 */
	@PostMapping("/deleteNews")
	public String deleteNews(String id) {
		if (id != null) {
			NewsEntity news = new NewsEntity();
			news.setId(id);
			BeanUtil.setZxField(news, "用户注销");
			return String.valueOf(news.updateBySelfId());
		}
		return "0";
	}

	/* 获取新闻信息 */
	@PostMapping("/getNews")
	public SetuResult getNews(int count, int pagesize, String title, String filter, String selectValue) {
		NewsEntity news = new NewsEntity();
		List<NewsEntity> listNews;
		SetuResult result = new SetuResult();
		news.setXtZxbz("0");
		if (CommonUtil.isNotNull(filter) && CommonUtil.isNotNull(selectValue) && CommonUtil.isNotNull(title)) {
			if (filter.equals(CmsConsts.FILTER_NINE)) {
				news.setStick(CmsConsts.FILTER_ONE);
			} else {
				news.setState(filter);
			}
			news.addConditonItem("and", new ConditionItem("title", "like", "%" + title + "%"));
			if (!selectValue.equals(CmsConsts.SELECTVALUE_ZERO)) {
				news.setColumns(selectValue);
			}
			listNews = news.queryListBySelf(null, count, pagesize);
			result.setTotal(news.countBySelf());
			result.setRows(listNews);
			return result;
		}
		if (CommonUtil.isNotNull(filter) && CommonUtil.isNotNull(selectValue)) {
			if (filter.equals(CmsConsts.FILTER_NINE)) {
				news.setStick(CmsConsts.STICK);
			} else {
				news.setState(filter);
			}
			news.setColumns(selectValue);
			listNews = news.queryListBySelf(null, count, pagesize);
			result.setTotal(news.countBySelf());
			result.setRows(listNews);
			return result;
		}
		if (CommonUtil.isNotNull(filter) && CommonUtil.isNotNull(title)) {
			if (filter.equals(CmsConsts.FILTER_NINE)) {
				news.setStick(CmsConsts.STICK);
			} else {
				news.setState(filter);
			}
			news.addConditonItem("and", new ConditionItem("title", "like", "%" + title + "%"));
			listNews = news.queryListBySelf(null, count, pagesize);
			result.setTotal(news.countBySelf());
			result.setRows(listNews);
			return result;
		}
		if (CommonUtil.isNotNull(selectValue) && CommonUtil.isNotNull(title)) {
			news.setColumns(selectValue);
			news.addConditonItem("and", new ConditionItem("title", "like", "%" + title + "%"));
			listNews = news.queryListBySelf(null, count, pagesize);
			result.setTotal(news.countBySelf());
			result.setRows(listNews);
			return result;
		}
		if (CommonUtil.isNotNull(filter)) {
			if (filter.equals(CmsConsts.FILTER_NINE)) {
				news.setStick(CmsConsts.STICK);
			} else {
				news.setState(filter);
			}
			listNews = news.queryListBySelf(null, count, pagesize);
			result.setTotal(news.countBySelf());
			result.setRows(listNews);
			return result;
		}
		if (CommonUtil.isNotNull(selectValue)) {
			news.setColumns(selectValue);
			listNews = news.queryListBySelf(null, count, pagesize);
			result.setTotal(news.countBySelf());
			result.setRows(listNews);
			return result;
		}
		if (CommonUtil.isNotNull(title)) {
			news.addConditonItem("and", new ConditionItem("title", "like", "%" + title + "%"));
			listNews = news.queryListBySelf(null, count, pagesize);
		} else {
			listNews = news.queryListBySelf(null, count, pagesize);
		}
		result.setTotal(news.countBySelf());
		result.setRows(listNews);
		return result;
	}

	/* 编辑新闻时，刷新页面根据id重新获取数据 */
	@RequestMapping("/getNewsById")
	public List<NewsEntity> getNewsById(String id) {
		if (id != null) {
			NewsEntity news = new NewsEntity();
			news.setId(id);
			return news.queryListBySelf();
		}
		return null;
	}

	@RequestMapping("/getWxNews")
	public List<NewsEntity> getWxNews(int pagesize, int count) {
		NewsEntity news = new NewsEntity();
		List<NewsEntity> listNews;
		news.setState(CmsConsts.STATE);
		listNews = news.queryListBySelf("updateDate", count, pagesize);
		if (listNews.size() != 0) {
//			List<NewSVo> listVo = new ArrayList<NewSVo>();
//			for (NewsEntity list : listNews) {
//				NewSVo newsvo = new NewSVo();
//				newsvo.setUrl(list.getId());
//				newsvo.setNewsimg("/");
//				newsvo.setNewstitle(list.getTitle());
//				String xtZhxgsj=list.getXtZhxgsj();
//				if(xtZhxgsj!=null) {
//					newsvo.setNewstime(xtZhxgsj.substring(0, 10));
//				}
//				newsvo.setNewsknown("已阅读:" + list.getClickCount());
//				listVo.add(newsvo);
//			}
			return listNews;
		}
		return null;
	}
	//state:0删除，1置顶，2取消置顶
	@PostMapping("/checkboxHandel")
	public String checkboxHandel(String[] listId, String state) {
		NewsEntity news = new NewsEntity();
		if (state.equals("0")) {
			news.addConditonItem(new ConditionItem(NewsDto.ID,"in",listId));
			BeanUtil.setZxField(news, "用户注销");
			news.updateBySelfCondition();
		} else if (state.equals("1")) {
			news.addConditonItem(new ConditionItem(NewsDto.ID,"in",listId));
			news.setStick(CmsConsts.STICK);
			BeanUtil.setXgField(news);
			news.updateBySelfCondition();
		} else if (state.equals("2")) {
			news.addConditonItem(new ConditionItem(NewsDto.ID,"in",listId));
			news.setStick(CmsConsts.NOT_STICK);
			BeanUtil.setXgField(news);
			news.updateBySelfCondition();
		}
		return state;
	}
	//获取新闻图片列表url
	@GetMapping("/getPictureListUrl")
	public List<NewsPictureVo> getPictureListUrl(String newsId) {
		NewsPictureEntity picture = new NewsPictureEntity();
		List<NewsPictureVo> urlList = new ArrayList<NewsPictureVo>();
		picture.setNewsId(newsId);
		List<NewsPictureEntity> pictureList=picture.queryListBySelf();
		if(pictureList.size()>0) {
			for(NewsPictureEntity np :pictureList) {
				NewsPictureVo npv = new NewsPictureVo();
				npv.setId(np.getId());
				npv.setUrl(np.getUrl());
				urlList.add(npv);
			}
			return urlList;
		}
		return null;
	}

}
