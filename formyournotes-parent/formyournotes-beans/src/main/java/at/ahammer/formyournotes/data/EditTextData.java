package at.ahammer.formyournotes.data;

public class EditTextData extends FormYourNotesData {

	private String value;

	public EditTextData() {
		super();
	}

	public EditTextData(String value, int formId) {
		super(formId);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setData(EditTextData data) {
		super.setData(data);
		this.value = data.getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		EditTextData other = (EditTextData) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EditTextData [value=" + value + "]";
	}

}
