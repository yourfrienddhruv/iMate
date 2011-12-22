package com.dstar.imate.remote.data;

import com.eaio.uuid.UUID;

public abstract class AbstractViewObject implements IView {
	private static final long serialVersionUID = 1L;
	private UUID id;

	public UUID getId() {
		return id;
	}
	
}
