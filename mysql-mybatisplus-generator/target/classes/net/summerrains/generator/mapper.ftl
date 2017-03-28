<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- table:${tableName} date:${createDate} comment:${tableComment} -->
<!-- namespace必须指向${packageName}.mapper包下的Mapper接口 -->
<mapper namespace="${packageName}.mapper.${entityName}Mapper">
	<!-- 通用查询结果列-->
	<sql id="Base_Column_List">${baseColumnList}</sql>
	
	
</mapper> 