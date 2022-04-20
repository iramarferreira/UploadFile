package br.com.iramar.UploadFile.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

// Anotações lombook
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
// Anotações JPA
@Entity
@Table(name = "files")
public class FileEntity {

    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String contentType;

    private Long size;

    @Lob
    private byte[] data;

}