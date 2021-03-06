package edu.fmi.nn.backpropagation.view;

import edu.fmi.nn.backpropagation.model.NetworkConfiguration;

public interface ViewCallback {

	void onClearClicked(final NetworkConfiguration configuration);

	void onApproximateClicked(final NetworkConfiguration configuration);
}
