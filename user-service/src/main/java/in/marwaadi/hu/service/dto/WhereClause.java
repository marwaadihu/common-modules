package in.marwaadi.hu.service.dto;

import javax.validation.constraints.NotNull;

import in.marwaadi.hu.service.enums.QueryClauseEnum;
import lombok.Getter;

/**
 * Where clause class to filter the data
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
@Getter
public class WhereClause {

	/**
	 * Query clause which needs to applied on field
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@NotNull
	private final QueryClauseEnum queryClause;

	/**
	 * Value to filter for a field
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	@NotNull
	private final Object value;

	/**
	 * Parameterized constructor with all fields
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param queryClause
	 * @param value
	 */
	public WhereClause(@NotNull QueryClauseEnum queryClause, @NotNull Object value) {
		super();
		this.queryClause = queryClause;
		this.value = value;
	}

	@Override
	public String toString() {
		return "WhereClause [queryClause=" + queryClause + ", toString()=" + super.toString() + "]";
	}

}
