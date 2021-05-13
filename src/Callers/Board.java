package Callers;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
/*
@author: Liam Gardner
@class: Board (controlls board stuff)
@date: 2018/4/4
 */
public class Board {
    //I started this but only did the class header for some reason
    /*
      @name: Check
      @params: 3D int array
      @purpose: checks for any win possibilities, return player that won or return 0
     */
    public static int check(int[][][] board) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j][0] != 0 && board[i][j][0] == board[i][j][1] && board[i][j][0] == board[i][j][2] && board[i][j][0] == board[i][j][3] && board[i][j][0] == board[i][j][4]) {
                    return board[i][j][0];
                }
            }
            for (int j = 0; j < 5; j++) {
                if (board[i][0][j] != 0 && board[i][0][j] == board[i][1][j] && board[i][0][j] == board[i][2][j] && board[i][0][j] == board[i][3][j] && board[i][0][j] == board[i][4][j]) {
                    return board[i][0][j];
                }
            }
            if (board[i][0][0] != 0 && board[i][0][0] == board[i][1][1] && board[i][0][0] == board[i][2][2] && board[i][0][0] == board[i][3][3] && board[i][0][0] == board[i][4][4]) {
                return board[i][0][0];
            }
            if (board[i][0][4] != 0 && board[i][0][4] == board[i][1][3] && board[i][0][4] == board[i][2][2] && board[i][0][4] == board[i][3][1] && board[i][0][4] == board[i][4][0]) {
                return board[i][0][4];
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[0][i][j] != 0 && board[0][i][j] == board[1][i][j] && board[0][i][j] == board[2][i][j] && board[0][i][j] == board[3][i][j] && board[0][i][j] == board[4][i][j]) {
                    return board[0][i][j];
                }
            }
        }


        return 0;
    }
    /*
      @Names: playMusic
      @params: either a string or URL
      @purpose: MUSIC!!!
     */
    public static void playMusic(String Song) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Board.class.getResource(Song)));
            clip.loop(Clip.LOOP_CONTINUOUSLY); //I do actively play this with my friend so I will add comments for parts of the code that weren't here when I handed them in, this is one of them.
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playMusic(URL Song) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Song));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
