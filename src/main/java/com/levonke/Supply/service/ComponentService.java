package com.levonke.Supply.service;

import com.levonke.Supply.domain.Component;
import com.levonke.Supply.web.model.ComponentRequest;

public interface ComponentService {
	
	Iterable<Component> getComponents();
	Component create(ComponentRequest componentRequest);
	Component read(Integer componentId);
	Component update(Integer componentId, ComponentRequest componentRequest);
	void delete(Integer componentId);
	
}
