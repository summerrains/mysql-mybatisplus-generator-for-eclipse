package net.summerrains.plugins.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import net.summerrains.plugins.DefaultConfigGenerator;

public final class Utils {
	
	public static void showMessageBox(Shell shell,String message,int style) {
		MessageBox mb = new MessageBox(shell,style);
		mb.setMessage(message);
		mb.open();
	}

	public static String throwableToString(Throwable throwable) {
		if(throwable == null) {
			return "";
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			throwable.printStackTrace(new PrintStream(out));
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		return out.toString();
	}
	
	public static void writeDefaultConfigGenerator(DefaultConfigGenerator defaultConfigGenerator) {
		FileOutputStream fout = null;
		try {
			Properties properties = new Properties();  
			fout = new FileOutputStream("defaultConfigGenerator");
			properties.setProperty("driverName", defaultConfigGenerator.getDbDriverName());
			properties.setProperty("host", defaultConfigGenerator.getHost());
			properties.setProperty("port", defaultConfigGenerator.getPort());
			properties.setProperty("database", defaultConfigGenerator.getDatabase());
			properties.setProperty("user", defaultConfigGenerator.getDbUser());
			properties.setProperty("password", defaultConfigGenerator.getDbPassword());
			properties.setProperty("saveDir", defaultConfigGenerator.getSaveDir());
			properties.setProperty("packageName", defaultConfigGenerator.getPackageName());
			properties.setProperty("tablePrefix", Boolean.toString(defaultConfigGenerator.isTablePrefix()));
			properties.setProperty("fieldPrefix", Boolean.toString(defaultConfigGenerator.isFieldPrefix()));
			properties.setProperty("columnPrefix", Boolean.toString(defaultConfigGenerator.isColumnPrefix()));
            properties.store(fout, "defaut config generator infomation");  
            fout.close();  
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if(fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	
	public static DefaultConfigGenerator readDefaultConfigGenerator() {
		DefaultConfigGenerator defaultConfigGenerator = null;
		FileInputStream fin = null;
		try {
			fin = new FileInputStream("defaultConfigGenerator");
			Properties properties = new Properties();  
	        properties.load(fin);  
	        fin.close();
	        defaultConfigGenerator = new DefaultConfigGenerator();
	        defaultConfigGenerator.setDbDriverName(properties.getProperty("driverName", "com.mysql.jdbc.Driver"));
	        defaultConfigGenerator.setHost(properties.getProperty("host", "127.0.0.1"));
			defaultConfigGenerator.setPort(properties.getProperty("port", "3306"));
			defaultConfigGenerator.setDatabase(properties.getProperty("database", "test"));
			defaultConfigGenerator.setDbUser(properties.getProperty("user", "root"));
			defaultConfigGenerator.setDbPassword(properties.getProperty("password", ""));
			defaultConfigGenerator.setSaveDir(properties.getProperty("saveDir", "./generator"));
			defaultConfigGenerator.setTablePrefix(Boolean.valueOf(properties.getProperty("tablePrefix", "true")));
			defaultConfigGenerator.setFieldPrefix(Boolean.valueOf(properties.getProperty("fieldPrefix", "true")));
			defaultConfigGenerator.setColumnPrefix(Boolean.valueOf(properties.getProperty("columnPrefix", "true")));
			defaultConfigGenerator.setPackageName(properties.getProperty("packageName", "com.yourcompany.db"));
			defaultConfigGenerator.setDbUrl("jdbc:mysql://"+defaultConfigGenerator.getHost()+":"+defaultConfigGenerator.getPort()+"/"+defaultConfigGenerator.getDatabase()+"?characterEncoding=utf8");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if(fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
				}
			}
		}
		if(defaultConfigGenerator == null) {
			defaultConfigGenerator = new DefaultConfigGenerator();
			defaultConfigGenerator.setDbDriverName("com.mysql.jdbc.Driver");
			defaultConfigGenerator.setHost("127.0.0.1");
			defaultConfigGenerator.setPort("3306");
			defaultConfigGenerator.setDatabase("test");
			defaultConfigGenerator.setDbUser("root");
			defaultConfigGenerator.setDbPassword("");
			defaultConfigGenerator.setSaveDir("./generator");
			defaultConfigGenerator.setTablePrefix(true);
			defaultConfigGenerator.setFieldPrefix(true);
			defaultConfigGenerator.setColumnPrefix(true);
			defaultConfigGenerator.setPackageName("com.yourcompany.db");
			defaultConfigGenerator.setDbUrl("jdbc:mysql://"+defaultConfigGenerator.getHost()+":"+defaultConfigGenerator.getPort()+"/"+defaultConfigGenerator.getDatabase()+"?characterEncoding=utf8");
		}
		return defaultConfigGenerator;
	}
	
	public static void writeDefaultTemplete(DefaultConfigGenerator defaultConfigGenerator) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			Element root = doc.createElement("templetes");
			doc.appendChild(root);
			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("entity", defaultConfigGenerator.getEntityTemplete());
			map.put("dao", defaultConfigGenerator.getDaoTemplete());
			map.put("mapper", defaultConfigGenerator.getMapperTemplete());
			map.put("service", defaultConfigGenerator.getServiceTemplete());
			map.put("serviceImpl", defaultConfigGenerator.getServiceImplTemplete());
			map.put("baseEntity", defaultConfigGenerator.getBaseEntityTemplete());
			map.put("repository", defaultConfigGenerator.getRepositoryTemplete());
			for (Entry<String, String> entry : map.entrySet()) {
				Element templete = doc.createElement("templete");
				Attr id = doc.createAttribute("id");
				id.setValue(entry.getKey());
				templete.setAttributeNode(id);
				Text content = doc.createTextNode(entry.getValue());
				templete.appendChild(content);
				root.appendChild(templete);
			}
			TransformerFactory tFactory =TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new java.io.File("defaultTemplete"));
			transformer.transform(source, result); 
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static DefaultConfigGenerator readDefaultTemplete(DefaultConfigGenerator defaultConfigGenerator) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("defaultTemplete")); 
			doc.normalize(); 
			NodeList nodeList = doc.getElementsByTagName("templete");
			int length = nodeList.getLength();
			for (int i = 0; i < length; i++) {
				Element element = (Element)nodeList.item(i);
				String name = element.getAttribute("id");
				String content = element.getTextContent();
				if("entity".equals(name)) {
					defaultConfigGenerator.setEntityTemplete(content);
				}
				if("dao".equals(name)) {
					defaultConfigGenerator.setDaoTemplete(content);
				}
				if("mapper".equals(name)) {
					defaultConfigGenerator.setMapperTemplete(content);
				}
				if("service".equals(name)) {
					defaultConfigGenerator.setServiceTemplete(content);
				}
				if("serviceImpl".equals(name)) {
					defaultConfigGenerator.setServiceImplTemplete(content);
				}
				if("baseEntity".equals(name)) {
					defaultConfigGenerator.setBaseEntityTemplete(content);
				}
				if("repository".equals(name)) {
					defaultConfigGenerator.setRepositoryTemplete(content);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defaultConfigGenerator;
	}
}
