package com.iush.ca.transversal.domain.models.responses;

import java.util.List;

public record ResponseList<T> (List<T> data, PaginationResponse pagination){
}
