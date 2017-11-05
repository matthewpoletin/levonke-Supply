package com.levonke.Supply.service;

import com.levonke.Supply.domain.Component;
import com.levonke.Supply.web.model.ComponentRequest;

import java.util.List;

public interface ComponentService {
	List<Component> getComponents(Integer page, Integer size);
	Component create(ComponentRequest componentRequest);
	Component read(Integer componentId);
	Component update(Integer componentId, ComponentRequest componentRequest);
	void delete(Integer componentId);
}
