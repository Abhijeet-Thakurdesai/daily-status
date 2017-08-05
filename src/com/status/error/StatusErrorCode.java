package com.status.error;

public enum StatusErrorCode implements ErrorCode{
	invalid_email,
	invalid_status,
	team_name_required,
	team_leader_required,
	team_member_required,
	company_name_required,
	company_not_found,
	alias_name_required,
	first_name_required,
	last_name_required,
	email_id_already_used,
	team_required,
	invalid_team,
	invalid_company;

	@Override
	public String getCode() {
		return this.name();
	}
}
