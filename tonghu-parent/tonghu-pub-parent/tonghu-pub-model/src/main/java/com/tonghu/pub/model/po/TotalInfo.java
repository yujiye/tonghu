package com.tonghu.pub.model.po;

/**
 * @Description: 封装查询结果的数量信息
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:37:50
 */
public class TotalInfo extends BasePo {

	private Integer totalCount; // 记录总的数量
	private Integer pageSize; // 每页的记录条数
	private Integer pageTotal; // 总页数
	private Integer currentPage; // 当前页码
	private Integer startNum; // 当前页起始记录的序号
	private Integer endNum;// 当前页结束记录的序号
	
	public TotalInfo(){}
	
	public TotalInfo(Integer totalCount, Integer pageSize, Integer currentPage, Integer startNum) {
		super();
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.pageTotal = (totalCount / pageSize) + ((totalCount % pageSize) > 0 ? 1 : 0);
		this.currentPage = currentPage;
		this.startNum = startNum;
		this.endNum = (startNum + pageSize) > totalCount ? totalCount : (startNum + pageSize);
		
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getEndNum() {
		return endNum;
	}

	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}

}
