package com.org.hermes.user.server.repository.sql;

public class MasterQuery {

	public static final String SELECT_ACTIVE_APP_DETAILS = "SELECT \"app_id\", \"app_name\", \"app_secret\", \"description\", \"auth_source\", "
			+ " \"auth_type\", \"token_type\", \"token_validity\", \"cookie_domain\", \"cookie_name\", \"cookie_path\", "
			+ " \"cookie_hide_from_script\", \"cookie_secure_only\", \"is_active\"  FROM \"apps\" WHERE \"is_active\" = 1";

	public static final String COLUMN_NAME_APP_ID = "APP_ID";
	public static final String COLUMN_NAME_APP_NAME = "APP_NAME";
	public static final String COLUMN_NAME_APP_SECRET = "APP_SECRET";
	public static final String COLUMN_NAME_DESCRIPTION = "DESCRIPTION";
	public static final String COLUMN_NAME_AUTH_SOURCE = "AUTH_SOURCE";
	public static final String COLUMN_NAME_AUTH_TYPE = "AUTH_TYPE";
	public static final String COLUMN_NAME_TOKEN_TYPE = "TOKEN_TYPE";
	public static final String COLUMN_NAME_TOKEN_VALIDITY = "TOKEN_VALIDITY";
	public static final String COLUMN_NAME_REDIRECTION_URL = "REDIRECTION_URL";
	public static final String COLUMN_NAME_COOKIE_DOMAIN = "COOKIE_DOMAIN";
	public static final String COLUMN_NAME_COOKIE_NAME = "COOKIE_NAME";
	public static final String COLUMN_NAME_COOKIE_PATH = "COOKIE_PATH";
	public static final String COLUMN_NAME_COOKIE_HIDE_FROM_SCRIPT = "COOKIE_HIDE_FROM_SCRIPT";
	public static final String COLUMN_NAME_COOKIE_SECURE_ONLY = "COOKIE_SECURE_ONLY";
	public static final String COLUMN_NAME_IS_ACTIVE = "IS_ACTIVE";
}
