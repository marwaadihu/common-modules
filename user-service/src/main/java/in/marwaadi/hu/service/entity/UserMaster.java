package in.marwaadi.hu.service.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user-master")
public class UserMaster extends BaseAuditEntity {

	private static final long serialVersionUID = 6865032984479264225L;

	/**
	 * Primary key
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "um_id")
	private Long id;

	/**
	 * First name of User
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@NotBlank
	@Column(name = "um_first_name", nullable = false)
	private String firstName;

	/**
	 * Last name of User
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Column(name = "um_last_name")
	private String lastName;

	/**
	 * E-mail ID of user
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Email
	@Column(name = "um_email", nullable = false, unique = true)
	private String email;

	/**
	 * Encrypted password of user
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{16,32}$")
	@Column(name = "um_password", nullable = false)
	private String password;

	/**
	 * Boolean flag indicates if account is expired or not
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Column(name = "um_account_non_expired", nullable = false)
	private boolean accountNonExpired = Boolean.TRUE;

	/**
	 * Boolean flag indicates if account is locked or not
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Column(name = "um_account_non_locked", nullable = false)
	private boolean accountNonLocked = Boolean.TRUE;

	/**
	 * Boolean flag indicates if credentials is expired or not
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Column(name = "um_password_non_expired", nullable = false)
	private boolean credentialsNonExpired = Boolean.TRUE;

	/**
	 * Boolean flag indicates if user is enabled or not
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Column(name = "um_enabled", nullable = false)
	private boolean enabled = Boolean.TRUE;

	/**
	 * Default constructor
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public UserMaster() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(getEmail(), getId());
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
		UserMaster other = (UserMaster) obj;
		return Objects.equals(getEmail(), other.getEmail()) && Objects.equals(getId(), other.getId());
	}

	@Override
	public String toString() {
		return "UserMaster [id=" + id + ", accountNonExpired=" + accountNonExpired + ", accountNonLocked="
				+ accountNonLocked + ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled
				+ ", toString()=" + super.toString() + "]";
	}

}
