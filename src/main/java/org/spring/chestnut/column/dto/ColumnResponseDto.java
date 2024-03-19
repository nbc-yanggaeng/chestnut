package org.spring.chestnut.column.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnResponseDto {

    private Long id;
    private String title;
    private Integer sequence;

    @JsonCreator
    public ColumnResponseDto(@JsonProperty("id") Long id,
        @JsonProperty("title") String title,
        @JsonProperty("sequence") Integer sequence) {
        this.id = id;
        this.title = title;
        this.sequence = sequence;
    }
}