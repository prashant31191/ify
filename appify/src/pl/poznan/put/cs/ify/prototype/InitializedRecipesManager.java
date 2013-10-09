package pl.poznan.put.cs.ify.prototype;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.cs.ify.features.YReceipt;

public class InitializedRecipesManager {
	private List<YReceipt> mInitializedRecipes = new ArrayList<YReceipt>();

	public void addInitializedRecipe(YReceipt receipt) {
		mInitializedRecipes.add(receipt);
	}

	public boolean isReceiptInitialized(String name) {
		for (YReceipt initialized : mInitializedRecipes) {
			if (name.equals(initialized.getName())) {
				return true;
			}
		}
		return false;
	}
}
