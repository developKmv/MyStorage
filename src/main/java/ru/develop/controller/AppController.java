package ru.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

/*    @GetMapping("/")
    public String mainPage(){
        return "index";
    }*/

    @GetMapping("/")
    public String getAllstorages(Model model){

        List<Storage> getAllStorage = storageDAO.getAllStorage();
        model.addAttribute("allSt",getAllStorage);

        Storage storage = new Storage();
        model.addAttribute("obj",storage);

        return "index";
    }

  /*  @GetMapping("/create")
    public String createStorage(Model model){
        Storage storage = new Storage();
        model.addAttribute("obj",storage);

        System.out.println("Call create STORAGE");
        return "redirect:/";
    }
*/
    @PostMapping("/storage")
    public String saveStorage(@ModelAttribute Storage storage,Model model,@RequestParam("file") MultipartFile file){

        storage.setFileData(utils.uploadFile(file));
        model.addAttribute("obj",storage);
        storageDAO.saveStorage(storage);

        return "redirect:/";
    }

    @GetMapping("/storage")
    public String getStorages(@RequestParam("id") int id,@RequestParam("name") String name,
                              @RequestParam("extension")String ext){

        Storage storage = storageDAO.getStorage(id);
        utils.downloadFile(name,ext,storage.getFileData());
        return "redirect:/";
    }

}
