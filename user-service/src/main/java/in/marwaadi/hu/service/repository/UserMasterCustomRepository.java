package in.marwaadi.hu.service.repository;

import java.util.List;
import java.util.Map;

import in.marwaadi.hu.service.dto.WhereClause;
import in.marwaadi.hu.service.entity.UserMaster;

/**
 * Custom Repository interface for {@link UserMaster}
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
public interface UserMasterCustomRepository {

	/**
	 * Get All Users based on provided filters
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param whereClauseMap
	 * @return
	 */
	List<UserMaster> getAllUsers(Map<String, WhereClause> whereClauseMap);
}
