package jp.co.internous.ecsite.model.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.internous.ecsite.model.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	
	@Query(value="SELECT * FROM purchase p " +
			"INNER JOIN goods g " +
			"ON p.goods_id = g.id " +
			"WHERE created_at = (" +
			"SELECT MAX(created_at) FROM purchase p WHERE p.user_id = :userId) ",
			nativeQuery=true)
	List<Purchase> findHistory(@Param("userId") long userId);
	
	@Query(value="INSERT INTO purchase (user_id, goods_id, goods_name, item_count, total, created_at) " +
			"VALUES (?1, ?2, ?3, ?4, ?5, now())", nativeQuery=true)
	@Transactional
	@Modifying
	void persist(@Param("userId") long userId,
				 @Param("goodsId") long goodsId,
				 @Param("goodsName") String goodsName,
				 @Param("itemCount") long itemCount,
				 @Param("total") long total);

}