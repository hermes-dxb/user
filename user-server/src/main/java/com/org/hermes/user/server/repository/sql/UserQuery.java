package com.org.hermes.user.server.repository.sql;

public class UserQuery {

	public static final String UPDATE_USER_BY_ID = "UPDATE DCD_USER SET STATUS = ? , GRP_NUM = ?, DEP_ID = ?, COMPANY_NAME = ? , DED_LICENSE = ? , EMAIL = ? , FIRST_NAME = ? , LAST_NAME = ? , PHONE = ?, LAST_MOD_DATE = SYSDATE , LAST_MOD_USER = ? WHERE USER_ID = ?";
}
