package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.features.YReceipt;
import pl.poznan.put.cs.ify.services.YReceiptsService;
import android.content.Context;
import android.util.Log;

public abstract class YFeature {
	/**
	 * 
	 * @return class name
	 */
	public abstract String getName();

	public abstract void initialize(Context ctx, YReceiptsService srv); // TODO:
																		// Remove
																		// first
																		// arg
																		// maybe

	public abstract void uninitialize();

	public abstract void registerReceipt(YReceipt receipt);

	public abstract void unregisterReceipt(YReceipt receipt);

	private int mReceiptsCount;

	public void addReceipt(YReceipt receipt) {
		Log.d("FEATURE", "RegisterReceipt: " + receipt.getName() + " to " + getName());
		mReceiptsCount++;
		registerReceipt(receipt);
	}

	public boolean isUsed() {
		return mReceiptsCount > 0;
	}

	public void removeUser(YReceipt receipt) {
		Log.d("FEATURE", "UnregisterReceipt: " + receipt.getName() + " from " + getName());
		unregisterReceipt(receipt);
		mReceiptsCount--;
	}

}
