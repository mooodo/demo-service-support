/* 
 * Created by 2019年1月29日
 */
package com.demo.support.web.easyui;

import java.util.Collection;

import com.demo.support.entity.ResultSet;

/**
 * 
 * @author fangang
 */
public class EasyuiResult extends ResultSet {
	private int retCode = 0;
	private Collection<?> footer = null;
	
	public EasyuiResult(ResultSet resultSet) {
		this.setData(resultSet.getData());
		this.setPage(resultSet.getPage());
		this.setSize(resultSet.getSize());
		this.setCount(resultSet.getCount());
		this.setAggregate(resultSet.getAggregate());
	}
	/**
	 * @return the retCode
	 */
	public int getRetCode() {
		return retCode;
	}
	/**
	 * @param retCode the retCode to set
	 */
	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}
	/**
	 * @return the total
	 */
	public long getTotal() {
		return this.getCount()==null ? 0 : this.getCount();
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.setCount(total);
	}
	/**
	 * @return the rows
	 */
	public Collection<?> getRows() {
		return this.getData();
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(Collection<?> rows) {
		this.setData(rows);
	}
	/**
	 * @return the footer
	 */
	public Collection<?> getFooter() {
		return footer;
	}
	/**
	 * @param footer the footer to set
	 */
	public void setFooter(Collection<?> footer) {
		this.footer = footer;
	}
	
}
