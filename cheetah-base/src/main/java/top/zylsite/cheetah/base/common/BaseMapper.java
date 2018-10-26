package top.zylsite.cheetah.base.common;

import tk.mybatis.mapper.common.Mapper;
import top.zylsite.cheetah.base.common.mapper.BatchUpdateMapper;

/**
 * Description: 继承通用Mapper的接口，可扩展
 * 注意：此接口不能当做Mapper被扫描到，否则会报错
 * @author jason
 * 2018年10月25日
 * @version 1.0
 */
public interface BaseMapper<T>  extends Mapper<T>, BatchUpdateMapper<T> {

}
