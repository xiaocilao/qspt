package com.zxkj.hc.qspt.cms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.NewsColumnEntity;

@RestController
@RequestMapping("/api/newsColumn")
public class NewsColumnController {
	@PostMapping("/addColumn")
	public String addColumn(String columnName) {
		NewsColumnEntity column = new NewsColumnEntity();
		column.setColumnName(columnName);
		return column.insertSelf();
	}
	@GetMapping("/getColumn")
	public List<NewsColumnEntity> getColumn() {
		NewsColumnEntity column = new NewsColumnEntity();
		return column.queryListBySelf();
	}
	@PostMapping("/deleteColumn")
	public String deleteColumn(String id) {
		NewsColumnEntity column = new NewsColumnEntity();
		column.setId(id);
		if(1==column.deleteBySelfId()) {
			return id;
		}
		return null;
	}
}
