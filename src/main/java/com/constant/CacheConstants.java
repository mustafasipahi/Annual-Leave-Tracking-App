package com.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheConstants {

    public static final String USER_DETAIL_CACHE = "user_detail_cache_";
    public static final String USER_ANNUAL_LEAVE_LIST_CACHE = "user_list_annual_leave_cache_";
    public static final String USER_TOTAL_ANNUAL_LEAVE_CACHE = "user_total_annual_leave_cache_";
}
