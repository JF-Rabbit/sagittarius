package org.sagittarius.uitest.web.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sagittarius.mybatis.generator.ProjectMapper;
import org.sagittarius.mybatis.generator.dto.Project;
import org.sagittarius.mybatis.generator.dto.ProjectCriteria;
import org.sagittarius.mybatis.generator.dto.ProjectKey;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class TestCRUD extends AbstractJUnit4SpringContextTests{

	@Resource
	ProjectMapper mapper;

	//@Test
	public void testCreate() {
		Project project = new Project();
		project.setOwner("k2data");
		project.setProjName("project");
		mapper.insertSelective(project);
	}

	@Test
	public void testQueryByKey() {
		int id = 100000;
		ProjectKey projectKey = new ProjectKey();
		projectKey.setId(id);
		Project project = mapper.selectByPrimaryKey(projectKey);
		System.out.println(project);
	}

	@Test
	public void testQueryByExample() {
		String ownerString = "k2data";
		ProjectCriteria projectCriteria = new ProjectCriteria();
		ProjectCriteria.Criteria actualCriteria = projectCriteria.createCriteria();

		actualCriteria.andOwnerEqualTo(ownerString);

		List<Project> projectList = mapper.selectByExample(projectCriteria);
		projectList.forEach(System.out::println);
	}

	//@Test
	public void testDelete() {
		int id = 100000;
		ProjectKey projectKey = new ProjectKey();
		projectKey.setId(id);

		int effectRow = mapper.deleteByPrimaryKey(projectKey);
		System.out.println(effectRow);
	}

	//@Test
	public void testUpdate() {
		int id = 100000;
		ProjectKey projectKey = new ProjectKey();
		projectKey.setId(id);
		Project orignalProject = mapper.selectByPrimaryKey(projectKey);

		Project changedProject = new Project();
		changedProject.setId(orignalProject.getId());
		changedProject.setProjName("new Name");

		int effectRow = mapper.updateByPrimaryKeySelective(changedProject);
		System.out.println(effectRow);
	}
}
