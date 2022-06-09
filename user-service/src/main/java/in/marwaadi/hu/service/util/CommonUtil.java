package in.marwaadi.hu.service.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.security.crypto.password.PasswordEncoder;

import in.marwaadi.hu.service.entity.BaseAuditEntity;

/**
 * @author a.anilagrawal5477@gmail.com
 *
 */
public final class CommonUtil {

	/**
	 * Field name of Entity for is_deleted column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public static final String COLUMN_IS_DELETED = "isDeleted";

	/**
	 * Field name of Entity for id column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public static final String COLUMN_ID = "id";

	/**
	 * Field name of Entity for modifiedDate column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public static final String COLUMN_MODIFIED_DATE = "modifiedDate";

	/**
	 * Field name of Entity for modifiedBy column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public static final String COLUMN_MODIFIED_BY = "modifiedBy";

	/**
	 * Field name of Entity for createdDate column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public static final String COLUMN_CREATED_DATE = "createdDate";

	/**
	 * Field name of Entity for createdBy column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public static final String COLUMN_CREATED_BY = "createdBy";

	/**
	 * Default constructor
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	private CommonUtil() {
		super();
	}

	/**
	 * Update Audit metadata for an entity
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param baseAuditEntity
	 * @param isUpdate
	 * @param modifiedBy
	 * @param modifiedDate
	 */
	public static void setAuditMetadata(BaseAuditEntity baseAuditEntity, boolean isUpdate, Long modifiedBy,
			Long modifiedDate) {
		if (!isUpdate) {
			baseAuditEntity.setCreatedBy(modifiedBy);
			baseAuditEntity.setCreatedDate(modifiedDate);
		}
		baseAuditEntity.setModifiedBy(modifiedBy);
		baseAuditEntity.setModifiedDate(modifiedDate);
	}

	/**
	 * Validate the request
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param <T>
	 * @param validator
	 * @param request
	 */
	public static <T> void validate(Validator validator, T request) {
		Set<ConstraintViolation<T>> violations = validator.validate(request);

		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<T> constraintViolation : violations) {
				sb.append(constraintViolation.getMessage());
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
	}

	/**
	 * Encode the user password
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param passwordEncoder
	 * @param userPassword
	 * @return
	 */
	public static String encodeUserPassword(PasswordEncoder passwordEncoder, String userPassword) {
		return passwordEncoder.encode(userPassword);
	}

}
