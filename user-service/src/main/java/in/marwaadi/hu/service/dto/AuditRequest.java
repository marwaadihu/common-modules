package in.marwaadi.hu.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class to represent Audit history for the performed action
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
@Getter
@Setter
public class AuditRequest {

	/**
	 * User-Id who has performed the action
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@NotNull
	private Long modifiedBy;

	/**
	 * Date in UTC epoch
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@NotNull
	private Long modifiedDate;

	/**
	 * Default constructor
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	public AuditRequest() {
		super();
	}

	@Override
	public String toString() {
		return "AuditRequest [modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", toString()="
				+ super.toString() + "]";
	}

}
