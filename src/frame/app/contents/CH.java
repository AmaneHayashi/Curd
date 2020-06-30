package frame.app.contents;

import javax.swing.JFrame;

import common.support.SQLSupport;
import common.support.UtilSupport;
import frame.app.vars.CCH;
import frame.origin.contents.F2;
import frame.origin.contents.F2P1;

public class CH implements CCH {

	CHF2 jf;
	String search_name;

	public CH(String search_name) {
		this.search_name = search_name;
	}

	@Override
	public JFrame run() {
		jf = new CH(search_name).new CHF2();
		return jf;
	}

	public class CHF2 extends F2 {

		private static final long serialVersionUID = -316263345420212959L;

		@Override
		public void init() {
			jbs = F2jbs;
			rfstr = F2rfstr;
			shN = search_name;
		}
	}

	public class CHF2P1 extends F2P1 {

		private static final long serialVersionUID = -3310532987156997101L;

		@Override
		public void init() {
			jl2s = F2P1jl2s;
			mts = F2P1mts;
			jbs = F2P1jbs;
			dmcs = F2P1dmcs;
			shN = search_name;
		}

		@Override
		public void H() {
			colN = SQLSupport.sqlGetColN(UtilSupport.strReplace(shN, "_", 2, 4));
			dmcv = new String[colN.length][dmcs.length];
			for (int i = 0; i < dmcv.length; i++) {
				dmcv[i] = colN;
			}
			String pref = UtilSupport.subBefore(shN, "_");
			String suff = UtilSupport.subAfter(shN, "_");
			int label = UtilSupport.filterOrder(UtilSupport.Transpose(CURD)[1], (s) -> s.equals(pref));
			String prefix = CURD[label][0];
			String suffix = MENUf[label][UtilSupport.filterOrder(CURDf[label], (s) -> s.equals(suff))];
			jl1s = String.format("%s¡¤%s", prefix, suffix);

			super.H();

			dtm.addTableModelListener((l) -> jl2.setText(jl2s + (dtm.getRowCount())));
		}
	}
}
