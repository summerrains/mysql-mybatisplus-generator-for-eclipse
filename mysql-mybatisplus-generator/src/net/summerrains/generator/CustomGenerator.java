package net.summerrains.generator;

/**
 * 代码生成器Demo
 * @author summerrains
 */
public class CustomGenerator {

	public static void main(String[] args) {
		
		ConfigGenerator cg = new ConfigGenerator();
		cg.setDbDriverName("com.mysql.jdbc.Driver");
		cg.setDbUrl("jdbc:mysql://127.0.0.1:3306/confluence?characterEncoding=utf8");
		cg.setDbPassword("");
		cg.setDbUser("root");
		cg.setSaveDir("D://source");
		//去除表前缀
		cg.setTablePrefix(true);
		//去除字段前缀
		cg.setFieldPrefix(true);
		//base cloumn是否添加前缀
		cg.setColumnPrefix(true);
		
		cg.setPackageName("com.hna.unifiedpay.db");
//		cg.setTables(new String[]{"hibernate_unique_key"});
		cg.setGeneratorListener(new AutoGenerator.GeneratorListener() {
			@Override
			public void onSuccess() {
				System.out.println("dnoe");
			}
			@Override
			public void onProcess(String tableName, String filePath) {
				System.out.println(tableName + " generator to " + filePath);
			}
			@Override
			public void onError(String message, Throwable throwable) {
				throwable.printStackTrace();
			}
		});
		AutoGenerator.run(cg);
		
	}

}
