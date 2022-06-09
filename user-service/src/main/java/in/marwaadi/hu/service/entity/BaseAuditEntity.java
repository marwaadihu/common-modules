package in.marwaadi.hu.service.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseAuditEntity implements Serializable {

	private static final long serialVersionUID = 3171636698938015069L;

	/**
	 * User-Id who created the row
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@NotNull
	@Column(name = "created_by", nullable = false, updatable = false)
	private Long createdBy;

	/**
	 * Time-stamp in UTC when the row is created
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@NotNull
	@Column(name = "created_date", nullable = false, updatable = false)
	private Long createdDate;

	/**
	 * User-Id who modified the row
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@NotNull
	@Column(name = "modified_by", nullable = false)
	private Long modifiedBy;

	/**
	 * Time-stamp in UTC when the row is modified
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@NotNull
	@Column(name = "modified_date", nullable = false)
	private Long modifiedDate;

	/**
	 * Boolean flag indicates if the row is deleted
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@NotNull
	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;

	/**
	 * Time-stamp in UTC when the row is deleted
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@Column(name = "deleted_on")
	private Long deletedOn;

	/**
	 * Default constructor
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public BaseAuditEntity() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getDeletedOn(), isDeleted());
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
		BaseAuditEntity other = (BaseAuditEntity) obj;
		return Objects.equals(getDeletedOn(), other.getDeletedOn()) && isDeleted() == other.isDeleted();
	}

	@Override
	public String toString() {
		return "BaseAuditEntity [createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", isDeleted=" + isDeleted + ", deletedOn=" + deletedOn
				+ ", toString()=" + super.toString() + "]";
	}
}
