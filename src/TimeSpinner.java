import javax.swing.AbstractSpinnerModel;
import javax.swing.JSpinner;

public class TimeSpinner extends JSpinner {

	public TimeSpinner() {
		super();
		setModel(new TimeSpinnerModel());
		// make editable
		((JSpinner.DefaultEditor) getEditor()).getTextField().setEditable(true);
	}

	public class TimeSpinnerModel extends AbstractSpinnerModel {
	
		private Time t = new Time(0);

		@Override
		public Object getValue() {
			return t;
		}

		@Override
		public void setValue(Object o) {
			Time t2;
			try {
				if (! t.equals(t2 = Time.parseTime(o.toString())) && t2.toSeconds() > 0) {
					t = t2;
				}
				fireStateChanged();
			} catch (NumberFormatException e) {}
		}

		@Override
		public Object getNextValue() {
			// next 10 seconds step
			return new Time(((t.toSeconds() + 10) / 10) * 10);
		}
	
		@Override
		public Object getPreviousValue() {
			if (t.toSeconds() > 10) {
				// previous 10 seconds step
				return new Time(((t.toSeconds() - 1) / 10) * 10);
			}
			return t;
		}
	}
}
