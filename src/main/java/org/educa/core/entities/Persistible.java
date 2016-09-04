package org.educa.core.entities;

import java.io.Serializable;

public interface Persistible extends Serializable {
	void setId(Long id);
	Long getId();
}
