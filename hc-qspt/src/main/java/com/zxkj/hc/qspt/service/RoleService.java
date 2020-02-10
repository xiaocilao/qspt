package com.zxkj.hc.qspt.service;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.RoleEntity;

import java.util.List;

public interface RoleService {

    List<RoleEntity> listRoleByUserId(String userId);

    List<RoleEntity> listRoleByUrl(String userId);

}
