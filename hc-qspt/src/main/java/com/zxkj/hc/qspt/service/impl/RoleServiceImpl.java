package com.zxkj.hc.qspt.service.impl;

import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.RoleDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.RoleEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.RoleResEntity;
import com.zxkj.hc.qspt.service.RoleService;
import com.zxkj.hc.qspt.setu.system.CacheStructure;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.jdbc.core.SQL;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Override
    @Cacheable(value = CacheStructure.HC_QSPT_CACHE, key = "'USER_ROLE:'+#userId")
    public List<RoleEntity> listRoleByUserId(String userId) {
        RoleEntity roleEntity=new RoleEntity();
        roleEntity.setXtZxbz("0");
        SQL sql=new SQL(SQL.SELECT,"SELECT role_id FROM role_user WHERE xt_zxbz='0' AND user_id=(SELECT id FROM `user` WHERE xt_zxbz='0' AND id=?)",new String[]{userId}, RoleResEntity.class);
        roleEntity.addConditonItem(new ConditionItem(RoleDto.ID,"in",sql));
        return roleEntity.queryListBySelf();
    }

    @Override
    @Cacheable(value = CacheStructure.HC_QSPT_CACHE, key = "'URL_ROLE:'+#url")
    public List<RoleEntity> listRoleByUrl(String url) {
        RoleEntity roleEntity=new RoleEntity();
        roleEntity.setXtZxbz("0");
        SQL sql=new SQL(SQL.SELECT,"SELECT role_id FROM role_res WHERE xt_zxbz='0' AND res_id=(SELECT id FROM resource WHERE xt_zxbz='0' AND res_url=?)",new String[]{url}, RoleResEntity.class);
        roleEntity.addConditonItem(new ConditionItem(RoleDto.ID,"in",sql));
        return roleEntity.queryListBySelf();
    }
}
