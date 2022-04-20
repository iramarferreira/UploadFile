package br.com.iramar.UploadFile.service;

import br.com.iramar.UploadFile.model.FileEntity;
import br.com.iramar.UploadFile.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    // Salvar um arquivo
    public void save(MultipartFile file) throws IOException {
        FileEntity fileEntity = FileEntity.builder().build();

        fileEntity.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        fileEntity.setContentType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setSize(file.getSize());

        fileRepository.save(fileEntity);
    }

    // Buscar um arquivo pelo id
    public FileEntity findById(UUID id){
        if(fileRepository.findById(id).isPresent()){
            return fileRepository.findById(id).get();
        }else{
            return null;
        }
    }

    public List<FileEntity> findAll(){
        return fileRepository.findAll();
    }
}
