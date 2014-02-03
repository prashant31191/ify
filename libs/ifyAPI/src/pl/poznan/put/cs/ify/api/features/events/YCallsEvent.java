package pl.poznan.put.cs.ify.api.features.events;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YCallsEvent extends YEvent {

	private final String state;
	private final String incomingNumber;

	public static final String OFFHOOK = "OFFHOOK";
	public static final String IDLE = "IDLE";
	public static final String RINGING = "RINGING";

	public YCallsEvent(String string, String incomingNumber) {
		this.state = string;
		this.incomingNumber = incomingNumber;
	}

	@Override
	public long getId() {
		return Y.Calls;
	}

	public String getIncomingNumber() {
		return incomingNumber;
	}

	public String getState() {
		return state;
	}
}
