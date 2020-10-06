/*
 * Created by 2020-06-25 13:59:49 
 */
package com.demo.support.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * load resources with the file path.
 * @author fangang
 */
public class FileResourceLoader 
					extends AbstractResourceLoader implements ResourceLoader {
	private static Log log = LogFactory.getLog(FileResourceLoader.class);
	@Override
	public boolean loadResource(ResourceCallBack callback, String path) throws IOException {
		boolean success = false;
		FileResource loader = new FileResource(new File(path));
		loader.setFilter(this.getFilter());
		Resource[] resources = loader.getResources();
		if(resources==null){return false;}
		log.debug("read files with FileResourceLoader");
		for(int i=0; i<resources.length; i++){
			printLogs(resources[i]);
			InputStream is = resources[i].getInputStream();
			if(is!=null){
				callback.apply(is);
				success = true;
			}
		}
		return success;
	}
	
	private void printLogs(Resource resource) {
		try {
			log.debug(resource.getFileName());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
