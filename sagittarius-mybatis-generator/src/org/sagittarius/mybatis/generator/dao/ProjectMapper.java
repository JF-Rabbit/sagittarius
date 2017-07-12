package org.sagittarius.mybatis.generator.dao;

import org.sagittarius.mybatis.generator.model.Project;

public interface ProjectMapper {
    int insert(Project record);

    int insertSelective(Project record);
}