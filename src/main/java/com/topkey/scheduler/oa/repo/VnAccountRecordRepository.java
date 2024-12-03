package com.topkey.scheduler.oa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.topkey.scheduler.erp.dto.SalesOrderDetail;
import com.topkey.scheduler.oa.entity.Ekp_vn_account_record;
import com.topkey.scheduler.oa.entity.pk.Ekp_vn_account_recordId;

@Repository
public interface VnAccountRecordRepository extends JpaRepository<Ekp_vn_account_record,Ekp_vn_account_recordId> {
	/**
	 * 
	 * @return
	 */
  
    @Query(value = "SELECT fd_id,fd_txtFormNo,fd_txtSystemNo,fd_txtName,fd_txtID,DATE(fd_dStartDate) AS fd_dStartDate,DATE(fd_dEndDate) AS fd_dEndDate,fd_clPermission,fd_txtRemark,fd_dlState,fd_dReal_EndDate,fd_dFirstTime FROM ekp_vn_account_record WHERE DATE(fd_dEndDate) = CURDATE()", nativeQuery = true)
    List<Ekp_vn_account_record> findTodayEndDateRecordsNative();
}
