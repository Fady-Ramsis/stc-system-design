package org.stc.assessment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "file_")
public class File extends BaseEntity {

    @Lob
    @Column(name = "binary_data", columnDefinition = "VARBINARY(MAX)")
    private byte[] binaryData;

    @OneToOne
    @JoinColumn(name = "item_id", unique = true)
    private Item item;
}
