package top.zylsite.cheetah.base.common;

import java.io.Serializable;

/**
 * 
 * Description: 表格分页查询参数
 * @author jason
 * 2018年10月25日
 * @version 1.0
 */
public class QueryParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int DEFAULT_PAGE_NUMBER = 1;// 默认第一页
	private final int DEFAULT_PAGE_SIZE = 10;// 默认每页显示行数

	private Integer pageNumber;// 页码
	private Integer pageSize;// 每页行数
	private String sortName;// 需要排序的字段名称
	private String sortOrder;// 排序规则，升序或降序

	/**
	 * 判断是否需要排序
	 * 
	 * @return
	 * @create: 2018年3月22日 上午10:38:00 zhaoyl
	 * @history:
	 */
	public boolean isNeedOrder() {
		return isNotEmptySortName() && isNotEmptySortOrder();
	}

	/**
	 * 获取排序条件
	 * 
	 * @return
	 * @create: 2018年3月22日 上午10:38:13 zhaoyl
	 * @history:
	 */
	public String getOrderByClause() {
		return getSortName() + " " + getSortOrder();
	}

	public boolean isNotEmptySortName() {
		return null != sortName && !"".equals(sortName.trim());
	}

	public boolean isNotEmptySortOrder() {
		return null != sortOrder && !"".equals(sortOrder.trim());
	}

	public Integer getPageNumber() {
		return (null == pageNumber || pageNumber <= 0) ? DEFAULT_PAGE_NUMBER : pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return (null == pageSize || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortName() {
		if (isNotEmptySortName()) {
			sortName = sortName.trim();
		}
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		if (isNotEmptySortOrder()) {
			sortOrder.trim();
		}
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}
