package in.marwaadi.hu.service.dto;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class to update single User
 * 
 * @author a.anilagrawal5477@gmail.com *
 */
@Getter
@Setter
public class UpdateUserRequest extends AuditRequest {

	/**
	 * First name of User
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@NotBlank
	private String firstName;

	/**
	 * Last name of User
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	private String lastName;

	/**
	 * E-mail ID of user
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Email
	private String email;

	/**
	 * Boolean flag indicates if account is expired or not
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	private boolean accountNonExpired = Boolean.TRUE;

	/**
	 * Boolean flag indicates if account is locked or not
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	private boolean accountNonLocked = Boolean.TRUE;

	/**
	 * Boolean flag indicates if credentials is expired or not
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	private boolean credentialsNonExpired = Boolean.TRUE;

	/**
	 * Boolean flag indicates if user is enabled or not
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	private boolean enabled = Boolean.TRUE;

	/**
	 * Default constructor
	 * 
	 * @author 111144
	 */
	public UpdateUserRequest() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getEmail());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserRequest other = (UserRequest) obj;
		return Objects.equals(getEmail(), other.getEmail());
	}

	@Override
	public String toString() {
		return "UpdateUserRequest [toString()=" + super.toString() + "]";
	}

}
