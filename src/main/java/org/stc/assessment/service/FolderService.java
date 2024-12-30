package org.stc.assessment.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;

import org.springframework.stereotype.Service;
import org.stc.assessment.model.Item;
import org.stc.assessment.model.ItemType;
import org.stc.assessment.repository.ItemRepository;

@Service
public class FolderService {

    private final ItemRepository itemRepository;
    private final PermissionService permissionService;

    public FolderService(ItemRepository itemRepository, PermissionService permissionService) {
        this.itemRepository = itemRepository;
        this.permissionService = permissionService;
    }

    @SneakyThrows
    @Transactional
    @SuppressWarnings("unchecked")
    public void createFolder(Long parentId, String folderName, String userEmail) {
        // Step 1: Get the parent item
        Item parent = itemRepository.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent space/folder not found"));

        // Step 2: Check permissions
        if (!permissionService.hasEditAccess(userEmail, parent)) {
            throw new AuthException("User does not have EDIT access to create a folder.");
        }

        // Step 3: Create the folder
        Item folder = new Item();
        folder.setName(folderName);
        folder.setType(ItemType.FOLDER);
        folder.setParent(parent);
        folder.setPermissionGroup(parent.getPermissionGroup());
        itemRepository.save(folder);
    }
}

