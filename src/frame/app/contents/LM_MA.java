package frame.app.contents;

import frame.app.vars.CLM;
import frame.origin.contents.FM;

public class LM_MA extends FM implements CLM.MA {

	private static final long serialVersionUID = -4324448828998614663L;

	public void init() {
		jl1s = FMjl1s;
		jl2s = String.format(FMjl2s, LM_LG.permission);
		jl3s = FMjl3s;
	}
}
