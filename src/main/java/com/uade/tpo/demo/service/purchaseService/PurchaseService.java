package com.uade.tpo.demo.service.purchaseService;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.PurchaseDTO;

public interface PurchaseService {

    void purchase(PurchaseDTO purchaseDTO, User buyer);
}
