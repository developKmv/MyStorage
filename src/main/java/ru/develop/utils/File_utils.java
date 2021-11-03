package ru.develop.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class File_utils {

    public byte[] uploadFile(MultipartFile file){
        byte[] rs = null;
       /* byte[] rs;
        List<Byte> listByte = new ArrayList<>();
        FileInputStream fis = null;

        try {
            fis = (FileInputStream) file.getInputStream();
            byte buf[] = new byte[2048];

            while (fis.read(buf,0,2048) > 0){
              for(byte b : buf) listByte.add(b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        rs = new byte[listByte.size()];

        for (int i =0;i<listByte.size();i++)
        {
            rs[i] = listByte.get(i);
        }

        return rs;*/
        try {
            rs = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void downloadFile(String name,String extension,byte[] out){
        File outputFile = new File(new String(name + "." + extension));

        try(RandomAccessFile writer = new RandomAccessFile(outputFile, "rw"))
        {
            writer.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
