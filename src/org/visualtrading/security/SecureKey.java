/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SecureKey {

// ------------------------------ FIELDS ------------------------------

    final static String usage = "his MessageDigest class provides applications the functionality of a message digest algorithm, such as MD5 or SHA. Message digests are secure one-way hash functions that take arbitrary-sized data and output a fixed-length hash value." +
                                "A MessageDigest object starts out initialized. The data is processed through it using the update methods. At any point reset can be called to reset the digest. Once all the data to be updated has been updated, one of the digest methods should be called to complete the hash computation." +
                                "The digest method can be called once for a given number of updates. After digest has been called, the MessageDigest object is reset to its initialized state." +
                                "Implementations are free to implement the Cloneable interface. Client applications can test cloneability by attempting cloning and catching the CloneNotSupportedException:" +
                                "" +
                                "MessageDigest md = MessageDigest.getInstance(SHA);" +
                                "try {" +
                                "md.update(toChapter1)" +
                                "MessageDigest tc1 = md.clone();" +
                                "byte[] toChapter1Digest = tc1.digest();" +
                                "md.update(toChapter2);" +
                                "...etc.";
    final static String usage2 = " } catch (CloneNotSupportedException cnse) " +
                                 "throw new DigestException(couldn't make digest of partial content);}" +
                                 "Note that if a given implementation is no" +
                                 "t cloneable, it is still possible to compute intermediate digests by instantiating several instances, if the number of digests is known in advance." +
                                 "Note that this class is abstract and extends f" +
                                 "rom MessageDigestSpi for historical reasons. Applicati" +
                                 "on developers should onl" +
                                 "take notice of the methods defined in this MessageDigest clas" +
                                 "s; all the methods in the superclass are intended for cryptographic" +
                                 " service providers who wish to supply their own implementa" +
                                 "tions of message digest algorithms. ";

    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

// -------------------------- STATIC METHODS --------------------------

    public static boolean check(String id, String key) {
        return key.equals(make(id));
    }

    public static String make(String id) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            String s = usage + id + usage2, r = "";
            byte[] s1 = s.getBytes();
            int slice = 100, stop = s.length() - slice;
            for (int count = 0; count <= stop; count += slice) {
                md.update(s.substring(count, count + slice).getBytes());
                r += encodeBytesAsHex(md.digest()) + "\n";
            }
            return r;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String encodeBytesAsHex(byte[] bites) {
        char[] hexChars = new char[bites.length * 2];
        for (int charIndex = 0, startIndex = 0; charIndex < hexChars.length;) {
            int bite = bites[startIndex++] & 0xff;
            hexChars[charIndex++] = HEX_CHARS[bite >> 4];
            hexChars[charIndex++] = HEX_CHARS[bite & 0xf];
        }
        return new String(hexChars);
    }

}
