package com.topkey.scheduler.erp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.topkey.scheduler.erp.dto.SalesOrderDetail;
import com.topkey.scheduler.erp.entity.F4211;
import com.topkey.scheduler.erp.entity.pk.F4211Id;

@Repository
public interface SalesOrderRepository extends JpaRepository<F4211, F4211Id> {

	/**
	 * 兩個entity join查詢後將結果放在CombinedData這個class.
	 * 尚有Spring Data JPA Projections的作法 透過介面來達到直接取得需要欄位
	 * @param ukid
	 * @return
	 */
    @Query("SELECT new com.topkey.scheduler.erp.dto.SalesOrderDetail(d, m) FROM F554211F m JOIN F4211 d ON m.fnDoco = d.sdDoco " +
           "AND m.fnLnid = d.sdLnid AND d.sdkCoo = m.fnKcoo AND m.fnDcto = d.sdDcto " +
           "WHERE m.fnUkid = :ukid")
    
    List<SalesOrderDetail> findByUkid(@Param("ukid") String ukid); 
}
