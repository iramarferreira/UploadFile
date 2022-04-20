package br.com.iramar.UploadFile.model;

import lombok.*;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FileResponse {

    private UUID id;
    private String name;
    private Long size;
    private String url;
    private String contentType;

}
