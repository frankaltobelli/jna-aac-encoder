package org.sheinbergon.aac.sound;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sheinbergon.aac.MediaInfoSupport;
import org.sheinbergon.aac.TestSupport;
import org.sheinbergon.aac.encoder.util.AACEncodingProfile;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

// TODO - Consider adding actual byte-level comparison encoding tests
@DisplayName("Java AudioSystem AAC encoding support")
public class AACFileWriterTest {

    private final static AudioFileFormat.Type[] NO_FILE_TYPES = new AudioFileFormat.Type[0];
    private final static AudioFileFormat.Type[] AAC_FILE_TYPES = new AudioFileFormat.Type[]{AACFileTypes.AAC_LC, AACFileTypes.AAC_HE, AACFileTypes.AAC_HE_V2};
    private final static AACEncodingProfile[] AAC_AUDIO_TYPES = new AACEncodingProfile[]{AACEncodingProfile.AAC_LC, AACEncodingProfile.HE_AAC, AACEncodingProfile.HE_AAC_V2};
    private final AACFileWriter writer = new AACFileWriter();

    @Test
    @DisplayName("Supported audio file types")
    public void supportedTypes() {
        Assertions.assertArrayEquals(AAC_FILE_TYPES, writer.getAudioFileTypes());
    }

    @Test
    @DisplayName("Unsupported audio input")
    public void unsupportedAudioInput() throws UnsupportedAudioFileException, IOException {
        AudioInputStream input = TestSupport.unsupported24bitWAVAudioInputStream();
        Assertions.assertArrayEquals(NO_FILE_TYPES, writer.getAudioFileTypes(input));
    }

    @Test
    @DisplayName("Supported audio input")
    public void supportedAudioInput() throws UnsupportedAudioFileException, IOException {
        AudioInputStream input = TestSupport.supportedWAVAudioInputStream();
        Assertions.assertArrayEquals(AAC_FILE_TYPES, writer.getAudioFileTypes(input));
    }

    @Test
    @DisplayName("Unsupported audio encoding")
    public void unsupportedAudioEncoding() throws UnsupportedAudioFileException, IOException {
        File aac = TestSupport.tempAACOutputFile();
        AudioInputStream input = TestSupport.unsupported24bitWAVAudioInputStream();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            try (input) {
                writer.write(input, AACFileTypes.AAC_LC, aac);
            } finally {
                if (aac.exists()) {
                    Files.delete(aac.toPath());
                }
            }
        });
    }

    @Test
    @DisplayName("Supported audio types encoding (LC/HE-AAC/HE-AACv2)")
    public void supportedAudioOutputStreamEncoding() {
        Assertions.assertAll(Stream.of(AAC_FILE_TYPES).map(audioType -> () -> {
            File aac = TestSupport.tempAACOutputFile();
            FileOutputStream output = new FileOutputStream(aac);
            AudioInputStream input = TestSupport.supportedWAVAudioInputStream();
            try (input; output) {
                writer.write(input, audioType, output);
                Assertions.assertTrue(aac.length() > 0);
                output.flush();
                MediaInfoSupport.assertAACOutput(aac, input.getFormat(), AACFileWriter.profileByType(audioType));
            } finally {
                if (aac.exists()) {
                    Files.delete(aac.toPath());
                }
            }
        }));
    }

    @Test
    @DisplayName("Supported audio file encoding")
    public void supportedAudioFileEncoding() throws UnsupportedAudioFileException, IOException {
        File aac = TestSupport.tempAACOutputFile();
        AudioInputStream input = TestSupport.supportedWAVAudioInputStream();
        try (input) {
            writer.write(input, AACFileTypes.AAC_LC, aac);
            Assertions.assertTrue(aac.length() > 0);
            MediaInfoSupport.assertAACOutput(aac, input.getFormat(), AACEncodingProfile.AAC_LC);
        } finally {
            if (aac.exists()) {
                Files.delete(aac.toPath());
            }
        }
    }
}
