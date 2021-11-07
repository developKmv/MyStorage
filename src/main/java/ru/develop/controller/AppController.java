package ru.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.develop.dao.StorageDAO;
import ru.develop.entity.Storage;
import ru.develop.exception_handling.EntityExp;
import ru.develop.utils.File_utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class AppController {

    private static final Logger log = Logger.getLogger(AppController.class.getName());

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

        String fileName = file.getOriginalFilename();
        storage.setFileName(fileName);
        log.log(Level.INFO,"File name "+ file.getOriginalFilename());
        String ext = fileName.substring(fileName.indexOf("."));
        storage.setFileExtensions(ext);

        log.log(Level.INFO,storage.toString());
        storage.setFileData(utils.uploadFile(file));

        model.addAttribute("obj",storage);
        storageDAO.saveStorage(storage);

        return "redirect:/";
    }

    /*@GetMapping("/storage")
    public String getStorages(@RequestParam("id") int id,@RequestParam("name") String name,
                              @RequestParam("extension")String ext){

        Storage storage = storageDAO.getStorage(id);
        utils.downloadFile(name,ext,storage.getFileData());
        return "redirect:/";
    }*/

    @GetMapping("/storage")
    public  ResponseEntity<Resource> download(@RequestParam("id") int id,@RequestParam("name") String name,
                                             @RequestParam("extension")String ext){
        String strPath = String.format("%s.%s",name,ext);
        log.log(Level.INFO,strPath);

        Storage storage = storageDAO.getStorage(id);
        //File file = new File(strPath);

      /*  try {
            Files.write(file.toPath(),storage.getFileData());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //log.log(Level.INFO, String.valueOf(file.length()));
        log.log(Level.INFO, String.valueOf(storage.getFileData()));

        ByteArrayResource resource = new ByteArrayResource(storage.getFileData());
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + strPath);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(storage.getFileData().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @DeleteMapping("/storage")
    @ResponseBody
    public String deleteStorage(@RequestParam("id") int id){
        log.log(Level.INFO,"call delete!");
        storageDAO.deleteStorage(id);
      return "redirect:/";
    };

    @ExceptionHandler
    public ResponseEntity<EntityExp> handleException(Exception e){
        EntityExp entityExp = new EntityExp();
        entityExp.setMsg(e.getMessage());

        return  new ResponseEntity<>(entityExp, HttpStatus.NOT_FOUND);
    }

}
