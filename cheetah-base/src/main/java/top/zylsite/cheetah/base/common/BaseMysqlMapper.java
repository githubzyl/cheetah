package top.zylsite.cheetah.base.common;

import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Description: 继承通用Mapper的接口，可扩展,MysqlMapper
 * 注意：此接口不能当做Mapper被扫描到，否则会报错
 * @author jason
 * 2018年10月25日
 * @version 1.0
 */
public interface BaseMysqlMapper<T>  extends BaseMapper<T>, MySqlMapper<T> {

}