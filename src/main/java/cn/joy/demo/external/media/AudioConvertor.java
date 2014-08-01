package cn.joy.demo.external.media;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;

public class AudioConvertor {
	public void amr2mp3(File source, File target) {
		AudioAttributes audio = new AudioAttributes();
		Encoder encoder = new Encoder();

		try {
			for (String fs : encoder.getSupportedEncodingFormats()){
				System.out.println("SupportedEncodingFormat: "+fs);
			}
			// pcm_s16le libmp3lame libvorbis libfaac
			audio.setCodec("libmp3lame");
			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setFormat("mp3");
			attrs.setAudioAttributes(audio);

			encoder.encode(source, target, attrs);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		File source = new File("D:\\20131101192318-758886747.amr");
		File target = new File("D:\\20131101192318-758886747.mp3");
		
		AudioConvertor convertor = new AudioConvertor();
		convertor.amr2mp3(source, target);

	}
}