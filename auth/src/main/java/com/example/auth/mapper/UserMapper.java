package com.example.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.po.UserPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-24 16:31
 * @interfaceName: UserMapper
 * @description:
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserPo> {

    /**
     * 获取用户权限
     * @author mao
     * @param username
     * @return list
     * @date 2022-55-20 14:55
    **/
    List<String> getPermission(String username);
}
