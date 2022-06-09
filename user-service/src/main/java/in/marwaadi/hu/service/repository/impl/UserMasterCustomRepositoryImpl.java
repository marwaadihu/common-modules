package in.marwaadi.hu.service.repository.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.marwaadi.hu.service.dto.WhereClause;
import in.marwaadi.hu.service.entity.UserMaster;
import in.marwaadi.hu.service.repository.UserMasterCustomRepository;

/**
 * Implementation class for {@link UserMasterCustomRepository}
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
public class UserMasterCustomRepositoryImpl extends CustomRepositoryImpl<UserMaster>
		implements UserMasterCustomRepository {

	/**
	 * Default constructor
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public UserMasterCustomRepositoryImpl() {
		super();
	}

	@Override
	@Autowired
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	protected void setTypeParameerClass(Class<UserMaster> typeParameterClass) {
		this.typeParameterClass = UserMaster.class;
	}

	@Override
	public List<UserMaster> getAllUsers(Map<String, WhereClause> whereClauseMap) {
		return getFilterdData(whereClauseMap);
	}

}
