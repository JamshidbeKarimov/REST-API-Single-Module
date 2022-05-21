package com.epam.esm.service;

import com.epam.esm.dto.BaseResponse;

public interface BaseService<P, G> {
    BaseResponse<G> create(P p);

    BaseResponse<G> get(Long id);

    BaseResponse delete(Long id);
}
