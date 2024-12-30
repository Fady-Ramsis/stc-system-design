package org.stc.assessment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileMetadataDTO {

    private Long itemId;
    private String fileName;
    private String itemType;
    private String userEmail;
    private String permissionLevel;
    private String groupName;

    public FileMetadataDTO(Long itemId, String fileName, String itemType,
                           String userEmail, String permissionLevel, String groupName) {
        this.itemId = itemId;
        this.fileName = fileName;
        this.itemType = itemType;
        this.userEmail = userEmail;
        this.permissionLevel = permissionLevel;
        this.groupName = groupName;
    }
}
