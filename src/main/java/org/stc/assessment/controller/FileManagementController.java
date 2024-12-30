package org.stc.assessment.controller;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stc.assessment.model.File;
import org.stc.assessment.model.FileMetadataDTO;
import org.stc.assessment.service.FileService;
import org.stc.assessment.service.FolderService;
import org.stc.assessment.service.SpaceService;

@RestController
@RequestMapping("/api")
public class FileManagementController {

    private final SpaceService spaceService;
    private final FolderService folderService;
    private final FileService fileService;

    public FileManagementController(SpaceService spaceService, FolderService folderService, FileService fileService) {
        this.spaceService = spaceService;
        this.folderService = folderService;
        this.fileService = fileService;
    }


    @PostMapping("/spaces")
    public ResponseEntity<String> createSpace(@RequestParam(name = "space_name") String spaceName) {
        spaceService.createSpace(spaceName);
        return ResponseEntity.ok("Space '" + spaceName + "' created with permissions.");
    }

    @PostMapping("/folders")
    public ResponseEntity<String> createFolder(@RequestParam(name = "parent_space_id") Long parentSpaceId,
                                               @RequestParam(name = "folder_name") String folderName,
                                               @RequestParam(name = "user_email") String userEmail) {
        folderService.createFolder(parentSpaceId, folderName, userEmail);
        return ResponseEntity.ok("Folder '" + folderName + "' created under space ID " + parentSpaceId);
    }

    @PostMapping("/files")
    public ResponseEntity<String> createFile(@RequestParam(name = "folder_id") Long folderId,
                                             @RequestParam(name = "file_name") String fileName,
                                             @RequestParam("user_email") String userEmail,
                                             @RequestBody byte[] fileData) {
        fileService.createFile(folderId, fileName, userEmail, fileData);
        return ResponseEntity.ok("File '" + fileName + "' created under folder ID " + folderId);
    }

    @GetMapping("/files/{id}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable(name = "id") Long id) {
        File file = fileService.getFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getItem().getName() + "\"")
                .body(file.getBinaryData());
    }

    @GetMapping("/files/{id}/metadata")
    public ResponseEntity<Object> getFileMetadata(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "user_email") String userEmail) {
        Optional<FileMetadataDTO> metadata = fileService.getFileMetadata(id, userEmail);
        return metadata.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Access denied or file not found."));
    }
}

