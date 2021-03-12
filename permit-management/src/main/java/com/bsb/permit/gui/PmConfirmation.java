package com.bsb.permit.gui;

public final class PmConfirmation {
	private boolean confirmed;
	private boolean checked;

	public PmConfirmation(boolean confirmed, boolean checked) {
		this.confirmed = confirmed;
		this.checked = checked;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public boolean isChecked() {
		return checked;
	}
}
