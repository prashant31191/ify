package pl.poznan.put.cs.ify.app.ui;

import pl.poznan.put.cs.ify.api.params.YParamList;

public interface IOptionsDialogDataProvider {

	public YParamList getRequired();
	public YParamList getOptional();
}