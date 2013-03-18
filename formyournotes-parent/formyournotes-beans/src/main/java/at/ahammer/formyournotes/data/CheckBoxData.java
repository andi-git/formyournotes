package at.ahammer.formyournotes.data;

public class CheckBoxData extends FormYourNotesData {

	private boolean checked;

	public CheckBoxData() {
		super();
	}

	public CheckBoxData(boolean checked, int formId) {
		super(formId);
		this.checked = checked;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (checked ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckBoxData other = (CheckBoxData) obj;
		if (checked != other.checked)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CheckBoxData [checked=" + checked + "]";
	}

}
