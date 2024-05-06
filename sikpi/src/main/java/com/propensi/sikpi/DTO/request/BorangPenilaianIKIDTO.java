package com.propensi.sikpi.DTO.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BorangPenilaianIKIDTO {

    private List<List<Integer>> kriteriaScoresIKI = new ArrayList<>();
    
}
