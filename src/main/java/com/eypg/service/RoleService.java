package com.eypg.service;

import com.eypg.dao.Pagination;
import com.eypg.pojo.Role;

public interface RoleService extends TService<Role, Integer> {

	public Pagination roleList(int pageNo, int pageSize);
	
}
