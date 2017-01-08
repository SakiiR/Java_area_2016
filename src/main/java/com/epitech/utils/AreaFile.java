package com.epitech.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is used to represent a file.
 */
public class                            AreaFile {
    private byte[]                      data;
    private String                      filename;
    private String                      mime;

    /**
     * Constructor.
     *
     * @param filename the filename.
     * @param data the data byte array.
     * @param mime the mimetype
     */
    public                              AreaFile(String filename, byte[] data, String mime) {
        this.data = data;
        this.filename = filename;
        this.mime = mime;
    }

    /**
     * Getter
     *
     * @return a data byte array.
     */
    public byte[]                       getData() {
        return this.data;
    }

    /**
     * Getter.
     *
     * @return a string File name.
     */
    public String                       getFilename() {
        return filename;
    }

    /**
     * Getter.
     *
     * @return a String mime type.
     */
    public String                       getMime() {
        return mime;
    }

    /**
     * Setter.
     *
     * @param data the data byte array to set.
     * @return an instance of AreaFile.
     */
    public AreaFile                     setData(byte[] data) {
        this.data = data;
        return this;
    }

    /**
     * Setter.
     *
     * @param filename the string filename to set.
     * @return an instance of AreaFile.
     */
    public AreaFile                     setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * Setter.
     *
     * @param mime the string mime type.
     * @return an instance of AreaFile.
     */
    public AreaFile                     setMime(String mime) {
        this.mime = mime;
        return this;
    }

    /**
     * Save the file to the disk.
     *
     * @return a IO File
     * @see java.io.File
     * @throws FileNotFoundException throw a FileNotFoundException Exception
     * @throws IOException throw a IOException Exception.
     */
    public java.io.File                 saveFile() throws FileNotFoundException, IOException {
        java.io.File                    newFile = new java.io.File(String.format("/tmp/%s", filename));
        FileOutputStream fileOutputStream = new FileOutputStream(newFile.getAbsoluteFile());
        fileOutputStream.write(this.data);
        fileOutputStream.close();
        return newFile;
    }
}