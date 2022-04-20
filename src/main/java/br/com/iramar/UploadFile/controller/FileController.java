package br.com.iramar.UploadFile.controller;


import br.com.iramar.UploadFile.model.FileEntity;
import br.com.iramar.UploadFile.model.FileResponse;
import br.com.iramar.UploadFile.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ResponseBody
@Controller
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileService fileService;

    // Salvar um arquivo
    @PostMapping(path = "/file")
    public ResponseEntity<String> save(@RequestPart(value = "file") MultipartFile file){
        try{
            fileService.save(file);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(String.format("Upload realizado com sucesso: %s!", file.getOriginalFilename()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Não foi possível fazer o upload do arquivo: %s!", file.getOriginalFilename()));
        }
    }

    // listar todos os arquivos
    @GetMapping(path = "/files")
    public List<FileResponse> findAll(){
        return fileService.findAll()
                .stream()
                .map(this::mapToFileResponse)
                .collect(Collectors.toList());
    }

    // Método para fazer o mapeamento do FileEntity para FileResponse
    private FileResponse mapToFileResponse(FileEntity fileEntity){
        String downloadURL = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/file/")
                .path(fileEntity.getId().toString())
                .toUriString();

        FileResponse fileResponse = FileResponse.builder().build();
        fileResponse.setId(fileEntity.getId());
        fileResponse.setName(fileEntity.getName());
        fileResponse.setContentType(fileEntity.getContentType());
        fileResponse.setSize(fileEntity.getSize());
        fileResponse.setUrl(downloadURL);

        return fileResponse;

    }

    @GetMapping(path = "/file/{id}")
    public ResponseEntity<byte[]> findById(@PathVariable UUID id){
        FileEntity fileEntity = fileService.findById(id);

        if(fileEntity == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileEntity.getName() + "\"")
                    .body(fileEntity.getData());
        }
    }

}
