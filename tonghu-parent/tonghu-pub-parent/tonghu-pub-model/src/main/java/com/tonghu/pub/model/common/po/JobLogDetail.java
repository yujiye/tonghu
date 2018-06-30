package com.tonghu.pub.model.common.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/**
 * @Description: 作业详细日志表实体类
 * @author liangyongjian
 * @date 2017年9月30日 下午3:39:20
 */
@Getter
@Setter
public class JobLogDetail extends BusinessBasePo {

	private String jobSequenceNo; //作业序列号
	private String jobName; //作业名称
	private String taskContent; //当前任务处理所处理的内容
	private Integer status; //当前任务处理状态
	private String note; //操作备注
	
	public JobLogDetail(){}
	
	public JobLogDetail(String jobSequenceNo, String jobName, String taskContent, Integer status, String note){
		this.jobSequenceNo = jobSequenceNo;
		this.jobName = jobName;
		this.taskContent = taskContent;
		this.status = status;
		if (StringUtils.isNotEmpty(note) && note.length() > 1000) {
			note = note.substring(0, 1000);
		}
		this.note = note;
	}

}
