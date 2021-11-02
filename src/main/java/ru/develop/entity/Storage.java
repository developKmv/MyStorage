package ru.develop.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "storage")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "file_data")
    private byte[] fileData;
    @Column(name = "file_extensions")
    private String fileExtensions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileExtensions() {
        return fileExtensions;
    }

    public void setFileExtensions(String fileExtensions) {
        this.fileExtensions = fileExtensions;
    }

    public Storage() {
    }

    public Storage(String fileName, byte[] fileData, String fileExtensions) {
        this.fileName = fileName;
        this.fileData = fileData;
        this.fileExtensions = fileExtensions;
    }
}
