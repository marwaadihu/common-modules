package in.marwaadi.hu.service.repository.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import in.marwaadi.hu.service.dto.WhereClause;
import in.marwaadi.hu.service.entity.UserMaster;
import in.marwaadi.hu.service.enums.QueryClauseEnum;
import in.marwaadi.hu.service.util.CommonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = { CustomRepositoryImplTest.class })
public class CustomRepositoryImplTest {

	class CustomRepositoryChildImpl extends CustomRepositoryImpl<UserMaster> {

		@Override
		protected void setEntityManager(EntityManager entityManager) {
			this.entityManager = entityManager;
		}

		@Override
		protected void setTypeParameerClass(Class<UserMaster> typeParameterClass) {
			this.typeParameterClass = UserMaster.class;
		}
	}

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

	@SuppressWarnings("rawtypes")
	@Mock
	private Path path;

	@InjectMocks
	private CustomRepositoryChildImpl customRepositoryChildImpl = new CustomRepositoryChildImpl();

	@Before
	public void init() {
		customRepositoryChildImpl.setEntityManager(entityManager);
		customRepositoryChildImpl.setTypeParameerClass(UserMaster.class);
	}

	@Test
	public void testGetFilterdData() {
		doNothing().when(entityManager).clear();
		doReturn(criteriaBuilder).when(entityManager).getCriteriaBuilder();
		doReturn(createQuery).when(criteriaBuilder).createQuery(UserMaster.class);
		doReturn(from).when(createQuery).from(UserMaster.class);
		doReturn(createQuery).when(createQuery).select(from);
		doReturn(path).when(from).get(Mockito.anyString());
		doReturn(typedQuery).when(entityManager).createQuery(createQuery);
		doReturn(Arrays.asList()).when(typedQuery).getResultList();
		Map<String, WhereClause> whereClauseMap = new HashMap<>();
		whereClauseMap.put(CommonUtil.COLUMN_IS_DELETED, new WhereClause(QueryClauseEnum.EQUAL, Boolean.FALSE));
		whereClauseMap.put(CommonUtil.COLUMN_MODIFIED_DATE,
				new WhereClause(QueryClauseEnum.GREATER_THEN, System.currentTimeMillis()));
		whereClauseMap.put(CommonUtil.COLUMN_MODIFIED_BY,
				new WhereClause(QueryClauseEnum.GREATER_THEN_OR_EQUAL_TO, -1));
		whereClauseMap.put(CommonUtil.COLUMN_CREATED_DATE,
				new WhereClause(QueryClauseEnum.LESS_THEN, System.currentTimeMillis()));
		whereClauseMap.put(CommonUtil.COLUMN_CREATED_BY, new WhereClause(QueryClauseEnum.LESS_THEN_OR_EQUAL_TO, -1));
		whereClauseMap.put(CommonUtil.COLUMN_ID, new WhereClause(QueryClauseEnum.IN, Arrays.asList(-1)));
		List<UserMaster> allUsers = customRepositoryChildImpl.getFilterdData(whereClauseMap);
		assertNotNull("Response cannot be null", allUsers);
		verify(entityManager).clear();
		verify(entityManager).getCriteriaBuilder();
		verify(criteriaBuilder).createQuery(UserMaster.class);
		verify(createQuery).from(UserMaster.class);
		verify(createQuery).select(from);
		verify(entityManager).createQuery(createQuery);
		verify(from, times(6)).get(Mockito.anyString());
		verify(typedQuery).getResultList();
	}

	@Test
	public void testGetFilterdDataWithNotQuery() {
		doNothing().when(entityManager).clear();
		doReturn(criteriaBuilder).when(entityManager).getCriteriaBuilder();
		doReturn(createQuery).when(criteriaBuilder).createQuery(UserMaster.class);
		doReturn(from).when(createQuery).from(UserMaster.class);
		doReturn(createQuery).when(createQuery).select(from);
		doReturn(path).when(from).get(Mockito.anyString());
		doReturn(typedQuery).when(entityManager).createQuery(createQuery);
		doReturn(Arrays.asList()).when(typedQuery).getResultList();
		Map<String, WhereClause> whereClauseMap = new HashMap<>();
		whereClauseMap.put(CommonUtil.COLUMN_IS_DELETED, new WhereClause(QueryClauseEnum.NOT_EQUAL, Boolean.FALSE));
		whereClauseMap.put(CommonUtil.COLUMN_ID, new WhereClause(QueryClauseEnum.NOT_IN, Arrays.asList(-12)));
		List<UserMaster> allUsers = customRepositoryChildImpl.getFilterdData(whereClauseMap);
		assertNotNull("Response cannot be null", allUsers);
		verify(entityManager).clear();
		verify(entityManager).getCriteriaBuilder();
		verify(criteriaBuilder).createQuery(UserMaster.class);
		verify(createQuery).from(UserMaster.class);
		verify(createQuery).select(from);
		verify(entityManager).createQuery(createQuery);
		verify(from, times(2)).get(Mockito.anyString());
		verify(typedQuery).getResultList();
	}

	@Test
	public void testGetFilterdDataAllData() {
		doNothing().when(entityManager).clear();
		doReturn(criteriaBuilder).when(entityManager).getCriteriaBuilder();
		doReturn(createQuery).when(criteriaBuilder).createQuery(UserMaster.class);
		doReturn(from).when(createQuery).from(UserMaster.class);
		doReturn(createQuery).when(createQuery).select(from);
		doReturn(typedQuery).when(entityManager).createQuery(createQuery);
		doReturn(Arrays.asList()).when(typedQuery).getResultList();
		Map<String, WhereClause> whereClauseMap = new HashMap<>();
		List<UserMaster> allUsers = customRepositoryChildImpl.getFilterdData(whereClauseMap);
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
