package org.spring.chestnut.card.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WorkerRemoveRequest {

    public List<Long> workerList = new ArrayList<>();

}
