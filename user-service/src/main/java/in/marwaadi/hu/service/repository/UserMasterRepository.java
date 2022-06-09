package in.marwaadi.hu.service.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.marwaadi.hu.service.entity.UserMaster;

/**
 * Repository interface for {@link UserMaster}
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface UserMasterRepository extends UserMasterCustomRepository, JpaRepository<UserMaster, Long> {

	/**
	 * Update e-mail Id of user
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param userId
	 * @param emailId
	 * @param modifiedBy
	 * @param modifiedDate
	 * @return
	 */
	@Query("UPDATE UserMaster SET emailId = :emailId, modifiedBy = :modifiedBy, modifiedDate = :modifiedDate WHERE id = :userId")
	int updateEmailIdForUser(@Param("userId") Long userId, @Param("emailId") String emailId,
			@Param("modifiedBy") Long modifiedBy, @Param("modifiedDate") Long modifiedDate);

	/**
	 * Update password of user
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param userId
	 * @param encodeUserPassword
	 * @param modifiedBy
	 * @param modifiedDate
	 * @return
	 */
	@Query("UPDATE UserMaster SET password = :password, modifiedBy = :modifiedBy, modifiedDate = :modifiedDate WHERE id = :userId")
	int updatePasswordForUser(@Param("userId") Long userId, @Param("password") String encodeUserPassword,
			@Param("modifiedBy") Long modifiedBy, @Param("modifiedDate") Long modifiedDate);

	/**
	 * Soft-Delete user
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param userId
	 * @param modifiedBy
	 * @param modifiedDate
	 * @return
	 */
	@Query("UPDATE UserMaster SET isDeleted = true, modifiedBy = :modifiedBy, deletedOn = :modifiedDate WHERE id = :userId")
	int deleteUser(@Param("userId") Long userId, @Param("modifiedBy") Long modifiedBy,
			@Param("modifiedDate") Long modifiedDate);

	/**
	 * Soft-Delete users
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param userIds
	 * @param modifiedBy
	 * @param modifiedDate
	 * @return
	 */
	@Query("UPDATE UserMaster SET isDeleted = true, modifiedBy = :modifiedBy, deletedOn = :modifiedDate WHERE id IN (:userIds)")
	int deleteUsers(@Param("userIds") Set<Long> userIds, @Param("modifiedBy") Long modifiedBy,
			@Param("modifiedDate") Long modifiedDate);

}
