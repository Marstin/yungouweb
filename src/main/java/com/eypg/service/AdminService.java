package com.eypg.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.eypg.dao.Pagination;
import com.eypg.exception.AccountStatusException;
import com.eypg.exception.BadCredentialsException;
import com.eypg.exception.UsernameNotFoundException;
import com.eypg.pojo.Admin;
import com.eypg.pojo.Function;
import com.eypg.pojo.Role;
import com.eypg.pojo.User;

public interface AdminService extends TService<Admin, Integer>{
	/**
	 * 通过统一用户ID获得管理员
	 * 
	 * @param userId
	 * @return
	 */
	public Admin getByUserId(Integer userId);
	
	public Admin login(String userName, String password, String ip) 
			throws UsernameNotFoundException, BadCredentialsException, AccountStatusException;

	/**
	 * 通过登录名查找管理员
	 * 
	 * @param loginName
	 * @return
	 */
	public Admin getByLoginName(String loginName);

	/**
	 * 获得某站点管理员
	 * 
	 * @param webId
	 * @return
	 */
	public Pagination getAll(int page, int countPerPage);

	/**
	 * 获得登录状态的管理员
	 * 
	 * @param webId
	 * @param adminId
	 * @param userId
	 * @return
	 */
	//public Admin getLoginAdmin(Long adminId, Long userId);

	/**
	 * 获得登录状态的管理员
	 * 
	 * @param adminId
	 * @param userId
	 * @return
	 */
	//public Admin getLoginAdmin(Long adminId, Long userId, HttpSession session);

	/**
	 * 注册管理员
	 * 
	 * @param user
	 * @param admin
	 * @return
	 * @throws UserRegisterException
	 */
	public Admin register(User user, Set<Role> roleSet, Set<Function> funcSet);
	
	public Admin update(Admin admin, User user, Set<Role> roleSet, Set<Function> funcSet);
	
	public void deleteById(Serializable id);
}
