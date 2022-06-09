package in.marwaadi.hu.service.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

import in.marwaadi.hu.service.dto.AuditRequest;
import in.marwaadi.hu.service.dto.UpdateUserRequest;
import in.marwaadi.hu.service.dto.UserRequest;
import in.marwaadi.hu.service.dto.UserResponse;
import in.marwaadi.hu.service.dto.WhereClause;
import in.marwaadi.hu.service.entity.UserMaster;
import in.marwaadi.hu.service.enums.QueryClauseEnum;
import in.marwaadi.hu.service.repository.UserMasterRepository;
import in.marwaadi.hu.service.service.impl.UserMasterServiceImpl;
import in.marwaadi.hu.service.util.CommonUtil;

/**
 * Test class for {@link UserMasterServiceImpl}
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = { UserMasterServiceImpl.class })
public class UserMasterServiceTest {

	@Mock
	private UserMasterRepository userMasterRepository;

	@InjectMocks
	private UserMasterServiceImpl userMasterServiceImpl = new UserMasterServiceImpl();

	@Before
	public void init() {
		Field modelMapperField = ReflectionUtils.findField(UserMasterServiceImpl.class, "modelMapper",
				ModelMapper.class);
		ReflectionUtils.makeAccessible(modelMapperField);
		ReflectionUtils.setField(modelMapperField, userMasterServiceImpl, modelMapper());

		Field passwordEncoderField = ReflectionUtils.findField(UserMasterServiceImpl.class, "passwordEncoder",
				PasswordEncoder.class);
		ReflectionUtils.makeAccessible(passwordEncoderField);
		ReflectionUtils.setField(passwordEncoderField, userMasterServiceImpl, passwordEncoder());

		Field validatorField = ReflectionUtils.findField(UserMasterServiceImpl.class, "validator", Validator.class);
		ReflectionUtils.makeAccessible(validatorField);
		ReflectionUtils.setField(validatorField, userMasterServiceImpl, validator());
	}

	private Validator validator() {
		CustomValidatorBean customValidatorBean = new CustomValidatorBean();
		customValidatorBean.afterPropertiesSet();
		return customValidatorBean;
	}

	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Test
	public void testAddUser() {
		doReturn(new UserMaster()).when(userMasterRepository).save(Mockito.any(UserMaster.class));
		boolean addUser = userMasterServiceImpl.addUser(prepareUserRequest());
		assertTrue("Response must be true", addUser);
		verify(userMasterRepository).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAddUserConflict() {
		doThrow(DataIntegrityViolationException.class).when(userMasterRepository).save(Mockito.any(UserMaster.class));
		userMasterServiceImpl.addUser(prepareUserRequest());
		verify(userMasterRepository).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddUserRequestIsNull() {
		userMasterServiceImpl.addUser(null);
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	private void testAddUserEmail(String email) {
		UserRequest userRequest = prepareUserRequest();
		userRequest.setEmail(email);
		userMasterServiceImpl.addUser(userRequest);
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddUserEmailIdIsBlank() {
		testAddUserEmail(" ");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddUserEmailIdIsNotMatchPattern() {
		testAddUserEmail("Test");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddUserFirstNameIsNull() {
		testAddUserFirstName(null);
	}

	private void testAddUserFirstName(String firstName) {
		UserRequest userRequest = prepareUserRequest();
		userRequest.setFirstName(firstName);
		userMasterServiceImpl.addUser(userRequest);
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddUserFirstNameIsEmpty() {
		testAddUserFirstName("");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddUserFirstNameIsBlank() {
		testAddUserFirstName(" ");
	}

	private void testAddUserPassword(String password) {
		UserRequest userRequest = prepareUserRequest();
		userRequest.setPassword(password);
		userMasterServiceImpl.addUser(userRequest);
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddUserPasswordIsEmpty() {
		testAddUserPassword("");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddUserPasswordIsBlank() {
		testAddUserPassword(" ");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddUserPasswordIsNotMatchPattern() {
		testAddUserPassword("Test");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddUserModifiedByIsNull() {
		UserRequest userRequest = prepareUserRequest();
		userRequest.setModifiedBy(null);
		userMasterServiceImpl.addUser(userRequest);
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddUserModifiedDateIsNull() {
		UserRequest userRequest = prepareUserRequest();
		userRequest.setModifiedDate(null);
		userMasterServiceImpl.addUser(userRequest);
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	@Test
	public void testUpdateUser() {
		UserMaster toBeReturned = new UserMaster();
		doReturn(Arrays.asList(toBeReturned)).when(userMasterRepository).getAllUsers(Mockito.anyMap());
		doReturn(toBeReturned).when(userMasterRepository).save(Mockito.any(UserMaster.class));
		boolean updateUser = userMasterServiceImpl.updateUser(-1L, prepareUpdateUserRequest());
		assertTrue("Response must be true", updateUser);
		verify(userMasterRepository).getAllUsers(Mockito.anyMap());
		verify(userMasterRepository).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void testUpdateUserNoContent() {
		doReturn(Arrays.asList()).when(userMasterRepository).getAllUsers(Mockito.anyMap());
		userMasterServiceImpl.updateUser(-1L, prepareUpdateUserRequest());
		verify(userMasterRepository).getAllUsers(Mockito.anyMap());
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testUpdateUserConflict() {
		UserMaster toBeReturned = new UserMaster();
		doReturn(Arrays.asList(toBeReturned)).when(userMasterRepository).getAllUsers(Mockito.anyMap());
		doThrow(DataIntegrityViolationException.class).when(userMasterRepository).save(Mockito.any(UserMaster.class));
		userMasterServiceImpl.updateUser(-1L, prepareUpdateUserRequest());
		verify(userMasterRepository).getAllUsers(Mockito.anyMap());
		verify(userMasterRepository).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateUserRequestIsNull() {
		userMasterServiceImpl.updateUser(-1L, null);
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateUserUserIdIsNull() {
		userMasterServiceImpl.updateUser(null, prepareUpdateUserRequest());
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	private void testUpdateUserEmail(String email) {
		UpdateUserRequest updateUserRequest = prepareUpdateUserRequest();
		updateUserRequest.setEmail(email);
		userMasterServiceImpl.updateUser(-1L, updateUserRequest);
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUpdateUserEmailIdIsBlank() {
		testUpdateUserEmail(" ");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUpdateUserEmailIdIsNotMatchPattern() {
		testUpdateUserEmail("Test");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUpdateUserFirstNameIsNull() {
		testUpdateUserFirstName(null);
	}

	private void testUpdateUserFirstName(String firstName) {
		UpdateUserRequest updateUserRequest = prepareUpdateUserRequest();
		updateUserRequest.setFirstName(firstName);
		userMasterServiceImpl.updateUser(-1L, updateUserRequest);
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUpdateUserFirstNameIsEmpty() {
		testUpdateUserFirstName("");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUpdateUserFirstNameIsBlank() {
		testUpdateUserFirstName(" ");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUpdateUserModifiedByIsNull() {
		UpdateUserRequest updateUserRequest = prepareUpdateUserRequest();
		updateUserRequest.setModifiedBy(null);
		userMasterServiceImpl.updateUser(-1L, updateUserRequest);
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUpdateUserModifiedDateIsNull() {
		UpdateUserRequest updateUserRequest = prepareUpdateUserRequest();
		updateUserRequest.setModifiedDate(null);
		userMasterServiceImpl.updateUser(-1L, updateUserRequest);
		verify(userMasterRepository, times(0)).save(Mockito.any(UserMaster.class));
	}

	@Test
	public void testUpdateEmailIdForUser() {
		doReturn(1).when(userMasterRepository).updateEmailIdForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
		boolean updateEmailIdForUser = userMasterServiceImpl.updateEmailIdForUser(-1L, "test", prepareAuditRequest());
		assertTrue("Response must be true", updateEmailIdForUser);
		verify(userMasterRepository).updateEmailIdForUser(Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong(),
				Mockito.anyLong());
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void testUpdateEmailIdForUserNoContent() {
		doReturn(0).when(userMasterRepository).updateEmailIdForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
		userMasterServiceImpl.updateEmailIdForUser(-1L, "Test@Test.com", prepareAuditRequest());
		verify(userMasterRepository).updateEmailIdForUser(Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong(),
				Mockito.anyLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateEmailIdForUserUserIdIsNull() {
		userMasterServiceImpl.updateEmailIdForUser(null, "Test@Test.com", prepareAuditRequest());
		verify(userMasterRepository, times(0)).updateEmailIdForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateEmailIdForUserEmailIdIsNull() {
		userMasterServiceImpl.updateEmailIdForUser(-1L, null, prepareAuditRequest());
		verify(userMasterRepository, times(0)).updateEmailIdForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateEmailIdForUserAuditRequestIsNull() {
		userMasterServiceImpl.updateEmailIdForUser(-1L, "Test@Test.com", null);
		verify(userMasterRepository, times(0)).updateEmailIdForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUpdateEmailIdForUserAuditRequestModifiedByIsNull() {
		AuditRequest prepareAuditRequest = prepareAuditRequest();
		prepareAuditRequest.setModifiedBy(null);
		userMasterServiceImpl.updateEmailIdForUser(-1L, "Test@Test.com", prepareAuditRequest);
		verify(userMasterRepository, times(0)).updateEmailIdForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUpdateEmailIdForUserAuditRequestModifiedDateIsNull() {
		AuditRequest prepareAuditRequest = prepareAuditRequest();
		prepareAuditRequest.setModifiedDate(null);
		userMasterServiceImpl.updateEmailIdForUser(-1L, "Test@Test.com", prepareAuditRequest);
		verify(userMasterRepository, times(0)).updateEmailIdForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
	}

	@Test
	public void testUpdatePasswordForUser() {
		doReturn(1).when(userMasterRepository).updatePasswordForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
		boolean updatePasswordForUser = userMasterServiceImpl.updatePasswordForUser(-1L, "Test@1234567891011112",
				prepareAuditRequest());
		assertTrue("Response must be true", updatePasswordForUser);
		verify(userMasterRepository).updatePasswordForUser(Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong(),
				Mockito.anyLong());
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void testUpdatePasswordForUserNoContent() {
		doReturn(0).when(userMasterRepository).updatePasswordForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
		userMasterServiceImpl.updatePasswordForUser(-1L, "Test@1234567891011112", prepareAuditRequest());
		verify(userMasterRepository).updatePasswordForUser(Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong(),
				Mockito.anyLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdatePasswordForUserUserIdIsNull() {
		userMasterServiceImpl.updatePasswordForUser(null, "Test@1234567891011112", prepareAuditRequest());
		verify(userMasterRepository, times(0)).updatePasswordForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdatePasswordForUserPasswordIsNull() {
		userMasterServiceImpl.updatePasswordForUser(-1L, null, prepareAuditRequest());
		verify(userMasterRepository, times(0)).updatePasswordForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdatePasswordForUserAuditRequestIsNull() {
		userMasterServiceImpl.updatePasswordForUser(-1L, "Test@1234567891011112", null);
		verify(userMasterRepository, times(0)).updatePasswordForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUpdatePasswordForUserAuditRequestModifiedByIsNull() {
		AuditRequest prepareAuditRequest = prepareAuditRequest();
		prepareAuditRequest.setModifiedBy(null);
		userMasterServiceImpl.updatePasswordForUser(-1L, "Test@1234567891011112", prepareAuditRequest);
		verify(userMasterRepository, times(0)).updatePasswordForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testuUdatePasswordForUserAuditRequestModifiedDateIsNull() {
		AuditRequest prepareAuditRequest = prepareAuditRequest();
		prepareAuditRequest.setModifiedDate(null);
		userMasterServiceImpl.updatePasswordForUser(-1L, "Test@1234567891011112", prepareAuditRequest);
		verify(userMasterRepository, times(0)).updatePasswordForUser(Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong());
	}

	@Test
	public void testDeleteUser() {
		doReturn(1).when(userMasterRepository).deleteUser(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());
		boolean deleteUser = userMasterServiceImpl.deleteUser(-1L, prepareAuditRequest());
		assertTrue("Response must be true", deleteUser);
		verify(userMasterRepository).deleteUser(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void testDeleteUserNoContent() {
		doReturn(0).when(userMasterRepository).deleteUser(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());
		userMasterServiceImpl.deleteUser(-1L, prepareAuditRequest());
		verify(userMasterRepository).deleteUser(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteUserUserIdIsNull() {
		userMasterServiceImpl.deleteUser(null, prepareAuditRequest());
		verify(userMasterRepository, times(0)).deleteUser(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteUserAuditRequestIsNull() {
		userMasterServiceImpl.deleteUser(-1L, null);
		verify(userMasterRepository, times(0)).deleteUser(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testDeleteUserModifiedByIsNull() {
		AuditRequest prepareAuditRequest = prepareAuditRequest();
		prepareAuditRequest.setModifiedBy(null);
		userMasterServiceImpl.deleteUser(-1L, prepareAuditRequest);
		verify(userMasterRepository, times(0)).deleteUser(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testDeleteUserModifiedDateIsNull() {
		AuditRequest prepareAuditRequest = prepareAuditRequest();
		prepareAuditRequest.setModifiedDate(null);
		userMasterServiceImpl.deleteUser(-1L, prepareAuditRequest);
		verify(userMasterRepository, times(0)).deleteUser(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test
	public void testDeleteUsers() {
		doReturn(2).when(userMasterRepository).deleteUsers(Mockito.anySet(), Mockito.anyLong(), Mockito.anyLong());
		Set<Long> userIds = new HashSet<>();
		userIds.add(-1L);
		userIds.add(-2L);
		boolean deleteUsers = userMasterServiceImpl.deleteUsers(userIds, prepareAuditRequest());
		assertTrue("Response must be true", deleteUsers);
		verify(userMasterRepository).deleteUsers(Mockito.anySet(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void testDeleteUsersNoContent() {
		Set<Long> userIds = new HashSet<>();
		userIds.add(-1L);
		userIds.add(-2L);
		userMasterServiceImpl.deleteUsers(userIds, prepareAuditRequest());
		verify(userMasterRepository).deleteUsers(Mockito.anySet(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteUsersUserIdsIsNull() {
		userMasterServiceImpl.deleteUsers(null, prepareAuditRequest());
		verify(userMasterRepository, times(0)).deleteUsers(Mockito.anySet(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteUsersAuditRequestIsNull() {
		Set<Long> userIds = new HashSet<>();
		userIds.add(-1L);
		userIds.add(-2L);
		userMasterServiceImpl.deleteUsers(userIds, null);
		verify(userMasterRepository, times(0)).deleteUsers(Mockito.anySet(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testDeleteUsersModifiedByIsNull() {
		AuditRequest prepareAuditRequest = prepareAuditRequest();
		prepareAuditRequest.setModifiedBy(null);
		Set<Long> userIds = new HashSet<>();
		userIds.add(-1L);
		userIds.add(-2L);
		userMasterServiceImpl.deleteUsers(userIds, prepareAuditRequest);
		verify(userMasterRepository, times(0)).deleteUsers(Mockito.anySet(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testDeleteUsersModifiedDateIsNull() {
		AuditRequest prepareAuditRequest = prepareAuditRequest();
		prepareAuditRequest.setModifiedDate(null);
		Set<Long> userIds = new HashSet<>();
		userIds.add(-1L);
		userIds.add(-2L);
		userMasterServiceImpl.deleteUsers(userIds, prepareAuditRequest);
		verify(userMasterRepository, times(0)).deleteUsers(Mockito.anySet(), Mockito.anyLong(), Mockito.anyLong());
	}

	@Test
	public void testGetUser() {
		UserMaster toBeReturned = new UserMaster();
		doReturn(Arrays.asList(toBeReturned)).when(userMasterRepository).getAllUsers(Mockito.anyMap());
		UserResponse user = userMasterServiceImpl.getUser(-1L);
		assertNotNull("Response cannot be null", user);
		verify(userMasterRepository).getAllUsers(Mockito.anyMap());
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void testGetUserNoContent() {
		doReturn(Arrays.asList()).when(userMasterRepository).getAllUsers(Mockito.anyMap());
		userMasterServiceImpl.getUser(-1L);
		verify(userMasterRepository).getAllUsers(Mockito.anyMap());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUserUserIdIsNull() {
		userMasterServiceImpl.getUser(null);
		verify(userMasterRepository, times(0)).getAllUsers(Mockito.anyMap());
	}

	@Test
	public void testGetUsers() {
		doReturn(Arrays.asList()).when(userMasterRepository).getAllUsers(Mockito.anyMap());
		Map<String, WhereClause> whereClauseMap = new HashMap<>();
		whereClauseMap.put(CommonUtil.COLUMN_IS_DELETED, new WhereClause(QueryClauseEnum.EQUAL, Boolean.FALSE));
		List<UserResponse> users = userMasterServiceImpl.getUsers(whereClauseMap);
		assertNotNull("Response cannot be null", users);
		verify(userMasterRepository).getAllUsers(Mockito.anyMap());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUsersWhereClauseIsNull() {
		userMasterServiceImpl.getUsers(null);
		verify(userMasterRepository, times(0)).getAllUsers(Mockito.anyMap());
	}

	@Test
	public void testGetAllUsers() {
		doReturn(Arrays.asList(new UserMaster())).when(userMasterRepository).getAllUsers(Mockito.anyMap());
		Map<String, WhereClause> whereClauseMap = new HashMap<>();
		whereClauseMap.put(CommonUtil.COLUMN_IS_DELETED, new WhereClause(QueryClauseEnum.EQUAL, Boolean.FALSE));
		List<UserResponse> users = userMasterServiceImpl.getAllUsers(whereClauseMap);
		assertNotNull("Response cannot be null", users);
		verify(userMasterRepository).getAllUsers(Mockito.anyMap());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAllUsersWhereClauseIsNull() {
		userMasterServiceImpl.getAllUsers(null);
		verify(userMasterRepository, times(0)).getAllUsers(Mockito.anyMap());
	}

	private AuditRequest prepareAuditRequest() {
		AuditRequest auditRequest = new AuditRequest();
		auditRequest.setModifiedBy(System.currentTimeMillis());
		auditRequest.setModifiedDate(System.currentTimeMillis());
		return auditRequest;
	}

	private UpdateUserRequest prepareUpdateUserRequest() {
		UpdateUserRequest userUpdateRequest = new UpdateUserRequest();
		userUpdateRequest.setEmail("Test@Test.com");
		userUpdateRequest.setFirstName("Test");
		userUpdateRequest.setLastName("Test");
		userUpdateRequest.setModifiedBy(System.currentTimeMillis());
		userUpdateRequest.setModifiedDate(System.currentTimeMillis());
		return userUpdateRequest;
	}

	private UserRequest prepareUserRequest() {
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail("Test@Test.com");
		userRequest.setFirstName("Test");
		userRequest.setPassword("Test@1234567891011112");
		userRequest.setLastName("Test");
		userRequest.setModifiedBy(System.currentTimeMillis());
		userRequest.setModifiedDate(System.currentTimeMillis());
		return userRequest;
	}
}
