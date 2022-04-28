package com.wyj.library.service;

import com.wyj.library.mapper.UserMapper;
import com.wyj.library.model.User;
import com.wyj.library.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public List<User> checkUser(String loginName, String password){
        return userMapper.checkUser(loginName,password);
    }

    public List<User> getAllUser(UserExample example){
        return userMapper.selectByExample(example);
    }

    public List<User> searchUserList(Integer page, Integer limit,User user){
        Integer start = (page - 1)*limit;
        return userMapper.searchUserList(start,limit,user);
    }

    public int insertUser(User user){
        return userMapper.insertSelective(user);
    }

    public User getOneUser(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    public int updateUser(User user){
        return userMapper.updateByPrimaryKey(user);
    }

    public int deleteUser(Integer id){
        return userMapper.deleteByPrimaryKey(id);
    }

    public int selectCount() {
        return  userMapper.selectByCount();
    }
}
