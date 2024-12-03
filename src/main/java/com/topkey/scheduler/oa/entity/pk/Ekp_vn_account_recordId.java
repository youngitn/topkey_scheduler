package com.topkey.scheduler.oa.entity.pk;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ekp_vn_account_recordId implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String fd_id;
	private String sSystemNo;

}
