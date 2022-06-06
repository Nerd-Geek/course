package springbootapirestjava.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springbootapirestjava.config.APIConfig;
import springbootapirestjava.exceptions.storage.StorageException;
import springbootapirestjava.service.StorageService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(APIConfig.API_PATH + "/files")
public class FilesRestController {
    private StorageService storageService;

    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpServletRequest request) {
        Resource file = storageService.loadAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new StorageException("No se puede determinar el tipo de contenido del fichero", ex);
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestPart("file") MultipartFile file) {
        String urlImage = null;
        try {
            if (!file.isEmpty()) {
                String image = storageService.store(file);
                urlImage = storageService.getUrl(image);
                Map<String, Object> response = Map.of("url", urlImage);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                throw new StorageException("No se puede subir un fichero vacío");
            }
        } catch (StorageException e) {
            throw new StorageException("No se puede subir un fichero vacío");
        }
    }
}