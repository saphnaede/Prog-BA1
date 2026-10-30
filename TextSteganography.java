package ch.epfl.cs107.stegano;

import ch.epfl.cs107.Helper;

import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;

/**
 * <b>Task 3.2: </b>Utility class to perform Text Steganography
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public class TextSteganography {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private TextSteganography(){}

    // ============================================================================================
    // =================================== EMBEDDING BIT ARRAY ====================================
    // ============================================================================================

    /**
     * Embed a bitmap message in an ARGB image
     * @param cover Cover image
     * @param message Embedded message
     * @return ARGB image with the message embedded
     */
    public static int[][] embedBitArray(int[][] cover, boolean[] message) {
        int[][] result = new int[cover.length][cover[0].length];
        for (int i = 0; i < cover.length; i++) {
            for (int j = 0; j < cover[i].length; j++) {
                result[i][j] = cover[i][j];
            }
        }
        int bitIndex = 0;

        for (int i = 0; i < result.length && bitIndex < message.length; i++) {
            for (int j = 0; j < result[i].length && bitIndex < message.length; j++) {
                result[i][j] = embedInLSB(result[i][j], message[bitIndex]);
                bitIndex++;
            }
        }

        return result;
    }

    /**
     * Extract a bitmap from an image
     * @param image Image to extract from
     * @return extracted message
     */
    public static boolean[] revealBitArray(int[][] image) {
        int rows = image.length;
        int cols = image[0].length;

        boolean[] message = new boolean[rows * cols];

        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                message[index] = getLSB(image[i][j]);
                index++;
            }
        }

        return message;
    }



    // ============================================================================================
    // ===================================== EMBEDDING STRING =====================================
    // ============================================================================================

    /**
     * Embed a String message in an ARGB image
     * @param cover Cover image
     * @param message Embedded message
     * @return ARGB image with the message embedded
     */
    public static int[][] embedText(int[][] cover, byte[] message) {
        boolean[] bits = new boolean[message.length * 8];
        int index = 0;
        for (int i = 0; i < message.length; i++) {
            boolean[] byteBits = toBitArray(message[i]);

            for (int j = 0; j < 8; j++) {
                bits[index] = byteBits[j];
                index++;
            }
        }

        return embedBitArray(cover, bits);
    }


    /**
     * Extract a String from an image
     * @param image Image to extract from
     * @return extracted message
     */
    public static byte[] revealText(int[][] image) {
        boolean[] bits = revealBitArray(image);

        int numBytes = bits.length / 8;
        byte[] message = new byte[numBytes];

        for (int i = 0; i < numBytes; i++) {
            boolean[] byteBits = new boolean[8];

            for (int j = 0; j < 8; j++) {
                byteBits[j] = bits[i * 8 + j];
            }

            message[i] = toByte(byteBits);
        }

        return message;
    }

}
