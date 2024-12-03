package com.topkey.scheduler.oa.entity;


import java.util.Date;

import com.topkey.scheduler.oa.entity.pk.Ekp_vn_account_recordId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ekp_vn_account_record", schema = "ekp")
@IdClass(Ekp_vn_account_recordId.class)
public class Ekp_vn_account_record {
	@Id
	@Column(name = "fd_id")	//系統自動產出的id
	private String fd_id; 

	
	@Column(name = "fd_txtFormNo")	//作業申請單號(BPM/OA)
	private String sFormNo;	
	
	
	@Column(name = "fd_txtName")	//申請人姓名
	private String sName;
	
	@Id
	@Column(name = "fd_txtSystemNo")//資料紀錄單號
	private String sSystemNo;
	
	
	@Column(name = "fd_txtID")		//申請人工號
	private String sId;
	
	@Column(name = "fd_dStartDate")	//預計開通時間
	private String dStartDate;
	
	@Column(name = "fd_dEndDate")	//預計註銷時間
	private String dEndDate;
	
	@Column(name = "fd_clPermission")//開通的帳號帳號
	private String sPermission;

	@Column(name = "fd_txtRemark")	//備註
	private String sRemark;

	@Column(name = "fd_dlState")	//權限狀態
	private String sState;

	@Column(name = "fd_dReal_EndDate")	//實際註銷時間
	private Date dRealEndDate;

	@Column(name = "fd_dFirstTime")	//第一次資料錄入時間
	private Date dFirstTime;
/*	
	public String getfd_id() {
		return fd_id;
	}
	public void setfd_id(String fd_id) {
		this.fd_id = fd_id;
	}
	
	public String getfd_txtFormNo() {
		return sFormNo;
	}
	public void setfd_txtFormNo(String fd_txtFormNo) {
		this.sFormNo = sFormNo;
	}*/
}
