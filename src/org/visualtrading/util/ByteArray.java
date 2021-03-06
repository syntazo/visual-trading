/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.util;

public class ByteArray {

// ------------------------------ FIELDS ------------------------------

    private final static int DEFAULT_SIZE = 10;

    private byte[] data;
    private int currentPosition = 0;

// --------------------------- CONSTRUCTORS ---------------------------

    public ByteArray() {
        this(DEFAULT_SIZE);
    }

    public ByteArray(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size is negative.");
        }

        data = new byte[size];
    }

    public ByteArray(byte[] data) {
        this.data = data;
    }

// ------------------------ CANONICAL METHODS ------------------------

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof byte[])) {
            return false;
        }

        byte[] bytes = (byte[]) obj;

        if (bytes.length != data.length) {
            return false;
        }

        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != data[i]) {
                return false;
            }
        }

        return true;

    }

    public int hashCode() {
        int hash = super.hashCode();
        for (int i = 0; i < data.length; i++) {
            hash += 37 * data[i];
        }

        return hash;
    }

// -------------------------- OTHER METHODS --------------------------

    public ByteArray append(int value) {
        return append(Integer.toString(value, 10));
    }
    public ByteArray append(byte[] bytes) {

        if (bytes.length > 0) {
            validate(bytes.length);
            System.arraycopy(bytes, 0, data, currentPosition, bytes.length);
            currentPosition += bytes.length;
        }
        return this;
    }

    private void validate(int length) {
        if (currentPosition + length >= data.length) {
            byte[] tmp = new byte[data.length + 2 * length];
            System.arraycopy(data, 0, tmp, 0, data.length);
            data = tmp;
        }
    }

    public int length() {
        return currentPosition;
    }

// --------------------------- main() method ---------------------------

    public static void main(String[] args) {
        ByteArray array = new ByteArray(0);
        array.append("TEST");
        array.append((byte) 65);
        array.clear();
        array.append("TEST!");
        System.out.println("->" + array.toString());
    }

    public ByteArray append(byte b) {
        validate(1);
        data[currentPosition++] = b;
        return this;
    }

    public ByteArray clear() {
        currentPosition = 0;
        return this;
    }

    public ByteArray append(String str) {
        if (str == null) {
            return this;
        }
        return append(str.getBytes());
    }

    public String toString() {
        return new String(data, 0, currentPosition);
    }
}
    
