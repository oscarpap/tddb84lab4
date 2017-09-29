package net.sf.freecol.common.model;

import net.sf.freecol.common.model.Colony.NoBuildReason;

public class TupleType {

	private final boolean correctReason;
	private final NoBuildReason noBuildReason;

	public TupleType(boolean correctReason, NoBuildReason noBuildReason) {
		this.correctReason = correctReason;
		this.noBuildReason = noBuildReason;
	}

	public boolean getCorrectReason() {
		return correctReason;

	}

	public NoBuildReason getNoBuildReason() {

		return noBuildReason;
	}
}