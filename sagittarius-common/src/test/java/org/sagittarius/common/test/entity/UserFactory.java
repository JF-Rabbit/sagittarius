package org.sagittarius.common.test.entity;

public interface UserFactory<U extends User> {
	U create(String name);
}
