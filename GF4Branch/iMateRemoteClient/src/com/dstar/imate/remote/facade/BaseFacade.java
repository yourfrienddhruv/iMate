/**
 * 
 */
package com.dstar.imate.remote.facade;

import com.dstar.imate.remote.IManager;

public abstract class BaseFacade<M extends IManager> implements IManagerFacade {
	protected  BaseFacade(M iManager){
		//place holder
	}
}


