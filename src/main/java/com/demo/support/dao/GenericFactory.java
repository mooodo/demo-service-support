/*
 * Created by 2020-06-24 12:59:10 
 */
package com.demo.support.dao;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import com.demo.support.entity.Entity;
import com.demo.support.utils.BeanUtils;
import com.demo.support.xml.XmlBuildFactoryTemplate;

/**
 * @author fangang
 */
public abstract class GenericFactory<T extends Entity<String>> extends XmlBuildFactoryTemplate {
	private Map<String, T> map = new HashMap<>();
	private Class<T> clazz;
	
	/**
	 * @return the clazz
	 */
	public Class<T> getClazz() {
		if(clazz==null) throw new RuntimeException("Please initialize the clazz in the constructor");
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	protected GenericFactory() {}
	
	/**
	 * @param element
	 */
	@Override
	protected void loadBean(Element element) {
		try {
			T bean = clazz.newInstance();
			loadBean(element, bean);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("", e);
		}
	}
	
	/**
	 * @param element
	 * @param bean
	 */
	private void loadBean(Element element, T bean) {
		Field[] fields = bean.getClass().getDeclaredFields();
		for(int i=0; i<fields.length; i++) {
			String fieldName = fields[i].getName();
			Class<?> type = fields[i].getType();
			String str = element.getAttribute(fieldName);
			if("".equals(str)) continue;
			Object value = BeanUtils.bind(type, str);
			BeanUtils.setValueByField(bean, fieldName, value);
		}
		save(bean);
	}
	
	/**
	 * save the bean.
	 * @param bean
	 */
	public void save(T bean) {
		String id = bean.getId();
		map.put(id, bean);
	}
	
	/**
	 * delete by id.
	 * @param id
	 */
	public void delete(String id) {
		map.remove(id);
	}
	
	/**
	 * @param id
	 * @return get by id
	 */
	public T get(String id) {
		return map.get(id);
	}
	
	/**
	 * @return list all of the values.
	 */
	public Collection<T> list() {
		return map.values();
	}
}
