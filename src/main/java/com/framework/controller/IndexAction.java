package com.framework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.base.BaseAction;
import com.framework.constant.Constant;

@Controller
@RequestMapping("/index")
public class IndexAction extends BaseAction {

	@RequestMapping("getPage")
	@Override
	public String getPage() throws Exception {
		request.setAttribute(Constant.MENU_STRING, Constant.MENU_INDEX);
		return Constant.MENU_INDEX;
	}

	public void logout() {

	}

	@Override
	public void getList() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getById() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
