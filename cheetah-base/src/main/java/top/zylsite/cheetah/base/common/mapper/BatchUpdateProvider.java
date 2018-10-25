package top.zylsite.cheetah.base.common.mapper;

import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public class BatchUpdateProvider extends MapperTemplate {

	public BatchUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	public String batchUpdate(MappedStatement ms) {
		final String eachItemName = "record";
		final Class<?> entityClass = super.getEntityClass(ms);
		StringBuilder sql = new StringBuilder();
		sql.append("<foreach collection=\"list\" item=\"" + eachItemName + "\" separator=\";\" >");
		sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
		sql.append("<set>");

		Set<EntityColumn> columns = EntityHelper.getColumns(entityClass);
		String column, columnHolder;
		for (EntityColumn entityColumn : columns) {
			if (!entityColumn.isId()) {
				column = entityColumn.getColumn();
				columnHolder = entityColumn.getColumnHolder(eachItemName);
				sql.append(column).append("=").append(columnHolder).append(",");
			}
		}

		sql.append("</set>");
		sql.append(SqlHelper.wherePKColumns(entityClass));
		sql.append("</foreach>");
		
		return sql.toString();
	}

}
