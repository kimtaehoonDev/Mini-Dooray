package com.kimtaehoonki.task.tag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColorCode {
    RED("#8B0000"), BLUE("#0000FF"), GREEN("#006400"), YELLOW("#FAFAD2"), ORANGE("#FFA500");

    private String colorCode;
}
