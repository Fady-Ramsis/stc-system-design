package org.stc.assessment.service;

import org.springframework.stereotype.Service;
import org.stc.assessment.model.Item;
import org.stc.assessment.model.PermissionLevel;

@Service
public class PermissionService {

    public boolean hasEditAccess(String userEmail, Item item) {
        return item.getPermissionGroup().getPermissions().stream()
                .anyMatch(permission -> permission.getUserEmail().equals(userEmail) &&
                        permission.getPermissionLevel() == PermissionLevel.EDIT);
    }

    public boolean hasViewAccess(String userEmail, Item item) {
        return item.getPermissionGroup().getPermissions().stream()
                .anyMatch(permission -> permission.getUserEmail().equals(userEmail) &&
                        (permission.getPermissionLevel() == PermissionLevel.VIEW ||
                                permission.getPermissionLevel() == PermissionLevel.EDIT));
    }
}
