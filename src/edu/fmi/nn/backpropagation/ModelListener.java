package edu.fmi.nn.backpropagation;

import edu.fmi.nn.backpropagation.model.FunctionModel;

public interface ModelListener {
	void onModelChanged(final FunctionModel model);
}
