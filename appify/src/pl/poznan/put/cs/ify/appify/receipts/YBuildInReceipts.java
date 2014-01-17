package pl.poznan.put.cs.ify.appify.receipts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YFilesFeature;

public class YBuildInReceipts {
	private List<YReceipt> mList = null;

	private void initList() {
		mList = new ArrayList<YReceipt>();
		mList.add(new YWifiOffWhenLowBattery());
		mList.add(new YSMSReceipt());
		mList.add(new YAwesomeDemoReceipt());
		mList.add(new YPlayAcceleration());
		mList.add(new GeocoderReceipt());
		mList.add(new YTimeRingerReceipt());
		mList.add(new YRC());
		mList.add(new YGG());
		mList.add(new YFindFriend());
		mList.add(new YFileRecipe());

	}

	public List<YReceipt> getList() {
		if (mList == null)
			initList();
		return Collections.unmodifiableList(mList);
	}
}
