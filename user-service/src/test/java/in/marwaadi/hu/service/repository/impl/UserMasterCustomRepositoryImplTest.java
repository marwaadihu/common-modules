package in.marwaadi.hu.service.repository.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import in.marwaadi.hu.service.dto.WhereClause;
import in.marwaadi.hu.service.entity.UserMaster;
import in.marwaadi.hu.service.enums.QueryClauseEnum;
import in.marwaadi.hu.service.util.CommonUtil;

/**
 * Test class for {@link UserMasterCustomRepositoryImpl}
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = { UserMasterCustomRepositoryImpl.class })
public class UserMasterCustomRepositoryImplTest {

	@Mock
	private EntityManager entityManager;

	@Mock
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private CriteriaQuery<UserMaster> createQuery;

	@Mock
	private Root<UserMaster> from;

	@Mock
	private TypedQuery<UserMaster> typedQuery;

	@InjectMocks
	private UserMasterCustomRepositoryImpl userMasterCustomRepositoryImpl = new UserMasterCustomRepositoryImpl();

	@Before
	public void init() {
		userMasterCustomRepositoryImpl.setEntityManager(entityManager);
		userMasterCustomRepositoryImpl.setTypeParameerClass(UserMaster.class);
	}

	@Test
	public void testGetAllUsers() {
		doNothing().when(entityManager).clear();
		doReturn(criteriaBuilder).when(entityManager).getCriteriaBuilder();
		doReturn(createQuery).when(criteriaBuilder).createQuery(UserMaster.class);
		doReturn(from).when(createQuery).from(UserMaster.class);
		doReturn(createQuery).when(createQuery).select(from);
		doReturn(typedQuery).when(entityManager).createQuery(createQuery);
		doReturn(Arrays.asList()).when(typedQuery).getResultList();
		Map<String, WhereClause> whereClauseMap = new HashMap<>();
		whereClauseMap.put(CommonUtil.COLUMN_IS_DELETED, new WhereClause(QueryClauseEnum.EQUAL, Boolean.FALSE));
		List<UserMaster> allUsers = userMasterCustomRepositoryImpl.getAllUsers(whereClauseMap);
		assertNotNull("Response cannot be null", allUsers);
		verify(entityManager).clear();
		verify(entityManager).getCriteriaBuilder();
		verify(criteriaBuilder).createQuery(UserMaster.class);
		verify(createQuery).from(UserMaster.class);
		verify(createQuery).select(from);
		verify(entityManager).createQuery(createQuery);
		verify(typedQuery).getResultList();
	}

}
