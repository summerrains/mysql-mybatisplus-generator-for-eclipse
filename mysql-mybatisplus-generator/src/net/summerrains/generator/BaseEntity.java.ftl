package ${packageName}.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * 所有表的基类
 * 
 * @author ${author}
 * @Date ${createDate}
 *
 */
public class BaseEntity implements Serializable {

	@TableField(exist=false)
	private static final long serialVersionUID = 1L;

	/**
	 * 备份时调用，以key=value的形式存储，和数据库字段对应
	 * 
	 * @return
	 */
	public Map<String, Object> backup() {
		return null;
	}

	/**
	 * 回滚、还原，和backup方法相反
	 * 
	 * @param data
	 */
	public void restore(Map<String, Object> data) {
	}

	/**
	 * 转换指定类型的数据
	 * 
	 * @param object
	 * @param t
	 * @return
	 */
	protected <T> T transform(Object object, Class<T> entityClazz, T defaultValue) {
		if (object != null) {
			try {
				T t = entityClazz.cast(object);
				return t;
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		}
		return defaultValue;
	}

}
