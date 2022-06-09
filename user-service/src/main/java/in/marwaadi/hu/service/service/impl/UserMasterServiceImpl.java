package in.marwaadi.hu.service.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import in.marwaadi.hu.service.dto.AuditRequest;
import in.marwaadi.hu.service.dto.UpdateUserRequest;
import in.marwaadi.hu.service.dto.UserRequest;
import in.marwaadi.hu.service.dto.UserResponse;
import in.marwaadi.hu.service.dto.WhereClause;
import in.marwaadi.hu.service.entity.UserMaster;
import in.marwaadi.hu.service.enums.QueryClauseEnum;
import in.marwaadi.hu.service.repository.UserMasterRepository;
import in.marwaadi.hu.service.service.UserMasterService;
import in.marwaadi.hu.service.util.CommonUtil;

/**
 * Implementation class for {@link UserMasterService}
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
@Service
@Validated
@Transactional
public class UserMasterServiceImpl implements UserMasterService {

	/**
	 * {@link Validator} to validate the methods
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Autowired
	private Validator validator;

	/**
	 * {@link ModelMapper} to convert the entity to DTO
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * {@link PasswordEncoder} to encode the password
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * {@link Repository} class for {@link UserMaster}
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Autowired
	private UserMasterRepository userMasterRepository;

	/**
	 * Default constructor
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public UserMasterServiceImpl() {
		super();
	}

	@Override
	public boolean addUser(@Valid @NotNull UserRequest userRequest) {
		CommonUtil.validate(validator, userRequest);
		UserMaster userMaster = modelMapper.map(userRequest, UserMaster.class);
		String encodeUserPassword = CommonUtil.encodeUserPassword(passwordEncoder, userRequest.getPassword());
		userMaster.setPassword(encodeUserPassword);
		CommonUtil.setAuditMetadata(userMaster, Boolean.FALSE, userRequest.getModifiedBy(),
				userRequest.getModifiedDate());
		userMasterRepository.save(userMaster);
		return Boolean.TRUE;
	}

	@Override
	public boolean updateUser(@Valid @NotNull Long userId, @Valid @NotNull UpdateUserRequest userUpdateRequest) {
		CommonUtil.validate(validator, userId);
		CommonUtil.validate(validator, userUpdateRequest);
		UserMaster userMaster = getUserFromDatabase(userId);
		String password = userMaster.getPassword();
		modelMapper.map(userUpdateRequest, userMaster);
		userMaster.setPassword(password);
		CommonUtil.setAuditMetadata(userMaster, Boolean.TRUE, userUpdateRequest.getModifiedBy(),
				userUpdateRequest.getModifiedDate());
		userMasterRepository.save(userMaster);
		return Boolean.TRUE;
	}

	private UserMaster getUserFromDatabase(Long userId) {
		Map<String, WhereClause> whereClauseMap = new HashMap<>();
		whereClauseMap.put(CommonUtil.COLUMN_ID, new WhereClause(QueryClauseEnum.EQUAL, userId));
		whereClauseMap.put(CommonUtil.COLUMN_IS_DELETED, new WhereClause(QueryClauseEnum.EQUAL, Boolean.FALSE));
		List<UserMaster> userMasters = userMasterRepository.getAllUsers(whereClauseMap);
		if (userMasters.size() != 1) {
			throw new EmptyResultDataAccessException(String.format("No User found with id %d", userId), 1);
		}
		return userMasters.get(0);
	}

	@Override
	public boolean updateEmailIdForUser(@Valid @NotNull Long userId, @Valid @Email String newEmailId,
			@Valid @NotNull AuditRequest auditData) {
		CommonUtil.validate(validator, userId);
		CommonUtil.validate(validator, newEmailId);
		CommonUtil.validate(validator, auditData);
		int rowUpdated = userMasterRepository.updateEmailIdForUser(userId, newEmailId, auditData.getModifiedBy(),
				auditData.getModifiedDate());
		boolean isSuccess = rowUpdated == 1;
		return throwEmptyResultDataAccessExceptionIfRequired(userId, isSuccess);
	}

	private boolean throwEmptyResultDataAccessExceptionIfRequired(Long userId, boolean isSuccess) {
		if (isSuccess) {
			return isSuccess;
		}
		throw new EmptyResultDataAccessException(String.format("No User found with id %d", userId), 1);
	}

	@Override
	public boolean updatePasswordForUser(@Valid @NotNull Long userId,
			@Valid @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{16,32}$") String newPassword,
			@Valid @NotNull AuditRequest auditData) {
		CommonUtil.validate(validator, userId);
		CommonUtil.validate(validator, newPassword);
		CommonUtil.validate(validator, auditData);
		String encodeUserPassword = CommonUtil.encodeUserPassword(passwordEncoder, newPassword);
		int rowUpdated = userMasterRepository.updatePasswordForUser(userId, encodeUserPassword,
				auditData.getModifiedBy(), auditData.getModifiedDate());
		boolean isSuccess = rowUpdated == 1;
		return throwEmptyResultDataAccessExceptionIfRequired(userId, isSuccess);
	}

	@Override
	public boolean deleteUser(@Valid @NotNull Long userId, @Valid @NotNull AuditRequest auditData) {
		CommonUtil.validate(validator, userId);
		CommonUtil.validate(validator, auditData);
		int rowUpdated = userMasterRepository.deleteUser(userId, auditData.getModifiedBy(),
				auditData.getModifiedDate());
		boolean isSuccess = rowUpdated == 1;
		return throwEmptyResultDataAccessExceptionIfRequired(userId, isSuccess);
	}

	@Override
	public boolean deleteUsers(@Valid @NotEmpty Set<Long> userIds, @Valid @NotNull AuditRequest auditData) {
		CommonUtil.validate(validator, userIds);
		CommonUtil.validate(validator, auditData);
		int rowUpdated = userMasterRepository.deleteUsers(userIds, auditData.getModifiedBy(),
				auditData.getModifiedDate());
		boolean isSuccess = rowUpdated == userIds.size();
		return throwEmptyResultDataAccessExceptionIfRequired(userIds, isSuccess);
	}

	private boolean throwEmptyResultDataAccessExceptionIfRequired(Set<Long> userIds, boolean isSuccess) {
		if (isSuccess) {
			return isSuccess;
		}
		throw new EmptyResultDataAccessException(
				String.format("One of the User not found from %s ids", Arrays.toString(userIds.toArray())),
				userIds.size());
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponse getUser(@Valid @NotNull Long userId) {
		CommonUtil.validate(validator, userId);
		UserMaster userMaster = getUserFromDatabase(userId);
		return modelMapper.map(userMaster, UserResponse.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserResponse> getUsers(@Valid @NotEmpty Map<String, WhereClause> whereClauseMap) {
		CommonUtil.validate(validator, whereClauseMap);
		whereClauseMap.put(CommonUtil.COLUMN_IS_DELETED, new WhereClause(QueryClauseEnum.EQUAL, Boolean.FALSE));
		return getAllUsers(whereClauseMap);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserResponse> getAllUsers(@Valid @NotEmpty Map<String, WhereClause> whereClauseMap) {
		CommonUtil.validate(validator, whereClauseMap);
		List<UserMaster> userMasters = userMasterRepository.getAllUsers(whereClauseMap);
		return userMasters.stream().map(userMaster -> modelMapper.map(userMaster, UserResponse.class))
				.collect(Collectors.toList());
	}

}
