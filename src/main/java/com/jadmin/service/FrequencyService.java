package com.jadmin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jadmin.entity.Page;
import com.jadmin.entity.biz.Frequency;
import com.jadmin.mapper.FrequencyMapper;
import com.jadmin.util.PageData;

@Service
public class FrequencyService {

	@Autowired
	private FrequencyMapper customerMapper;
	
	/**
	 * 用户列表分页
	 * @param page
	 * @return
	 */
	public List<Frequency> getUserListPage(Page page){
		return customerMapper.queryUserListPage(page);
	}
	/**
	 * 查询列表
	 * @param pd
	 * @return
	 */
	public List<Frequency> getUserList(PageData pd){
		return customerMapper.queryUserList(pd);
	}
	/**
	 * 根据主键ID查询
	 * @param userId
	 * @return
	 */
	public Frequency getByUserId(Integer userId){
		return customerMapper.selectByUserId(userId);
	}
	/**
	 * 根据用户名查询
	 * @param userName
	 * @return
	 */
	public Frequency getByUserName(String userName){
		return customerMapper.selectByUserName(userName);
	}
	/**
	 * 验证用户名
	 * @param pd
	 * @return
	 */
	public Frequency validateUserName(PageData pd){
		return customerMapper.validateUserName(pd);
	}
	/**
	 * 插入用户
	 * @param user
	 * @return
	 */
	public int insert(Frequency user){
		return customerMapper.insert(user);
	}
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public int update(Frequency user){
		return customerMapper.update(user);
	}
	/**
	 * 根据主键ID删除
	 * @param userId
	 * @return
	 */
	public int deleteByUserId(Integer userId){
		return customerMapper.deleteByUserId(userId);
	}
	public List<Frequency> getXinListPage(Page page) {
		return customerMapper.queryXinListPage(page);
		}
	public Frequency getByXinUserId(Integer id) {
		return customerMapper.getByXinUserId(id);
		}
	public int updateXin(Frequency user) {
		return customerMapper.updateXin(user);
		}
	
}
