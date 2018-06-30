package com.tonghu.pub.model.common.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @Description: 作业日志表实体类
 * @author liangyongjian
 * @date 2017年9月29日 下午9:57:44
 */
@Getter
@Setter
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

}
