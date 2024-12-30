package org.stc.assessment.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.stc.assessment.model.Item;
import org.stc.assessment.model.ItemType;
import org.stc.assessment.model.Permission;
import org.stc.assessment.model.PermissionGroup;
import org.stc.assessment.model.PermissionLevel;
import org.stc.assessment.repository.ItemRepository;
import org.stc.assessment.repository.PermissionGroupRepository;
import org.stc.assessment.repository.PermissionRepository;

@Service
public class SpaceService {

    private final ItemRepository itemRepository;
    private final PermissionGroupRepository permissionGroupRepository;
    private final PermissionRepository permissionRepository;

    public SpaceService(ItemRepository itemRepository,
                        PermissionGroupRepository permissionGroupRepository,
                        PermissionRepository permissionRepository) {
        this.itemRepository = itemRepository;
        this.permissionGroupRepository = permissionGroupRepository;
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public void createSpace(String spaceName) {
        // Step 1: Create a permission group
        PermissionGroup adminGroup = new PermissionGroup();
        adminGroup.setGroupName("Admin Group");
        permissionGroupRepository.save(adminGroup);

        // Step 2: Add users to the permission group
        Permission viewPermission = new Permission();
        viewPermission.setUserEmail("view.user@example.com");
        viewPermission.setPermissionLevel(PermissionLevel.VIEW);
        viewPermission.setGroup(adminGroup);

        Permission editPermission = new Permission();
        editPermission.setUserEmail("edit.user@example.com");
        editPermission.setPermissionLevel(PermissionLevel.EDIT);
        editPermission.setGroup(adminGroup);

        permissionRepository.save(viewPermission);
        permissionRepository.save(editPermission);

        // Step 3: Create the space
        Item space = new Item();
        space.setName(spaceName);
        space.setType(ItemType.SPACE);
        space.setPermissionGroup(adminGroup);
        itemRepository.save(space);
    }
}
