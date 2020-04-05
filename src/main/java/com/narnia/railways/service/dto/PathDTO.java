package com.narnia.railways.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class PathDTO {
    private Long id;
    private StationDTO f_node;
    private StationDTO s_node;
    private Boolean reserved;
    private Long length;
}
