package in.marwaadi.hu.service.dto;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Response class for User Entity
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
@Getter
@Setter
public class UserResponse extends UserRequest {

	/**
	 * Primary Key
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	private Long userId;

	/**
	 * Default constructor
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public UserResponse() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(getUserId());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserResponse other = (UserResponse) obj;
		return Objects.equals(getUserId(), other.getUserId());
	}

	@Override
	public String toString() {
		return "UserResponse [userId=" + userId + ", toString()=" + super.toString() + "]";
	}

}
