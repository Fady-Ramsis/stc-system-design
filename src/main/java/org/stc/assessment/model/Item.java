package org.stc.assessment.model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "item_")
public class Item extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ItemType type;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Item parent;

    @ManyToOne
    @JoinColumn(name = "permission_group_id")
    private PermissionGroup permissionGroup;

    @OneToMany(mappedBy = "parent")
    private List<Item> children;

}
