package net.summerrains.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public final class AutoGenerator {

	private ConfigGenerator config;
	
	private static String PATH_ENTITY = null;
	private static String PATH_DAO = null;
	private static String PATH_XML = null;
	private static String PATH_SERVICE = null;
	private static String PATH_SERVICE_IMPL = null;
	private static String PATH_REPO = null;
	
	public interface GeneratorListener {
		void onError(String message,Throwable throwable);
		void onProcess(String tableName,String filePath);
		void onSuccess();
	}
	
	public AutoGenerator(ConfigGenerator config) {
		this.config = config;
	}
	
	public static void run(ConfigGenerator config) {
		/**
		 * 检查文件夹是否存在
		 */
		File gf = new File(config.getSaveDir());
		if (!gf.exists()) {
			gf.mkdirs();
		}
		
		/**
		 * 修改设置最终目录的逻辑
		 */
		String saveDir = gf.getPath();
		PATH_ENTITY = getFilePath(saveDir, getPathFromPackageName("src.main.java."+config.getPackageName()+".entity"));
		PATH_REPO = getFilePath(saveDir, getPathFromPackageName("src.main.java."+config.getPackageName()+".mybatis"));
		PATH_DAO = getFilePath(saveDir, getPathFromPackageName("src.main.java."+config.getPackageName()+".mapper"));
		PATH_XML = getFilePath(saveDir, getPathFromPackageName("src.main.resources.mybatis"));
		PATH_SERVICE = getFilePath(saveDir, getPathFromPackageName("src.main.java."+config.getPackageName()+".service"));
		PATH_SERVICE_IMPL = getFilePath(saveDir, getPathFromPackageName("src.main.java."+config.getPackageName()+".service.impl"));
		
		/**
		 * 开启生成映射关系
		 */
		new AutoGenerator(config).generate();

		/**
		 * 自动打开生成文件的目录
		 * <p>
		 * 根据 osName 执行相应命令
		 * </p>
		 */
		try {
			String osName = System.getProperty("os.name");
			if (osName != null) {
				if (osName.contains("Mac")) {
					Runtime.getRuntime().exec("open " + config.getSaveDir());
				} else if (osName.contains("Windows")) {
					Runtime.getRuntime().exec("cmd /c start " + config.getSaveDir());
				}
			}
		} catch (IOException e) {
			config.getGeneratorListener().onError("", e);
		}
	}
	
	/**
	 * 根据包名转换成具体路径
	 *
	 * @param packageName
	 * @return
	 */
	private static String getPathFromPackageName(String packageName) {
		if (packageName == null || packageName.length() == 0) {
			return "";
		}
		return packageName.replace(".", File.separator);
	}
	/**
	 * 生成文件地址
	 *
	 * @param segment
	 *            文件地址片段
	 * @return
	 */
	private static String getFilePath(String savePath, String segment) {
		File folder = new File(savePath + File.separator + segment);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return folder.getPath();
	}
	
	/**
	 * 检查配置的所有表是否在dbTables里存在.
	 * <ul>
	 * <li>如果未配置，则返回所有表</li>
	 * <li>所有都存在直接返回custTables.</li>
	 * <li>如果发现有不存在表名直接返回null。</li>
	 * </ul>
	 *
	 * @return
	 * @throws SQLException
	 */
	private List<String> getTables(Connection conn) throws SQLException {
		Map<String,String> tableMap = new HashMap<String,String>();
		PreparedStatement pstate = conn.prepareStatement(ConfigDataSource.MYSQL.getTablesSql());
		ResultSet results = pstate.executeQuery();
		while (results.next()) {
			String tableName = results.getString(1);
			tableMap.put(tableName, tableName);
		}
		
		List<String> tables = new ArrayList<String>();
		
		if(config.getTables() == null || config.getTables().length == 0) {
			for (Entry<String,String> entry : tableMap.entrySet()) {
				tables.add(entry.getValue());
			}
		} else {
			for(String tableName : config.getTables()) {
				if(tableMap.containsKey(tableName)) {
					tables.add(tableName);
				}
			}
		}
		return tables;
	}
	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(config.getDbDriverName());
		return DriverManager.getConnection(config.getDbUrl(), config.getDbUser(), config.getDbPassword());
	}
	
	/**
	 * 测试是否链接成功
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean testConnection() {
		Connection conn = null;
		try {
			conn = getConnection();
			if(conn != null) {
				return true;
			}
		} catch (ClassNotFoundException e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		} catch (SQLException e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					config.getGeneratorListener().onError(e.getMessage(), e);
				}
			}
		}
		return false;
	}
	/**
	 * 获取所有表名
	 * @return
	 */
	public List<String> getTables() {
		List<String> tables = new ArrayList<String>();
		Connection conn = null;
		try {
			conn = getConnection();
			Map<String,String> tableMap = new HashMap<String,String>();
			PreparedStatement pstate = conn.prepareStatement(ConfigDataSource.MYSQL.getTablesSql());
			ResultSet results = pstate.executeQuery();
			while (results.next()) {
				String tableName = results.getString(1);
				tableMap.put(tableName, tableName);
			}
			
			for (Entry<String,String> entry : tableMap.entrySet()) {
				tables.add(entry.getValue());
			}
			return tables;
		} catch (SQLException e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					config.getGeneratorListener().onError(e.getMessage(), e);
				}
			}
		}
		return tables;
	}
	
	/**
	 * 获取所有的数据库表注释
	 *
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getTablesComment() {
		Map<String, String> maps = new HashMap<String, String>();
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement pstate = conn.prepareStatement(ConfigDataSource.MYSQL.getTableCommentsSql());
			ResultSet results = pstate.executeQuery();
			while (results.next()) {
				maps.put(results.getString(ConfigDataSource.MYSQL.getTableName()),
						results.getString(ConfigDataSource.MYSQL.getTableComment()));
			}
		} catch (ClassNotFoundException e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		} catch (SQLException e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					config.getGeneratorListener().onError(e.getMessage(), e);
				}
			}
		}
		return maps;
	}
	
	private Map<String, String> getTablesComment(Connection conn) {
		Map<String, String> maps = new HashMap<String, String>();
		try {
			PreparedStatement pstate = conn.prepareStatement(ConfigDataSource.MYSQL.getTableCommentsSql());
			ResultSet results = pstate.executeQuery();
			while (results.next()) {
				maps.put(results.getString(ConfigDataSource.MYSQL.getTableName()),
						results.getString(ConfigDataSource.MYSQL.getTableComment()));
			}
		} catch (SQLException e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		}
		return maps;
	}

	/**
	 * 生成 beanName
	 *
	 * @param table
	 *            表名
	 * @return beanName
	 */
	private String getEntityName(String table) {
		StringBuilder sb = new StringBuilder();
		if (table.contains("_")) {
			String[] tables = table.split("_");
			int l = tables.length;
			int s = 0;
			if(config.isTablePrefix()) {				
				s = 1;
			}
			for (int i = s; i < l; i++) {
				String temp = tables[i].trim();
				if(temp.length() > 1) {
					sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1).toLowerCase());
				} else {
					sb.append(temp.toUpperCase());
				}
			}
		} else {
			if(table.length() > 1) {
				sb.append(table.substring(0, 1).toUpperCase()).append(table.substring(1));
			} else {
				sb.append(table.toUpperCase());
			}
		}
		return sb.toString();
	}
	
	private String getLowerEntityName(String entityName) {
		StringBuilder sb = new StringBuilder();
		sb.append(entityName.substring(0, 1).toLowerCase()).append(entityName.substring(1));
		return sb.toString();
	}
	
	/**
	 * 生成映射文件
	 */
	private void generate() {
		Connection conn = null;
		try {
			conn = getConnection();

			/**
			 * 根据配置获取应该生成文件的表信息
			 */
			List<String> tables = getTables(conn); //获取所有表
			if (null == tables || tables.size() == 0) {
				config.getGeneratorListener().onError("未选择任何表", null);
				return;
			}
			Map<String, String> tableComments = getTablesComment(conn); //获取所有表说明
			
			String createDate = getCreateDate();
			String author = System.getProperty("user.name");
			buildBaseEntity(createDate, author);
			buildRepo(createDate,author);
			for (String table : tables) {
				List<EntityField> entityFieldList = new ArrayList<EntityField>();
				/* ID 是否存在,不考虑联合主键设置 */
				String tableFieldsSql = String.format(ConfigDataSource.MYSQL.getTableFieldsSql(), table);
				ResultSet results = conn.prepareStatement(tableFieldsSql).executeQuery(); //查询所有列名
				boolean idExist = false;
				PrimaryKey primaryKey = null;
				while (results.next()) {
					EntityField entityField = new EntityField(config.isFieldPrefix());
					entityField.setComment(results.getString(ConfigDataSource.MYSQL.getFieldComment()));
					entityField.setColumnName(results.getString(ConfigDataSource.MYSQL.getFieldName()));
					entityField.setType(results.getString(ConfigDataSource.MYSQL.getFieldType()));
					entityFieldList.add(entityField);
					
					//主键
					if(!idExist) {
						String key = results.getString(ConfigDataSource.MYSQL.getFieldKey());
						if ("PRI".equals(key)) {
							primaryKey = new PrimaryKey(config.isFieldPrefix());
							primaryKey.setColumnName(entityField.getColumnName());
							primaryKey.setType(entityField.getType());
							idExist = true;
						}
					}
				}
				
				String entityName = getEntityName(table);

				
				String entityLowerName = getLowerEntityName(entityName);
				 //生成映射文件
				buildEntity(tableComments.get(table),createDate,table,author,entityName,entityFieldList,primaryKey);
				buildDao(createDate,author,entityName);
				buildMapper(tableComments.get(table),createDate,table,entityName,entityFieldList);
				buildService(createDate,author,entityName);
				buildServiceImpl(createDate,author,entityName,entityLowerName);
			}
			config.getGeneratorListener().onSuccess();
		} catch (Exception e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					config.getGeneratorListener().onError(e.getMessage(), e);
				}
			}
		}
	}
	
	private String getCreateDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
	
	private void buildServiceImpl(String createDate,String author,String entityName,String entityLowerName) {
		String daoFile = PATH_SERVICE_IMPL + File.separator + entityName + "ServiceImpl.java";
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("author", author);
		data.put("entityName", entityName);
		data.put("entityLowerName", entityLowerName);
		data.put("createDate", createDate);
		data.put("packageName", config.getPackageName());
		try {
			config.getGeneratorListener().onProcess(entityName + "ServiceImpl.java",daoFile);
			freemarkerRender(config.getServiceImplTemplete(),daoFile,data);
		} catch (Exception e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		}
	}
	
	private void buildService(String createDate,String author,String entityName) {
		String daoFile = PATH_SERVICE + File.separator + entityName + "Service.java";
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("author", author);
		data.put("entityName", entityName);
		data.put("createDate", createDate);
		data.put("packageName", config.getPackageName());
		try {
			config.getGeneratorListener().onProcess(entityName+"Service.java",daoFile);
			freemarkerRender(config.getServiceTemplete(),daoFile,data);
		} catch (Exception e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		}
	}
	
	private void buildMapper(String tableComment,String createDate,String tableName,String entityName,List<EntityField> entityFieldList) {
		String mapperFile = PATH_XML + File.separator + entityName + "Mapper.xml";
		
		StringBuffer sb = new StringBuffer("");
		for (EntityField entityField : entityFieldList) {
			if(config.isColumnPrefix()) {
				sb.append(tableName + "." + entityField.getColumnName() + " as " + entityField.getLowerPropertyName() + ",");
			} else {
				sb.append(entityField.getColumnName() + " as " + entityField.getLowerPropertyName() + ",");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" ");
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("tableComment", tableComment);
		data.put("entityName", entityName);
		data.put("baseColumnList", sb.toString());
		data.put("createDate", createDate);
		data.put("tableName", tableName);
		data.put("packageName", config.getPackageName());
		try {
			config.getGeneratorListener().onProcess(entityName+"Mapper.xml",mapperFile);
			freemarkerRender(config.getMapperTemplete(),mapperFile,data);
		} catch (Exception e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		}
	}
	
	private void buildDao(String createDate,String author,String entityName) {
		String daoFile = PATH_DAO + File.separator + entityName + "Mapper.java";
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("author", author);
		data.put("entityName", entityName);
		data.put("createDate", createDate);
		data.put("packageName", config.getPackageName());
		try {
			config.getGeneratorListener().onProcess(entityName+"Mapper.java",daoFile);
			freemarkerRender(config.getDaoTemplete(),daoFile,data);
		} catch (Exception e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		}
	}
	private void buildBaseEntity(String createDate,String author) {
		String baseEntityFile = PATH_ENTITY + File.separator + "BaseEntity.java";
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("author", author);
		data.put("createDate", createDate);
		data.put("packageName", config.getPackageName());
		try {
			config.getGeneratorListener().onProcess("BaseEntity.java",baseEntityFile);
			freemarkerRender(config.getBaseEntityTemplete(),baseEntityFile,data);
		} catch (Exception e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		}
		
	}
	private void buildEntity(String tableComment,String createDate,String tableName,String author,String entityName,List<EntityField> entityFieldList,PrimaryKey primaryKey) {
		String entityFile = PATH_ENTITY + File.separator + entityName + ".java";
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("tableComment", tableComment);
		data.put("author", author);
		data.put("entityName", entityName);
		data.put("entityFieldList", entityFieldList);
		data.put("createDate", createDate);
		data.put("tableName", tableName);
		if(primaryKey != null) {
			data.put("primaryKey", primaryKey);
		}
		data.put("packageName", config.getPackageName());
		try {
			config.getGeneratorListener().onProcess(entityName+".java",entityFile);
			freemarkerRender(config.getEntityTemplete(),entityFile,data);
		} catch (Exception e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		}
		
	}
	
	private void buildRepo(String createDate,String author) {
		String repoFile = PATH_REPO + File.separator + "MyBatisRepository.java";
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("author", author);
		data.put("createDate", createDate);
		data.put("packageName", config.getPackageName());
		try {
			config.getGeneratorListener().onProcess("MyBatisRepository.java",repoFile);
			freemarkerRender(config.getRepositoryTemplete(),repoFile,data);
		} catch (Exception e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		}
		
	}
	
	/**
	 * 渲染模板
	 * @param tplFile
	 * @param outFile
	 * @param data
	 * @throws Exception 
	 */
	private void freemarkerRender(String tplFile,String outFile,Map<String,Object> data) {
		FileOutputStream fos = null;
		try {
			fos = new  FileOutputStream(outFile);
			//1.创建配置实例Cofiguration  
			Configuration cfg = new Configuration();  
			//从文件或jar包中获取模板
			/**
			//2.设置模板文件目录  
        	cfg.setDirectoryForTemplateLoading(new File(getPathFromPackageName("src.test.java."+config.getPackageName()+".generator")));  
			//2.模板在jar包文件中
			cfg.setClassForTemplateLoading(AutoGenerator.class, "");
			//获取模板（template）  
			Template template = cfg.getTemplate(tplFile);  
			 **/
			//字符串当做模板
			StringTemplateLoader stringLoader = new StringTemplateLoader();  
	        stringLoader.putTemplate("mybatisplusGeneratorTemplete",tplFile);  
	        cfg.setTemplateLoader(stringLoader);  
	        /**获取模板（template）  */
	        Template template = cfg.getTemplate("mybatisplusGeneratorTemplete","UTF-8"); 
			
	        //获取输出流（指定到控制台（标准输出））  
			Writer out = new OutputStreamWriter(fos,"UTF-8");  
			//数据与模板合并（数据+模板=输出）  
			template.process(data, out);  
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		} catch (IOException e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		} catch (TemplateException e) {
			config.getGeneratorListener().onError(e.getMessage(), e);
		} finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
