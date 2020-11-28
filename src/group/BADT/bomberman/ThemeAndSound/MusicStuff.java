package group.BADT.bomberman.ThemeAndSound;

import javax.sound.sampled.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class MusicStuff {

    public static MusicStuff mss;
    public static boolean mState;

    public MusicStuff(boolean mState) {
        this.mState = mState;
    }

    public void getCur(boolean state) {
        mState = state;
    }

    public static void play(String sound) {
        new Thread(new Runnable() {
            public void run() {

                try {
                    File musicPath = new File("res/sound/" + sound + ".wav");

                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);

                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.start();

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

            }
        }).start();
    }
}