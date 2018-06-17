package com.tonghu.pub.model.common.po;

import com.tonghu.pub.model.po.BusinessBasePo;

import java.sql.Timestamp;

/**
 * @Description: 作业日志表实体类
 * @author liangyongjian
 * @date 2017年9月29日 下午9:57:44
 */
public class JobLog extends BusinessBasePo {

	private String jobSequenceNo; //作业序列号
	private String jobName; //作业名称
	private Integer status; //作业运行状态
	private String note; //操作备注
	private Integer totalTask; //作业处理任务总数量
	private Integer successTask; //成功处理任务数量
	private Timestamp jobStartTime; //作业开始执行时间
	private Timestamp jobEndTime; //作业结束执行时间
	private Long costTime; //作业执行耗费的时间
	
	public JobLog(){}
	
	public JobLog(String jobSequenceNo, String jobName, Integer status, String note, Integer totalTask,
                  Integer successTask, Timestamp jobStartTime, Timestamp jobEndTime){
		this.jobSequenceNo = jobSequenceNo;
		this.jobName = jobName;
		this.status = status;
		this.note = note;
		this.totalTask = totalTask;
		this.successTask = successTask;
		this.jobStartTime = jobStartTime;
		this.jobEndTime = jobEndTime;
		if (jobStartTime != null && jobEndTime != null) {
			this.costTime = jobEndTime.getTime() - jobStartTime.getTime();
		}
	}
	
	public String getJobSequenceNo() {
		return jobSequenceNo;
	}

	public void setJobSequenceNo(String jobSequenceNo) {
		this.jobSequenceNo = jobSequenceNo;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getTotalTask() {
		return totalTask;
	}

	public void setTotalTask(Integer totalTask) {
		this.totalTask = totalTask;
	}

	public Integer getSuccessTask() {
		return successTask;
	}

	public void setSuccessTask(Integer successTask) {
		this.successTask = successTask;
	}
	
	public Timestamp getJobStartTime() {
		return jobStartTime;
	}

	public void setJobStartTime(Timestamp jobStartTime) {
		this.jobStartTime = jobStartTime;
	}

	public Timestamp getJobEndTime() {
		return jobEndTime;
	}

	public void setJobEndTime(Timestamp jobEndTime) {
		this.jobEndTime = jobEndTime;
	}

	public Long getCostTime() {
		return costTime;
	}

	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}

}
