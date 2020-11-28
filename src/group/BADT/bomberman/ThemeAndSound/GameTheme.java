package group.BADT.bomberman.ThemeAndSound;

import javax.sound.sampled.*;
import java.io.File;

public class GameTheme {
    public static GameTheme sound  = new GameTheme();
    public static Clip clip;
    public static boolean musicSate = true;

    private GameTheme() {

    }

    public static GameTheme getInstance() {
        return sound;
    }

    public static void loadMusic(String file) {
        try {
            File musicPath = new File("res/sound/" + file + ".wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);

        }catch (Exception e) {
            System.err.println(e);
        }
    }


    public static void play() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               // clip.setMicrosecondPosition(0);
                if (musicSate) {
                    clip.start();
                }
            }
        }).start();
    }
    public static void stop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                clip.stop();
            }
        }).start();
    }

    public static void volumeDownControl (Double valueToPlusMinus) {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info[] lineInfors = mixer.getTargetLineInfo();
            for (Line.Info lineInfo: lineInfors) {
                Line line = null;
                boolean opened = true;
                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;

                    if (!opened) {
                        // open Line
                        line.open();
                    }
                    FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    float currentVolume = volControl.getValue();
                    Double volumeToCut = valueToPlusMinus;
                    float changeCalc =(float) ((float) currentVolume - (double)volumeToCut);
                    volControl.setValue(changeCalc);
                }catch(LineUnavailableException lineException) {
                }catch (IllegalArgumentException illException) {
                }finally {
                    // close time if it opened
                    if (line != null && opened) {
                        line.close();
                    }
                }


            }
        }
    }

    public static void volumeUpControl (Double valueToPlusMinus) {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info[] lineInfors = mixer.getTargetLineInfo();
            for (Line.Info lineInfo: lineInfors) {
                Line line = null;
                boolean opened = true;
                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;

                    if (!opened) {
                        // open Line
                        line.open();
                    }
                    FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    float currentVolume = volControl.getValue();
                    Double volumeToCut = valueToPlusMinus;
                    float changeCalc =(float) ((float) currentVolume + (double)volumeToCut);
                    volControl.setValue(changeCalc);
                }catch(LineUnavailableException lineException) {
                }catch (IllegalArgumentException illException) {
                }finally {
                    // close time if it opened
                    if (line != null && opened) {
                        line.close();
                    }
                }


            }
        }
    }


    public static void volumeControl (Double valueToPlusMinus) {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info[] lineInfors = mixer.getTargetLineInfo();
            for (Line.Info lineInfo: lineInfors) {
                Line line = null;
                boolean opened = true;
                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;

                    if (!opened) {
                        // open Line
                        line.open();
                    }
                    FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    float currentVolume = volControl.getValue();
                    Double volumeToCut = valueToPlusMinus;
                    float changeCalc =(float) ((double)volumeToCut);
                    volControl.setValue(changeCalc);
                }catch(LineUnavailableException lineException) {
                }catch (IllegalArgumentException illException) {
                }finally {
                    // close time if it opened
                    if (line != null && opened) {
                        line.close();
                    }
                }


            }
        }
    }


}
