package ch.epfl.cs107.utils;

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
 * <b>Task 1.1: </b>Utility class to manipulate bits
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Bit {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Bit(){}

    // ============================================================================================
    // ==================================== BIT MANIPULATION ======================================
    // ============================================================================================

    public static int embedInXthBit(int value, boolean m, int pos) {
        if (m) {
            int bit = 1 << pos;
            return value | bit;
        } else {
            int bit = ~(1 << pos);
            return value & bit;
        }
    }

    public static int embedInLSB(int value, boolean m){
        return(embedInXthBit(value,m,0) );
    }

    public static boolean getXthBit(int value, int pos) {
        return (value & ( 1 << pos)) != 0;
    }

    public static boolean getLSB(int value) {
        return (value & 1)!=0;
    }

    // ============================================================================================
    // ==================================== BYTE MANIPULATION =====================================
    // ============================================================================================

    public static boolean[] toBitArray(byte value) {
        boolean[] bitArray = new boolean[8];
        for (int i = 0; i < 8; i++) {
            bitArray[i] = (value & (1 << i)) != 0;
        }
        return bitArray;
    }
    public static byte toByte(boolean[] bitArray) {
        byte value = 0;
        for (int i = 0; i < bitArray.length; i++) {
            if (bitArray[i]) {
                value = (byte) (value | (1 << i));
            }
        }
        return value;
    }

}
