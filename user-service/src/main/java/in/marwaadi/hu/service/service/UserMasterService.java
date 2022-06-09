package in.marwaadi.hu.service.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import in.marwaadi.hu.service.dto.AuditRequest;
import in.marwaadi.hu.service.dto.UpdateUserRequest;
import in.marwaadi.hu.service.dto.UserRequest;
import in.marwaadi.hu.service.dto.UserResponse;
import in.marwaadi.hu.service.dto.WhereClause;

/**
 * Service class related to User operations
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
public interface UserMasterService {

	/**
	 * Add User and return true if user is added successfully.
	 * 
	 * Validations/Exceptions
	 * 
	 * 1. {@link DataIntegrityViolationException} if user's e-mail is already
	 * associated with existing user.
	 * 
	 * 2. {@link ConstraintViolationException} if request is invalid
	 * 
	 * 3. {@link IllegalArgumentException} if request is null
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param userRequest
	 * @return
	 */
	boolean addUser(@Valid @NotNull UserRequest userRequest);

	/**
	 * Update User and return true if user is updated successfully.
	 * 
	 * Please note this will update all the fields of existing user except Password
	 * based on this request.
	 * 
	 * Validations/Exceptions
	 * 
	 * 1. {@link EmptyResultDataAccessException} if request user doesn't exist
	 * 
	 * 2. {@link DataIntegrityViolationException} if user's updated e-mail is
	 * already associated with existing user
	 * 
	 * 3. {@link ConstraintViolationException} is request is invalid
	 * 
	 * 4. {@link IllegalArgumentException} if request is null
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param userId
	 * @param userUpdateRequest
	 * @return
	 */
	boolean updateUser(@Valid @NotNull Long userId, @Valid @NotNull UpdateUserRequest userUpdateRequest);

	/**
	 * Update e-mail Id of user and return true if its updated successfully.
	 * 
	 * Validations/Exceptions
	 * 
	 * 1. {@link EmptyResultDataAccessException} if request user doesn't exist
	 * 
	 * 2. {@link DataIntegrityViolationException} if user's updated e-mail is
	 * already associated with existing user
	 * 
	 * 3. {@link ConstraintViolationException} is request is invalid
	 * 
	 * 4. {@link IllegalArgumentException} if request is null
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param userId
	 * @param newEmailId
	 * @param auditData
	 * @return
	 */
	boolean updateEmailIdForUser(@Valid @NotNull Long userId, @Valid @Email String newEmailId,
			@Valid @NotNull AuditRequest auditData);

	/**
	 * Update password of user and return true if its updated successfully.
	 * 
	 * Validations/Exceptions
	 * 
	 * 1. {@link EmptyResultDataAccessException} if request user doesn't exist
	 * 
	 * 2. {@link ConstraintViolationException} is request is invalid
	 * 
	 * 3. {@link IllegalArgumentException} if request is null
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param userId
	 * @param newPassword
	 * @param auditData
	 * @return
	 */
	boolean updatePasswordForUser(@Valid @NotNull Long userId,
			@Valid @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{16,32}$") String newPassword,
			@Valid @NotNull AuditRequest auditData);

	/**
	 * Delete User and return true if user is deleted successfully.
	 * 
	 * Validations/Exceptions
	 * 
	 * 1. {@link EmptyResultDataAccessException} if request user doesn't exist
	 * 
	 * 2. {@link ConstraintViolationException} is request is invalid
	 * 
	 * 3. {@link IllegalArgumentException} if request is null
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param userId
	 * @param auditData
	 * @return
	 */
	boolean deleteUser(@Valid @NotNull Long userId, @Valid @NotNull AuditRequest auditData);

	/**
	 * Delete Users and return true if all users are deleted successfully.
	 * 
	 * Validations/Exceptions
	 * 
	 * 1. {@link EmptyResultDataAccessException} if one of the user doesn't exist
	 * 
	 * 2. {@link ConstraintViolationException} is request is invalid
	 * 
	 * 3. {@link IllegalArgumentException} if request is null
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param userIds
	 * @param auditData
	 * @return
	 */
	boolean deleteUsers(@Valid @NotEmpty Set<Long> userIds, @Valid @NotNull AuditRequest auditData);

	/**
	 * Get non-deleted user by user-id
	 * 
	 * Validations/Exceptions
	 * 
	 * 1. {@link EmptyResultDataAccessException} if request user doesn't exist
	 * 
	 * 2. {@link IllegalArgumentException} if request is null
	 * 
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param userId
	 * @return
	 */
	UserResponse getUser(@Valid @NotNull Long userId);

	/**
	 * Get non-deleted user by whereClauseMap
	 * 
	 * Validations/Exceptions
	 * 
	 * 1. {@link ConstraintViolationException} is request is invalid
	 * 
	 * 2. {@link IllegalArgumentException} if request is null
	 * 
	 * Here key is name of field from {@link Entity} class
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param whereClauseMap
	 * @return
	 */
	List<UserResponse> getUsers(@Valid @NotEmpty Map<String, WhereClause> whereClauseMap);

	/**
	 * Get user by whereClauseMap
	 * 
	 * Validations/Exceptions
	 * 
	 * 1. {@link ConstraintViolationException} is request is invalid
	 * 
	 * 2. {@link IllegalArgumentException} if request is null
	 * 
	 * Here key is name of field from {@link Entity} class
	 * 
	 * We can fetch deleted users by this method
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param whereClauseMap
	 * @return
	 */
	List<UserResponse> getAllUsers(@Valid @NotEmpty Map<String, WhereClause> whereClauseMap);
}
