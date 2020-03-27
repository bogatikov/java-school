package com.narnia.railways.service.dto;

import com.narnia.railways.model.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackDTO {

    private List<Station> stations;
}
