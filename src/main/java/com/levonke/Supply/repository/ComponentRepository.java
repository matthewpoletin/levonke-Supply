package com.levonke.Supply.repository;

import com.levonke.Supply.domain.Component;
import org.springframework.data.repository.CrudRepository;

public interface ComponentRepository
	extends CrudRepository<Component, Integer> {
}
