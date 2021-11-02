package ru.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.develop.dao.StorageDAO;
import ru.develop.entity.Storage;
import ru.develop.utils.File_utils;

import java.util.List;

@Controller

public class AppController {

    @Autowired
    private StorageDAO storageDAO;
    @Autowired
    public File_utils utils;

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

    @GetMapping("/storages")
    public String getAllstorages(Model model){

        List<Storage> getAllStorage = storageDAO.getAllStorage();
        model.addAttribute("allSt",getAllStorage);

        return "index";
    }

    @PostMapping("/storages")
    public String saveStorage(@RequestParam("file") MultipartFile file){

        Storage curStorage = new Storage();
        curStorage.setFileName(file.getName());
        curStorage.setFileData(utils.uploadFile(file));

        storageDAO.saveStorage(curStorage);

        return "index";
    }

    @GetMapping("/storage")
    public String getStorages(Model model){

        Storage storage = storageDAO.getStorage(4);
        utils.downloadFile(storage.getFileData());
        return "index";
    }

}
