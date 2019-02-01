/* 
 * Created by 2019年1月29日
 */
package com.demo.support.web.easyui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.support.web.QueryController;

/**
 * The query controller for easyui
 * @author fangang
 */
@RestController
public class EasyuiQryController extends QueryController {

	@Override
	@PostMapping("easyui/query/{bean}")
	public EasyuiResult query(@PathVariable("bean")String beanName, HttpServletRequest request) {
		return new EasyuiResult(super.query(beanName, request));
	}
	
}
