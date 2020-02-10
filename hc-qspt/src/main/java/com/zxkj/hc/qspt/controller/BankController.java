package com.zxkj.hc.qspt.controller;

import java.sql.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zxkj.hc.qspt.service.BankService;
import com.zxkj.hc.qspt.setu.system.SetuResult;

@RestController()
@RequestMapping("/api/bank")
public class BankController {
	@Resource
	BankService bankService;
	
	@RequestMapping("/getRyxxb")
	@ResponseBody
	public SetuResult getBank(String time) throws Exception {
		return bankService.getBank(time);
	}
}
