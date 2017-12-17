package com.serveme.service.notification.external.dto.input;


/**
 * Created by DavidChains on 10/12/15.
 */
public class ParseDTO {
	
	private WhereClauseParseDTO where;
	private DataClauseParseDTO data;

	public ParseDTO() {
	}
	
	public WhereClauseParseDTO getWhere() {
		return where;
	}
	public void setWhere(WhereClauseParseDTO where) {
		this.where = where;
	}
	public DataClauseParseDTO getData() {
		return data;
	}
	public void setData(DataClauseParseDTO data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "ParseDTO [where=" + where + ", data=" + data + "]";
	}
	
	
}
