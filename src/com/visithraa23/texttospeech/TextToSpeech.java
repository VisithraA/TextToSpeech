package com.visithraa23.texttospeech;

import java.util.Locale;
import java.util.Scanner;

import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class TextToSpeech {
	private static Synthesizer synthesizer;

    static {
        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

            SynthesizerModeDesc desc = new SynthesizerModeDesc(Locale.US);
            synthesizer = Central.createSynthesizer(desc);
            synthesizer.allocate();
            synthesizer.resume();
            synthesizer.getSynthesizerProperties().setSpeakingRate(100);
            synthesizer.getSynthesizerProperties().setPitch(150);
            synthesizer.getSynthesizerProperties().setVolume(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws EngineException, EngineStateError {
        try {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Enter the text to speak (type 'exit' to stop):");
                String userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("exit")) {
                	System.out.println("Exit.......");
                    break;
                }

                doSpeak(userInput);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            synthesizer.deallocate();
        }
    }


    private static void doSpeak(String text) {
        try {
            synthesizer.speakPlainText(text, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
}
}
