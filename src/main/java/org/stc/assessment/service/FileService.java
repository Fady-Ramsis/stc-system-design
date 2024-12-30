package org.stc.assessment.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.stc.assessment.model.File;
import org.stc.assessment.model.FileMetadataDTO;
import org.stc.assessment.model.Item;
import org.stc.assessment.model.ItemType;
import org.stc.assessment.repository.FileRepository;
import org.stc.assessment.repository.ItemRepository;

@Service
public class FileService {

    private final ItemRepository itemRepository;
    private final FileRepository fileRepository;
    private final PermissionService permissionService;

    public FileService(ItemRepository itemRepository,
                       FileRepository fileRepository,
                       PermissionService permissionService) {
        this.itemRepository = itemRepository;
        this.fileRepository = fileRepository;
        this.permissionService = permissionService;
    }

    @SneakyThrows
    @Transactional
    @SuppressWarnings("unchecked")
    public void createFile(Long folderId, String fileName, String userEmail, byte[] fileData) {
        // Step 1: Get the parent folder
        Item folder = itemRepository.findById(folderId)
                .orElseThrow(() -> new EntityNotFoundException("Parent folder not found"));

        // Step 2: Check permissions
        if (!permissionService.hasEditAccess(userEmail, folder)) {
            throw new AuthException("User does not have EDIT access to create a file.");
        }

        // Step 3: Create the file as an item
        Item fileItem = new Item();
        fileItem.setName(fileName);
        fileItem.setType(ItemType.FILE);
        fileItem.setParent(folder);
        fileItem.setPermissionGroup(folder.getPermissionGroup());
        itemRepository.save(fileItem);

        // Step 4: Save the binary data in the file table
        File file = new File();
        file.setBinaryData(fileData);
        file.setItem(fileItem);
        fileRepository.save(file);
    }

    public File getFileById(long id) {
        return fileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("File with id " + id + " not found!"));
    }

    public Optional<FileMetadataDTO> getFileMetadata(Long fileId, String userEmail) {
        return fileRepository.findFileMetadata(fileId, userEmail);
    }


}
