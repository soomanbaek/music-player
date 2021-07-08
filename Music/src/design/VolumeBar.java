package design;

import java.awt.Color;
import java.awt.Dimension;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class VolumeBar extends JSlider {
	VolumeControl ctr_vol = new VolumeControl();

	public VolumeBar() {
		setOrientation(JSlider.HORIZONTAL);
		setMinimum(0);
		setMaximum(100);
		setPreferredSize(new Dimension(50, 10));
		setFocusable(false);
		setOpaque(true);
		setBackground(Color.PINK);
		setUI(new SliderUI(this));
		setVisible(true);
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider src = (JSlider) e.getSource();
				// if (src.getValueIsAdjusting()) return; //optional
				if (src.getValue() % 5 != 0)
					return;
				float value = src.getValue() / 100.0f;
				try {
					ctr_vol.getVolumeControl().setValue(value);
					// you can put a click play code here to have nice feedback
					// when moving slider
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});

		// and this is for getting the value
		try {
			setValue((int) (ctr_vol.getVolumeControl().getValue() * 100.0f));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
class VolumeControl
{
	public FloatControl getVolumeControl() {
		try {
			Mixer.Info mixers[] = AudioSystem.getMixerInfo();
			for (Mixer.Info mixerInfo : mixers) {
				Mixer mixer = AudioSystem.getMixer(mixerInfo);
				mixer.open();

				// we check only target type lines, because we are looking for
				// "SPEAKER target port"
				for (Line.Info info : mixer.getTargetLineInfo()) {
					if (info.toString().contains("SPEAKER")) {
						Line line = mixer.getLine(info);
						try {
							line.open();
						} catch (IllegalArgumentException e) {
						}
						return (FloatControl) line
								.getControl(FloatControl.Type.VOLUME);
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
}