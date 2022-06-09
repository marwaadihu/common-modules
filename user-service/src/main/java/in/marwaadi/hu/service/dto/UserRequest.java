package in.marwaadi.hu.service.dto;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class to add single User
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
@Getter
@Setter
public class UserRequest extends UpdateUserRequest {

	/**
	 * Encrypted password of user
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{16,32}$")
	private String password;

	/**
	 * Default constructor
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public UserRequest() {
		super();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "UserRequest [toString()=" + super.toString() + "]";
	}

}
