package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YAccelerometerEvent;
import pl.poznan.put.cs.ify.api.features.YAccelerometerFeature;
import pl.poznan.put.cs.ify.api.features.YRawPlayer;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class YPlay extends YReceipt {

	short tab[] = new short[16000];
	int idx = 0;

	@Override
	public void requestParams(YParamList params) {
	}

	@Override
	public void requestFeatures(YFeatureList feats) {
		feats.add(new YAccelerometerFeature());
		feats.add(new YRawPlayer());
	}

	@Override
	public String getName() {
		return "Play";
	}

	@Override
	public YReceipt newInstance() {
		return new YPlay();
	}

	@Override
	public void handleEvent(YEvent event) {
		YAccelerometerEvent e = (YAccelerometerEvent) event;
		Log.i("Event");

		int x = (int) Math.max(100 * (10 - e.getX()), 0);
		int y = (int) Math.max(100 * (10 - e.getX()), 0);
		int z = (int) Math.max(100 * (10 - e.getX()), 0);

		float angle = 0;
		for (int i = idx * tab.length / 10; i < (idx + 1) * tab.length / 10; i++) {
			float angular_frequency = (float) (2 * Math.PI) * x / 8000;
			tab[i] = (short) (Short.MAX_VALUE * ((float) Math.sin(angle)));
			angle += angular_frequency;
		}
		angle = 0;
		for (int i = idx * tab.length / 10; i < (idx + 1) * tab.length / 10; i++) {
			float angular_frequency = (float) (2 * Math.PI) * y / 4000;
			tab[i] = (short) (Short.MAX_VALUE * ((float) Math.sin(angle)));
			angle += angular_frequency;
		}
		angle = 0;
		for (int i = idx * tab.length / 10; i < (idx + 1) * tab.length / 10; i++) {
			float angular_frequency = (float) (2 * Math.PI) * z / 16000;
			tab[i] = (short) (Short.MAX_VALUE * ((float) Math.sin(angle)));
			angle += angular_frequency;
		}

		if (++idx == 10) {
			Log.i("Playing");
			idx = 0;
			((YRawPlayer) mFeatures.get(Y.Freq)).play(tab, 8000);
		}
	}
}
