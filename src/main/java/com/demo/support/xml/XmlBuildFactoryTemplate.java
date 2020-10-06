/*
 * created on 2009-11-16 
 */
package com.demo.support.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 以DOM的方式读取和解析XML文件，并创建工厂的外观模型。
 * 它通过调用initFactory()，从指定的一个或多个XML文件中读取数据，
 * 然后将读取出的数据装载到一个工厂中。
 * <p>该类的所有继承类必须通过对buildFactory()的实现，
 * 具体定义如何将读取出的数据装载到一个工厂中。
 * @author FanGang
 */
public abstract class XmlBuildFactoryTemplate {
	private boolean validating = false;
	private boolean namespaceAware = false;
	private String[] paths;
	
	/**
	 * 确定在解析XML创建工厂时，是否提供对 XML 名称空间支持的解析器
	 * @return the namespaceAware
	 */
	public boolean isNamespaceAware() {
		return namespaceAware;
	}

	/**
	 * 指定由此代码生成的解析器将提供对 XML 名称空间的支持
	 * @param namespaceAware the namespaceAware to set
	 */
	public void setNamespaceAware(boolean namespaceAware) {
		this.namespaceAware = namespaceAware;
	}

	/**
	 * 确定在解析XML创建工厂时，是否解析器在解析时验证 XML 内容。
	 * @return the validating
	 */
	public boolean isValidating() {
		return validating;
	}

	/**
	 * 指定由此代码生成的解析器将验证被解析的 XML 文档
	 * @param validating the validating to set
	 */
	public void setValidating(boolean validating) {
		this.validating = validating;
	}

	/**
	 * 初始化工厂。根据路径读取XML文件，将XML文件中的数据装载到工厂中
	 * @param path XML的路径
	 */
	public void initFactory(String path){
		this.paths = new String[]{path};
		initFactory(this.paths);
	}
	
	/**
	 * 初始化工厂。根据路径列表依次读取XML文件，将XML文件中的数据装载到工厂中
	 * @param paths 路径列表
	 */
	public void initFactory(String... paths){
		try {
			ClassPathResourceLoader loader = new ClassPathResourceLoader(this.getClass());
			loader.loadResource(is->readXmlStream(is), paths);
		} catch (IOException e) {
			try {
				FileResourceLoader loader = new FileResourceLoader();
				loader.loadResource(is->readXmlStream(is), paths);
			} catch (IOException e1) {
				try {
					UrlResourceLoader loader = new UrlResourceLoader();
					loader.loadResource(is->readXmlStream(is), paths);
				} catch (IOException e2) {
					throw new RuntimeException("no found the file", e2);
				}
			}
		}
	}
	
	/**
	 * 重新初始化工厂，初始化所需的参数，为上一次初始化工厂所用的参数。
	 */
	public void reloadFactory(){
		initFactory(this.paths);
	}

	/**
	 * 读取并解析一个XML的文件输入流，以Element的形式获取XML的根，
	 * 然后调用<code>buildFactory(Element)</code>构建工厂
	 * @param inputStream 文件输入流
	 */
	protected void readXmlStream(InputStream inputStream) {
		try {
			if(inputStream==null) throw new RuntimeException("no input stream");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setValidating(this.isValidating());
	        factory.setNamespaceAware(this.isNamespaceAware());
	        DocumentBuilder build = factory.newDocumentBuilder();
	        Document doc = build.parse(new InputSource(inputStream));
	        Element root = doc.getDocumentElement();
	        buildFactory(root);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new RuntimeException("Error when decode xml stream by sax", e);
		}
		
	}
	
	/**
	 * 用从一个XML的文件中读取的数据构建工厂
	 * @param root 从一个XML的文件中读取的数据的根
	 */
	protected void buildFactory(Element root) {
		NodeList nodeList = root.getChildNodes();
		for(int i=0; i<=nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(!(node instanceof Element)) continue;
			Element element = (Element) node;
			loadBean(element);
		}
	}
	
	/**
	 * define what to do with each of node.
	 * @param element
	 */
	protected abstract void loadBean(Element element);
}
